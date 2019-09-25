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
import java.text.NumberFormat;
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

	private double getAttractionScore(StoreGravityData st, double distance, Float distanceFactor) {
		// TODO multiply exponent by CURVE. (Small curve = larger radius)
		return st.getSalesArea() / Math.pow(distance, distanceFactor);
	}

	private double[][] getMarketShares(List<BlockGroupPopulationCentroid> blockGroups, List<StoreGravityData> stores, Float distanceFactor) {
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
				attractionScores[b][s] = this.getAttractionScore(stores.get(s), distanceMatrix[b][s], distanceFactor);
			}
		}

		// Calculate market shares
		double sumOfMarketShares = 0.0;
		double[][] marketShares = new double[blockGroups.size()][stores.size()];
		for (int b = 0; b < blockGroups.size(); b++) {
			double sumOfAttractionScores = 0;
			for (int s = 0; s < stores.size(); s++) {
				sumOfAttractionScores += attractionScores[b][s];
			}
			for (int s = 0; s < stores.size(); s++) {
				marketShares[b][s] = attractionScores[b][s] / sumOfAttractionScores;
				sumOfMarketShares += marketShares[b][s];
			}
		}
		double averageMarketShare = sumOfMarketShares / (blockGroups.size() * stores.size());
		MtnLogger.info("Average Market Share: " + averageMarketShare);
		return marketShares;
	}

	private double[][] balance(double[][] dollarsMatrix, double[] blockGroupDollars, double[] storeVolumes, int round) {
		// Sum dollars by store
		double[] modelVolumes = new double[storeVolumes.length];
		for (int s = 0; s < storeVolumes.length; s++) {
			for (int b = 0; b < blockGroupDollars.length; b++) {
				modelVolumes[s] += dollarsMatrix[b][s];
			}
		}

		double[] storeFactors = new double[storeVolumes.length];
		for (int s = 0; s < storeVolumes.length; s++) {
			storeFactors[s] = storeVolumes[s] / modelVolumes[s];
		}

		double[] modelDollars = new double[blockGroupDollars.length];
		for (int b = 0; b < blockGroupDollars.length; b++) {
			for (int s = 0; s < storeVolumes.length; s++) {
				modelDollars[b] += dollarsMatrix[b][s];
			}
		}

		double[] blockGroupFactors = new double[blockGroupDollars.length];
		for (int b = 0; b < blockGroupDollars.length; b++) {
			blockGroupFactors[b] = blockGroupDollars[b] / modelDollars[b];
		}

		double sumOfAdjustedDollars = 0.0;
		double[][] adjustedDollarsMatrix = new double[blockGroupDollars.length][storeVolumes.length];
		for (int b = 0; b < blockGroupDollars.length; b++) {
			for (int s = 0; s < storeVolumes.length; s++) {
				adjustedDollarsMatrix[b][s] = dollarsMatrix[b][s] * storeFactors[s] * blockGroupFactors[b];
				sumOfAdjustedDollars += adjustedDollarsMatrix[b][s];
			}
		}

		LinearRegression volumeRegression = new LinearRegression(storeVolumes, modelVolumes);
		LinearRegression blockGroupRegression = new LinearRegression(blockGroupDollars, modelDollars);
		MtnLogger.info("Round " + String.format("%1$3s", round) +
				"\tv: " + String.format("%.3f", volumeRegression.R2()) +
				"\tb: " + String.format("%.3f", blockGroupRegression.R2()) +
				"\tsum: $" + NumberFormat.getNumberInstance(Locale.US).format(Math.round(sumOfAdjustedDollars)));

		boolean balanced = volumeRegression.R2() > 0.999 && blockGroupRegression.R2() > 0.999;
		if (!balanced && round <= 20) {
			return this.balance(adjustedDollarsMatrix, blockGroupDollars, storeVolumes, round + 1);
		} else {
			return adjustedDollarsMatrix;
		}
	}

	public List runModel(Integer projectId, Float bannerSisterFactor, Float fitSisterFactor, Float distanceFactor) {
		Query q = em.createNamedQuery("getStoreGravityDataForProjectBg")
				.setParameter("projectId", projectId);
		List<StoreGravityData> stores = q.getResultList();
		List<BlockGroupPopulationCentroid> blockGroups = blockGroupPopulationCentroidRepository.findAllForProject(projectId);

		// Calculate Market Shares
		double[][] startingMarketShares = this.getMarketShares(blockGroups, stores, distanceFactor);

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

		double sumOfDollars = 0.0;
		double[][] dollarsMatrix = new double[blockGroups.size()][stores.size()];
		for (int b = 0; b < blockGroups.size(); b++) {
			for (int s = 0; s < stores.size(); s++) {
				dollarsMatrix[b][s] = startingMarketShares[b][s] * adjustedBlockGroupDollars[b];
				sumOfDollars += dollarsMatrix[b][s];
			}
		}
		MtnLogger.info("Sum of Volumes: " + NumberFormat.getNumberInstance(Locale.US).format(totalMarketStoreVolume));
		MtnLogger.info("Sum of Dollars: " + NumberFormat.getNumberInstance(Locale.US).format(Math.round(sumOfDollars)));

		double[][] balancedDollarsMatrix = this.balance(dollarsMatrix, adjustedBlockGroupDollars, storeVolumes, 1);

		double[][] newMarketShares = new double[blockGroups.size()][stores.size()];
		double[][] marketShareRatios = new double[blockGroups.size()][stores.size()];
		double newMarketShareSum = 0.0;
		for (int b = 0; b < blockGroups.size(); b++) {
			double blockGroupSum = 0.0;
			for (int s = 0; s < stores.size(); s++) {
				blockGroupSum += balancedDollarsMatrix[b][s];
			}
			for (int s = 0; s < stores.size(); s++) {
				double newMarketShare = balancedDollarsMatrix[b][s] / blockGroupSum;
				newMarketShares[b][s] = newMarketShare;
				newMarketShareSum += newMarketShare;
				marketShareRatios[b][s] = newMarketShare / startingMarketShares[b][s];
			}
		}

		double newAverageMarketShare = newMarketShareSum / (blockGroups.size() * stores.size());
		MtnLogger.info("New Average Market Share: " + newAverageMarketShare);

		double[] storePowers = new double[stores.size()];
		for (int s = 0; s < stores.size(); s++) {
			double sumOfRatios = 0.0;
			for (int b = 0; b < blockGroups.size(); b++) {
				sumOfRatios += marketShareRatios[b][s];
			}
			storePowers[s] = sumOfRatios / blockGroups.size();
		}

		double[] dpsfs = stores.stream().map(st -> st.getVolume() / st.getSalesArea()).mapToDouble(v -> (double) v).toArray();

		LinearRegression powerRegression = new LinearRegression(storePowers, dpsfs);
		MtnLogger.info("Power regression: " + powerRegression.toString());

		List<Map<String, Object>> output = new ArrayList<>();
		for (int s = 0; s < stores.size(); s++) {
			Map<String, Object> values = new HashMap<>();
			StoreGravityData store = stores.get(s);
			values.put("storeId", store.getStoreId());
			values.put("storeName", store.getStoreName());
			values.put("dpsf", dpsfs[s]);
			values.put("power", storePowers[s]);
			values.put("bannerId", store.getBannerId());
			values.put("fit", store.getStoreFit());
			values.put("salesArea", store.getSalesArea());
			output.add(values);
		}

		return output;
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
