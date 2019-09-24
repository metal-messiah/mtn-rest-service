package com.mtn.service;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.geojson.Polygon;
import com.mongodb.client.model.geojson.Position;
import com.mtn.model.domain.*;
import com.mtn.repository.BlockGroupPopulationCentroidRepository;
import com.mtn.repository.BoundaryRepository;
import com.mtn.repository.SiteBlockGroupDriveTimeRepository;
import com.mtn.util.LinearRegression;
import com.mtn.util.MtnLogger;
import javafx.util.Pair;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.geolatte.geom.M;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GravityService {

	private final float DEG_2_RAD = (float) Math.PI / 180f;
	private final float EARTH_RADIUS = 3958.8f;

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
						  EntityManager em) {
		this.siteBlockGroupDriveTimeRepository = siteBlockGroupDriveTimeRepository;
		this.blockGroupPopulationCentroidRepository = blockGroupPopulationCentroidRepository;
		this.boundaryRepository = boundaryRepository;
		this.em = em;
	}

	public Map<String, Float> getStoreBlockGroupMarketShares(Integer projectId, Integer siteId,
															 float bannerSisterFactor, float fitSisterFactor,
															 float marketShareThreshold) {
		// Get all driveTimes for the site (will only include those that have been calculated from isochrone
		List<SiteBlockGroupDriveTime> dts = siteBlockGroupDriveTimeRepository.findAllBySiteId(siteId);

		// Storage for block group marketShares <fips, marketShare>
		Map<String, Float> blockGroupMarketShares = new HashMap<>();

		// For every block group the store feeds
		dts.forEach(dt -> {
			Query q = em.createNamedQuery("getGravityDataForProjectBg")
					.setParameter("projectId", projectId)
					.setParameter("fips", dt.getFips());
			List<GravityData> data = q.getResultList();

			Map<Integer, Float> siteScores = this.getBlockGroupSiteScores(data, bannerSisterFactor, fitSisterFactor);
			siteScores.values().stream().reduce(Float::sum).ifPresent(total -> {
				Float siteScore = siteScores.get(siteId);
				Float marketShare = siteScore / total;
				if (marketShare >= marketShareThreshold) {
					blockGroupMarketShares.put(dt.getFips(), marketShare);
				}
			});
		});

		return blockGroupMarketShares;
	}

	private float getHaversineDistance(BlockGroupPopulationCentroid centroid, StoreGravityData store) {
		double dLat = this.DEG_2_RAD * (store.getLatitude() - centroid.getLatitude());
		double dLng = this.DEG_2_RAD * (store.getLongitude() - centroid.getLongitude());

		double a = Math.pow(Math.sin(dLat / 2), 2) + (Math.cos(this.DEG_2_RAD * store.getLatitude()) * Math.cos(this.DEG_2_RAD * centroid.getLatitude()) * Math.pow(Math.sin(dLng / 2), 2));
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return (float) (this.EARTH_RADIUS * c);
	}

	private double getAttractionScore(StoreGravityData st, double distance) {
		// TODO multiply exponent by CURVE. (Small curve = larger radius)
		return st.getSalesArea() / Math.pow(distance, 2);
	}

	private double[][] getMarketShares(List<BlockGroupPopulationCentroid> blockGroups, List<StoreGravityData> stores) {
		// Generate distance matrix (BlockGroup > Store)
		double[][] distanceMatrix = new double[blockGroups.size()][stores.size()];
		for (int b = 0; b < blockGroups.size(); b++) {
			for (int s = 0; s < stores.size(); s++) {
				distanceMatrix[b][s] = this.getHaversineDistance(blockGroups.get(b), stores.get(s));
			}
		}

		// Generate attraction scores
		double[][] attractionScores = new double[blockGroups.size()][stores.size()];
		for (int b = 0; b < blockGroups.size(); b++) {
			for (int s = 0; s < stores.size(); s++) {
				attractionScores[b][s] = this.getAttractionScore(stores.get(s), distanceMatrix[b][s]);
			}
		}

		// Calculate market shares
		double[][] marketShares = new double[blockGroups.size()][stores.size()];
		for (int b = 0; b < blockGroups.size(); b++) {
			double sumOfAttractionScores = 0;
			for (int s = 0; s < stores.size(); s++) {
				sumOfAttractionScores += attractionScores[b][s];
			}
			for (int s = 0; s < stores.size(); s++) {
				marketShares[b][s] = attractionScores[b][s] / sumOfAttractionScores;
			}
		}
		return marketShares;
	}

	private void balance(double[][] dollarsMatrix, double[] blockGroupDollars, double[] storeVolumes, int round) {
		// Sum dollars by store
		double[] modelVolumes = new double[storeVolumes.length];
		for (int s = 0; s < storeVolumes.length; s++) {
			for (int b = 0; b < blockGroupDollars.length; b++) {
				modelVolumes[s] += dollarsMatrix[b][s];
			}
		}
		LinearRegression volumeRegression = new LinearRegression(storeVolumes, modelVolumes);
		MtnLogger.info("Round " + round + " volume regression:" + volumeRegression.toString());

		double[] storeFactors = new double[storeVolumes.length];
		for (int s = 0; s < storeVolumes.length; s++) {
			storeFactors[s] = storeVolumes[s] / modelVolumes[s];
		}

		double[][] adjustedDollarsMatrix = new double[blockGroupDollars.length][storeVolumes.length];
		for (int b = 0; b < blockGroupDollars.length; b++) {
			for (int s = 0; s < storeVolumes.length; s++) {
				adjustedDollarsMatrix[b][s] = dollarsMatrix[b][s] * storeFactors[s];
			}
		}

		double[] modelDollars = new double[blockGroupDollars.length];
		for (int b = 0; b < blockGroupDollars.length; b++) {
			for (int s = 0; s < storeVolumes.length; s++) {
				modelDollars[b] += adjustedDollarsMatrix[b][s];
			}
		}

		LinearRegression blockGroupRegression = new LinearRegression(blockGroupDollars, modelDollars);
		MtnLogger.info("Round " + round + " blockGroup regression:" + blockGroupRegression.toString());

		double[] blockGroupFactors = new double[blockGroupDollars.length];
		for (int b = 0; b < blockGroupDollars.length; b++) {
			blockGroupFactors[b] = blockGroupDollars[b] / modelDollars[b];
		}

		double[][] blockGroupAdjustedDollarsMatrix = new double[blockGroupDollars.length][storeVolumes.length];
		for (int b = 0; b < blockGroupDollars.length; b++) {
			for (int s = 0; s < storeVolumes.length; s++) {
				blockGroupAdjustedDollarsMatrix[b][s] = adjustedDollarsMatrix[b][s] * blockGroupFactors[b];
			}
		}

		if ((volumeRegression.R2() > 0.999 && blockGroupRegression.R2() > 0.999) || round > 10) {
			return;
		} else {
			this.balance(blockGroupAdjustedDollarsMatrix, blockGroupDollars, storeVolumes, round + 1);
		}
	}

	public List runModel(Integer projectId, Float bannerSisterFactor, Float fitSisterFactor) {
		Query q = em.createNamedQuery("getStoreGravityDataForProjectBg")
				.setParameter("projectId", projectId);
		List<StoreGravityData> stores = q.getResultList();
		List<BlockGroupPopulationCentroid> blockGroups = blockGroupPopulationCentroidRepository.findAllForProject(projectId);

		// Calculate Market Shares
		double[][] marketShares = this.getMarketShares(blockGroups, stores);

		// Balance
		// Sum of all store volumes
		Float totalMarketStoreVolume = stores.stream().map(s -> new Float(s.getVolume())).reduce(Float::sum).orElse(0.0f);
		// Sum of all available dollars for all block groups
		Float totalMarketBgVolume = blockGroups.stream().map(c -> c.getPcw() * c.getPopulation()).reduce(Float::sum).orElse(0.0f);
		// Factor to adjust available dollars
		double bgDollarFactor = totalMarketStoreVolume / totalMarketBgVolume;
		double[] adjustedBlockGroupDollars = new double[blockGroups.size()];
		for (int b = 0; b < blockGroups.size(); b++) {
			BlockGroupPopulationCentroid blockGroup = blockGroups.get(b);
			adjustedBlockGroupDollars[b] = blockGroup.getPcw() * blockGroup.getPopulation() * bgDollarFactor;
		}
		double[] storeVolumes = new double[stores.size()];
		for (int s = 0; s < stores.size(); s++) {
			storeVolumes[s] = stores.get(s).getVolume();
		}

		double[][] dollarsMatrix = new double[blockGroups.size()][stores.size()];
		for (int b = 0; b < blockGroups.size(); b++) {
			for (int s = 0; s < stores.size(); s++) {
				dollarsMatrix[b][s] = marketShares[b][s] * adjustedBlockGroupDollars[b];
			}
		}

		this.balance(dollarsMatrix, adjustedBlockGroupDollars, storeVolumes, 1);

//		// ----------------------------------------------------
//
//		// Primary matrix for balancing
//		List<List<Double>> storeDollarsFromBg = new ArrayList<>();
//		// For each block group
//		for (int i = 0; i < blockGroups.size(); i++) {
//			// Get the block group
//			BlockGroupPopulationCentroid centroid = blockGroups.get(i);
//			// Get the list of store market shares
//			List<Double> storeMarketShares = marketShares.get(i);
//			// Calculate the block group's adjusted available dollars
//			Double availableDollars = centroid.getPopulation() * centroid.getPcw() * bgDollarFactor;
//			// Calculate how many dollars go to each store
//			List<Double> bgStoreDollars = storeMarketShares.stream().map(ms -> ms * availableDollars).collect(Collectors.toList());
//			storeDollarsFromBg.add(bgStoreDollars);
//		}
//
//		// Sum up by store
//		List<Double> storeDollarsSum = stores.stream().map(sd -> 0.0).collect(Collectors.toList());
//		storeDollarsFromBg.forEach(storeDollars -> {
//			for (int i = 0; i < storeDollars.size(); i++) {
//				storeDollarsSum.set(i, storeDollarsSum.get(i) + storeDollars.get(i));
//			}
//		});
//
//		// Power???
//		List<Double> storeFactors = new ArrayList<>();
//		for (int i = 0; i < stores.size(); i++) {
//			storeFactors.add(stores.get(i).getVolume() / storeDollarsSum.get(i));
//		}
//
//		List<List<Double>> adjustedBgStoreDollars = storeDollarsFromBg.stream().map(bgStoreDollars -> {
//			List<Double> adjustedDollars = new ArrayList<>();
//			for (int i = 0; i < storeFactors.size(); i++) {
//				adjustedDollars.add(bgStoreDollars.get(i) * storeFactors.get(i));
//			}
//			return adjustedDollars;
//		}).collect(Collectors.toList());
//
//		List<Double> adustedBgDollars = adjustedBgStoreDollars.stream()
//				.map(storeDollarList -> storeDollarList.stream().reduce(Double::sum).orElse(0.0))
//				.collect(Collectors.toList());

		return new ArrayList();
	}

	public List<Map<String, Object>> getCalculatedModelData(Integer projectId, Float bannerSisterFactor, Float fitSisterFactor) {
		List<Map<String, Object>> blockGroupSiteScores = new ArrayList<>();
//		blockGroupSiteScores.add(new Object[]{"Fips", "siteId", "siteScore", "marketShare", "customers", "dollars"});

		Query q = em.createNamedQuery("getGravityDataForProject")
				.setParameter("projectId", projectId);
		List<GravityData> data = q.getResultList();

		// Generate list of fips codes for all block groups in project
		List<String> bgFips = data.stream().map(GravityData::getFips).distinct().collect(Collectors.toList());

		List<BlockGroupPopulationCentroid> centroids = this.blockGroupPopulationCentroidRepository.findAllByFipsIn(bgFips);
		// For each block group
		centroids.forEach(centroid -> {
			// Get all GravityData records for the block group
			List<GravityData> bgGds = data.stream().filter(gd -> gd.getFips().equals(centroid.getFips())).collect(Collectors.toList());
			// Get scores for each record
			Map<Integer, Float> bgScores = this.getBlockGroupSiteScores(bgGds, bannerSisterFactor, fitSisterFactor);

			// Centroid attraction score sum
			Float totalScore = bgScores.values().stream().reduce(Float::sum).orElse(0.0f);

			Map<Integer, Object[]> bgSiteValues = new HashMap<>();
			bgScores.keySet().forEach(siteId -> {
				Float siteScore = bgScores.get(siteId);
				float marketShare = siteScore / totalScore;
				Float customers = marketShare * centroid.getPopulation();
				Float dollars = customers * centroid.getPcw();
				Map<String, Object> values = new HashMap<>();
				values.put("fips", centroid.getFips());
				values.put("siteId", siteId);
				values.put("siteScore", siteScore);
				values.put("marketShare", marketShare);
				values.put("customers", customers.intValue());
				values.put("dollars", dollars.intValue());
				blockGroupSiteScores.add(values);
			});
		});
		return this.aggregateCalculatedVolumes(blockGroupSiteScores, projectId);
	}

	private List<Map<String, Object>> aggregateCalculatedVolumes(List<Map<String, Object>> modelData, Integer projectId) {
		List<Map<String, Object>> storeRecords = new ArrayList<>();

		Map<Integer, Integer> modelCustomers = modelData.stream()
				.collect(Collectors.groupingBy(m -> (Integer) m.get("siteId"), Collectors.summingInt(m -> (Integer) m.get("customers"))));
		Map<Integer, Integer> modelVolumes = modelData.stream()
				.collect(Collectors.groupingBy(m -> (Integer) m.get("siteId"), Collectors.summingInt(m -> (Integer) m.get("dollars"))));

		List<Integer> siteIds = modelData.stream().map(m -> (Integer) m.get("siteId")).distinct().collect(Collectors.toList());

		// SiteId, VolumeTotal
		List<Tuple> casedVolumes = em.createNativeQuery("select " +
				"st.site_id as siteId, " +
				"st.store_id as storeId, " +
				"st.store_name as storeName, " +
				"st.area_sales as salesArea, " +
				"IFNULL(st.store_fit, 'No Fit') as storeFit, " +
				"IFNULL(st.banner_id, 0) as bannerId, " +
				"v.volume_total as volumeTotal " +
				"from store_volume v " +
				"join store_casing stc on stc.store_volume_id = v.store_volume_id " +
				"join store_casing_project scp on scp.store_casing_id = stc.store_casing_id " +
				"join store st on st.store_id = stc.store_id " +
				"where scp.project_id = :projectId " +
				"and st.site_id in :siteIds " +
				"and stc.deleted_date is null " +
				"and v.deleted_date is null", Tuple.class)
				.setParameter("projectId", projectId)
				.setParameter("siteIds", siteIds)
				.getResultList();

		double[] x = new double[casedVolumes.size()];
		double[] y = new double[casedVolumes.size()];

		for (int i = 0; i < casedVolumes.size(); i++) {
			Tuple v = casedVolumes.get(i);
			Map<String, Object> record = new HashMap<>();
			Integer siteId = (Integer) v.get("siteId");
			record.put("siteId", siteId);
			record.put("storeId", v.get("storeId"));
			record.put("storeName", v.get("storeName"));
			record.put("salesArea", v.get("salesArea"));
			record.put("storeFit", v.get("storeFit"));
			record.put("bannerId", v.get("bannerId"));
			record.put("casedVolume", v.get("volumeTotal"));
			record.put("modelVolume", modelVolumes.get(siteId));
			record.put("modelCustomers", modelCustomers.get(siteId));
			storeRecords.add(record);

			x[i] = modelVolumes.get(siteId);
			y[i] = (double) (int) v.get("volumeTotal");
		}
		LinearRegression lr = new LinearRegression(x, y);
		MtnLogger.info(lr.toString());

		return storeRecords;
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
		Map<Integer, Float> closerBannerSisterCounts = new HashMap<>();
		Map<String, Float> closerFitSisterCounts = new HashMap<>();

		// For each site-bg drive time starting with the shortest
		data.forEach(gd -> {
			// If there is a store and the store has a sales area then calculate a score, otherwise skip the store
			// Counter for sister stores (banner and fit)
			float closerBannerSisterCount = 0;
			float closerFitSisterCount = 0;

			// If the store has a banner, and it isn't the first of its banner
			Integer bannerId = gd.getBannerId();
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

			float attractionScore = this.getAttractionScore(gd, closerBannerSisterCount, closerFitSisterCount, bannerSisterFactor, fitSisterFactor);

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

	/**
	 * [0, 0, 0.377]
	 */
	private Float getAttractionScorex(GravityData gd, float closerBannerSisterCount, float closerFitSisterCount, float bannerSisterFactor, float fitSisterFactor) {
		// Attraction is proportional to the store's sales area
		float numerator = gd.getAreaSales();
		// Attraction is INVERSLY proportional to the drive time and the number of sister stores that are closer (banner and fit)
		float denominator = gd.getDriveSeconds() + (closerBannerSisterCount * bannerSisterFactor) + (closerFitSisterCount * fitSisterFactor);
		// Gravity model attraction score calculation
		return numerator / denominator;
//		return numerator / ((float) Math.pow(denominator, 2));
	}

	/**
	 * [0, 0, 0.377]
	 */
	private Float getAttractionScore2(GravityData gd, float closerBannerSisterCount, float closerFitSisterCount, float bannerSisterFactor, float fitSisterFactor) {
		double score = (gd.getAreaSales() / gd.getDriveSeconds()) * Math.pow(bannerSisterFactor + 1, -closerBannerSisterCount);
		return (float) score;
	}

	/**
	 * [0, 0, 0.36]
	 */
	private Float getAttractionScore(GravityData gd, float closerBannerSisterCount, float closerFitSisterCount, float bannerSisterFactor, float fitSisterFactor) {
		double score = (gd.getAreaSales() * gd.getFitScore() / gd.getDriveSeconds()) * Math.pow(bannerSisterFactor + 1, -closerBannerSisterCount);
		return (float) score;
	}


}
