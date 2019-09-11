package com.mtn.service;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.geojson.Polygon;
import com.mongodb.client.model.geojson.Position;
import com.mtn.model.domain.BlockGroupPopulationCentroid;
import com.mtn.model.domain.Boundary;
import com.mtn.model.domain.GravityData;
import com.mtn.model.domain.SiteBlockGroupDriveTime;
import com.mtn.repository.BlockGroupPopulationCentroidRepository;
import com.mtn.repository.BoundaryRepository;
import com.mtn.repository.SiteBlockGroupDriveTimeRepository;
import com.mtn.repository.StoreRepository;
import com.mtn.util.MtnLogger;
import javafx.util.Pair;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GravityService {

	@Value("${mongodb-host}")
	private String mongoDbHost;

	private final SiteBlockGroupDriveTimeRepository siteBlockGroupDriveTimeRepository;
	private final BlockGroupPopulationCentroidRepository blockGroupPopulationCentroidRepository;
	private final BoundaryRepository boundaryRepository;
	private final EntityManager em;

	@Autowired
	public GravityService(SiteBlockGroupDriveTimeRepository siteBlockGroupDriveTimeRepository,
						  BlockGroupPopulationCentroidRepository blockGroupPopulationCentroidRepository,
						  BoundaryRepository boundaryRepository,
						  StoreRepository storeRepository,
						  EntityManager em) {
		this.siteBlockGroupDriveTimeRepository = siteBlockGroupDriveTimeRepository;
		this.blockGroupPopulationCentroidRepository = blockGroupPopulationCentroidRepository;
		this.boundaryRepository = boundaryRepository;
		this.em = em;
	}

	public Map<String, Float> getStoreBlockGroupMarketShares(Integer projectId, Integer siteId,
															 float bannerSisterFactor, float fitSisterFactor,
															 float marketShareThreshold) {
		List<SiteBlockGroupDriveTime> dts = siteBlockGroupDriveTimeRepository.findAllBySiteId(siteId);;

		Map<String, Float> blockGroupMarketShares = new HashMap<>();
		// For every block group the store feeds
		dts.forEach(dt -> {
			String fips = dt.getFips();
			Query q = em.createNamedQuery("getGravityDataForProjectBg")
					.setParameter("projectId", projectId)
					.setParameter("fips", fips);
			List<GravityData> data = q.getResultList();

			Map<Integer, Float> siteScores = this.getBlockGroupSiteScores(data, bannerSisterFactor, fitSisterFactor);
			siteScores.values().stream().reduce(Float::sum).ifPresent(total -> {
				Float siteScore = siteScores.get(siteId);
				Float marketShare = siteScore / total;
				if (marketShare >= marketShareThreshold) {
					blockGroupMarketShares.put(fips, marketShare);
				}
			});
		});

		return blockGroupMarketShares;
	}

	public Map<String, Map<Integer, Float>> getCentroidAttractionTotalsForProject(Integer projectId, Float bannerSisterFactor, Float fitSisterFactor) {
		Query q = em.createNamedQuery("getGravityDataForProject")
				.setParameter("projectId", projectId);
		List<GravityData> data = q.getResultList();

		Map<String, Map<Integer, Float>> blockGroupSiteScores = new HashMap<>();

		List<String> bgFips = data.stream().map(GravityData::getFips).distinct().collect(Collectors.toList());
		bgFips.forEach(fips -> {
			List<GravityData> bgGds = data.stream().filter(gd -> gd.getFips().equals(fips)).collect(Collectors.toList());
			Map<Integer, Float> bgScores = this.getBlockGroupSiteScores(bgGds, bannerSisterFactor, fitSisterFactor);
			blockGroupSiteScores.put(fips, bgScores);
		});


		return blockGroupSiteScores;
	}

	public String getBlockGroupWeb(String fips,
								   Integer projectId,
								   float bannerSisterFactor,
								   float fitSisterFactor,
								   float marketShareThreshold) throws Exception {
		BlockGroupPopulationCentroid centroid = blockGroupPopulationCentroidRepository.findByFipsEquals(fips);
		Query q = em.createNamedQuery("getGravityDataForProjectBg")
				.setParameter("projectId", projectId)
				.setParameter("fips", fips);
		List<GravityData> data = q.getResultList();

		Map<Integer, Float> siteScores = getBlockGroupSiteScores(data, bannerSisterFactor, fitSisterFactor);
		Optional<Float> totalScoreOpt = siteScores.values().stream().reduce(Float::sum);
		if (!totalScoreOpt.isPresent()) {
			throw new Exception("No scores for this block group - probably need to generate more drive times!");
		}
		Float totalScore = totalScoreOpt.get();

		Document d = new Document();
		d.append("type", "FeatureCollection");
		List<Document> features = new ArrayList<>();

		for (GravityData gd : data) {
			Float siteScore = siteScores.get(gd.getSiteId());
			Float marketShare = (siteScore != null ? siteScore : 0.0f) / totalScore;

			if (marketShare >= marketShareThreshold) {
				Document feature = new Document();
				feature.append("type", "Feature");

				Document properties = new Document();
				properties.append("score", siteScores.get(gd.getSiteId()));
				properties.append("marketShare", marketShare);
				feature.append("properties", properties);

				Document geometry = new Document();
				geometry.append("type", "LineString");
				List<Float> centroidCoords = Arrays.asList(centroid.getLongitude(), centroid.getLatitude());
				List<Float> siteCoords = Arrays.asList(gd.getLongitude(), gd.getLatitude());
				geometry.append("coordinates", Arrays.asList(centroidCoords, siteCoords));

				feature.append("geometry", geometry);
				features.add(feature);
			}
		}

		d.append("features", features);

		return d.toJson();
	}

	public String getBlockGroupGeoJsonForProject(Integer projectId) {
		MongoClient mongoClient = new MongoClient(mongoDbHost);
		MongoDatabase database = mongoClient.getDatabase("geodb");
		MongoCollection<Document> collection = database.getCollection("blockGroupBoundaries");

		Boundary b = this.boundaryRepository.getBoundaryForProject(projectId);
		List<Position> ps = Arrays.stream(b.getBoundary().getCoordinates()).map(coordinate -> new Position(coordinate.x, coordinate.y)).collect(Collectors.toList());
		Polygon atlantaBoundary = new Polygon(ps);

		List<BlockGroupPopulationCentroid> centroids = this.blockGroupPopulationCentroidRepository.findAllForProject(projectId);
		Map<String, Pair<Float, Float>> centroidCenters = centroids.stream().collect(Collectors.toMap(c -> c.getFips(), c -> new Pair<>(c.getLatitude(), c.getLongitude())));

		Bson storeBgFilter = Filters.in("geoid", centroidCenters.keySet());
		Bson projectBoundsFilter = Filters.geoIntersects("boundary", atlantaBoundary);
		FindIterable<Document> result = collection.find(Filters.or(storeBgFilter, projectBoundsFilter));

		Document d = new Document();
		d.append("type", "FeatureCollection");

		List<Document> features = new ArrayList<>();

		for (Document document : result) {
			String geoid = document.getString("geoid");

			Document feature = new Document();
			feature.append("type", "Feature");

			Document properties = new Document();
			properties.append("fips", geoid);

			Pair<Float, Float> center = centroidCenters.get(geoid);
			if (center != null) {
				properties.append("lat", center.getKey());
				properties.append("lng", center.getValue());
			}

			feature.append("properties", properties);
			feature.append("geometry", document.get("boundary"));
			features.add(feature);
		}

		d.append("features", features);

		return d.toJson();
	}

	private Map<Integer, Float> getBlockGroupSiteScores(List<GravityData> data, float bannerSisterFactor, float fitSisterFactor) {
		// Final map of site scores that will be returned
		Map<Integer, Float> siteScores = new HashMap<>();

		// Empty maps to count the number of sister stores (by banner and fit) that are closer to the block group
		Map<Integer, Integer> closerBannerSisterCounts = new HashMap<>();
		Map<String, Integer> closerFitSisterCounts = new HashMap<>();

		// For each site-bg drive time starting with the shortest
		data.forEach(gd -> {
			// If there is a store and the store has a sales area then calculate a score, otherwise skip the store
			// Counter for sister stores (banner and fit)
			Integer closerBannerSisterCount = 0;
			Integer closerFitSisterCount = 0;

			// If the store has a banner, and it isn't the first of its banner
			Integer bannerId = gd.getBannerid();
			if (bannerId != null && closerBannerSisterCounts.get(bannerId) != null) {
				// Get the number of previously processed (closer) banner sister stores
				closerBannerSisterCount = closerBannerSisterCounts.get(bannerId);
			}

			// If the store has, and it isn't the first of its fit category
			String storeFit = gd.getStoreFit();
			if (storeFit != null && closerFitSisterCounts.get(storeFit) != null) {
				// Get the number of previously processed (closer) fit sister stores
				closerFitSisterCount = closerFitSisterCounts.get(storeFit);
			}

			// Attraction is proportional to the store's sales area
			Float numerator = gd.getAreaSales().floatValue();
			// Attraction is INVERSLY proportional to the drive time and the number of sister stores that are closer (banner and fit)
			Float denominator = gd.getDriveSeconds().floatValue() + (closerBannerSisterCount * bannerSisterFactor) + (closerFitSisterCount * fitSisterFactor);
			// Gravity model attraction score calculation
			Float attractionScore = numerator / denominator;
			// Save the score
			siteScores.put(gd.getSiteId(), attractionScore);

			// Increment the banner and fit count numbers
			if (bannerId != null) {
				closerBannerSisterCounts.put(bannerId, closerBannerSisterCount + 1);
			}
			if (storeFit != null) {
				closerFitSisterCounts.put(storeFit, closerFitSisterCount + 1);
			}
		});
		return siteScores;
	}

}
