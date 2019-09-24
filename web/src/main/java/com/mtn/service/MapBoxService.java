package com.mtn.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.mtn.constant.StoreType;
import com.mtn.model.domain.*;
import com.mtn.repository.BlockGroupPopulationCentroidRepository;
import com.mtn.repository.SiteBlockGroupDriveTimeRepository;
import com.mtn.repository.SiteIsochroneRepository;
import com.mtn.util.MtnLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class MapBoxService {

	@Value("${map-box-token}")
	private String mapBoxToken;

	private final SiteService siteService;
	private final ProjectService projectService;
	private final SiteIsochroneRepository siteIsochroneRepository;
	private final SiteBlockGroupDriveTimeRepository siteBlockGroupDriveTimeRepository;
	private final BlockGroupPopulationCentroidRepository blockGroupPopulationCentroidRepository;

	private long driveTimeRequestStartTime = 0;
	private int driveTimeRequestCount = 0;

	@Autowired
	public MapBoxService(SiteService siteService,
						 ProjectService projectService,
						 SiteIsochroneRepository siteIsochroneRepository,
						 SiteBlockGroupDriveTimeRepository siteBlockGroupDriveTimeRepository,
						 BlockGroupPopulationCentroidRepository blockGroupPopulationCentroidRepository) {
		this.siteService = siteService;
		this.projectService = projectService;
		this.siteIsochroneRepository = siteIsochroneRepository;
		this.siteBlockGroupDriveTimeRepository = siteBlockGroupDriveTimeRepository;
		this.blockGroupPopulationCentroidRepository = blockGroupPopulationCentroidRepository;
	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	private List<Site> getProjectSites(Integer projectId) {
		Project project = this.projectService.findOne(projectId);
		List<Store> stores = project.getStoreCasings().stream()
				.map(StoreCasing::getStore)
				.filter(store -> !store.getFloating() && store.getStoreType().equals(StoreType.ACTIVE))
				.collect(Collectors.toList());
		return stores.stream().map(Store::getSite).distinct()
				.collect(Collectors.toList());
	}

	public void generateIsochronesForProject(Integer projectId) {
		List<Site> sites = getProjectSites(projectId);

		long start = System.currentTimeMillis();
		int count = 0;
		for (Site site : sites) {
			count++;
			MtnLogger.info(String.valueOf(count));
			long elapsed = System.currentTimeMillis() - start;
			if (count / 300f >= 1 && elapsed < 60000) {
				try {
					long sleepTime = 60000 - elapsed;
					MtnLogger.info("Sleeping for " + sleepTime + " ms");
					TimeUnit.MILLISECONDS.sleep(sleepTime);
					count = 0;
					start = System.currentTimeMillis();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			generateIsochroneForSite(site, 15);
		}
	}

	public void generateDriveTimesForProject(Integer projectId) {
		List<Site> sites = this.getProjectSites(projectId);

		this.driveTimeRequestStartTime = System.currentTimeMillis();
		this.driveTimeRequestCount = 0;
		for (Site site : sites) {
			generateDriveTimesForSite(site);
		}
	}

	private void generateIsochroneForSite(Site site, Integer driveMinutes) {
		final String uri = "https://api.mapbox.com/isochrone/v1/mapbox/driving/" +
				site.getLongitude() + "," + site.getLatitude() +
				"?generalize=250&contours_minutes=" + driveMinutes + "&polygons=true&access_token=" + mapBoxToken;

		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class);

		if (result.startsWith("{\"features\"")) {

			SiteIsochrone siteIsochrone = new SiteIsochrone();
			siteIsochrone.setSite(site);
			siteIsochrone.setDriveMinutes(driveMinutes);
			siteIsochrone.setGeojson(result);

			siteIsochroneRepository.save(siteIsochrone);
		} else {
			MtnLogger.info(site.getId() + " failed: " + result);
		}
	}

	private String getSourceIndexes(int size) {
		StringBuilder indexes = new StringBuilder("1");
		int[] a = new int[size];
		for (int i = 1; i < a.length; i++) {
			indexes.append(";").append(i + 1);
		}
		return indexes.toString();
	}

	private void generateDriveTimesForSite(Site site) {
		Optional<SiteIsochrone> isochroneOptional = site.getSiteIsochrones().stream().filter(i -> i.getDriveMinutes() == 15).findFirst();
		if (isochroneOptional.isPresent()) {
			final String siteCoords = site.getLongitude() + "," + site.getLatitude();

			List<BlockGroupPopulationCentroid> centroids = this.blockGroupPopulationCentroidRepository.findAllForSite(site.getId());
			// TODO request matrix with site location as source and all centroids as destinations (map-reduce)
			List<List<BlockGroupPopulationCentroid>> batches = Lists.partition(centroids, 24);
			for (List<BlockGroupPopulationCentroid> batch : batches) {

				// Limited to 60 requests per minute
				long elapsed = System.currentTimeMillis() - this.driveTimeRequestStartTime;
				if (this.driveTimeRequestCount / 60f >= 1 && elapsed < 60000) {
					try {
						long sleepTime = 60000 - elapsed;
						MtnLogger.info("Sleeping for " + sleepTime + " ms");
						TimeUnit.MILLISECONDS.sleep(sleepTime);
						this.driveTimeRequestCount = 0;
						this.driveTimeRequestStartTime = System.currentTimeMillis();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				this.processDriveTimeBatch(batch, siteCoords, site.getId());
			}
		} else {
			MtnLogger.warn("Site " + site.getId() + " has no isochrone!");
		}
	}

	private void processDriveTimeBatch(List<BlockGroupPopulationCentroid> batch, String siteCoords, Integer siteId) {
		Site site = this.siteService.findOneUsingSpecs(siteId);
		Optional<String> coordString = batch.stream().map(centroid -> centroid.getLongitude() + "," + centroid.getLatitude()).reduce((combined, coords) -> combined + ";" + coords);
		if (coordString.isPresent()) {
			MtnLogger.info(String.valueOf(++this.driveTimeRequestCount));
			String url = "https://api.mapbox.com/directions-matrix/v1/mapbox/driving/" + siteCoords + ";" + coordString.get() + "?sources=" + getSourceIndexes(batch.size())+ "&destinations=0&annotations=duration&access_token=" + mapBoxToken;
			String result = new RestTemplate().getForObject(url, String.class);
			try {
				JsonNode root = new ObjectMapper().readTree(result);
				JsonNode durations = root.get("durations");
				if (durations.isArray()) {
					for (int i = 0; i < batch.size(); i++) {
						// durations has a list of lists (one from each source (block group) to the site
						JsonNode duration = durations.get(i).get(0);
						BlockGroupPopulationCentroid centroid = batch.get(i);
						SiteBlockGroupDriveTime driveTime = new SiteBlockGroupDriveTime();
						driveTime.setSite(site);
						driveTime.setFips(centroid.getFips());
						driveTime.setDriveSeconds(duration.intValue());
						this.siteBlockGroupDriveTimeRepository.save(driveTime);
					}
				} else {
					MtnLogger.error("durations isn't an array!");
				}
			} catch (IOException e) {
				MtnLogger.error("Failed to parse distance matrix result", e);
			}
		} else {
			MtnLogger.warn("No coordinates!");
		}
	}

}
