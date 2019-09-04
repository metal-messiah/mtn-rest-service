package com.mtn.service;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.geojson.Polygon;
import com.mongodb.client.model.geojson.Position;
import com.mtn.model.domain.*;
import com.mtn.util.MtnLogger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GravityService {

	@Value("${mongodb-host}")
	private String mongoDbHost;

	@Autowired
	public GravityService() {
	}

	public String getModelChoropleth(Project project, Integer storeId) throws Exception {
		List<Store> stores = project.getStoreCasings().stream().map(StoreCasing::getStore).collect(Collectors.toList());
		Optional<Store> storeOpt = stores.stream().filter(store -> store.getId().equals(storeId)).findFirst();
		if (!storeOpt.isPresent()) {
			throw new Exception("Store not found in project " + project.getProjectName() + "!");
		}
		int targetStoreIndex = stores.indexOf(storeOpt.get());

		Optional<List<BlockGroupPopulationCentroid>> blockGroupsOptional = stores.stream()
				.map(store -> store.getSite().getCentroids())
				.reduce((prev, curr) -> {
					prev.addAll(curr);
					return prev;
				});
		if (!blockGroupsOptional.isPresent()) {
			throw new Exception("No block groups found for project " + project.getProjectName() + "!");
		}
		List<BlockGroupPopulationCentroid> centroids = blockGroupsOptional.get().stream().distinct().collect(Collectors.toList());

		float[][] attractionScores = new float[centroids.size()][stores.size()];
		HashMap<String, Float> blockGroupTotals = new HashMap<>();
		for (int centroidIndex = 0; centroidIndex < centroids.size(); centroidIndex++) {
			BlockGroupPopulationCentroid centroid = centroids.get(centroidIndex);
			float centroidAttractionSum = 0;
			for (int storeIndex = 0; storeIndex < stores.size(); storeIndex++) {
				Store store = stores.get(storeIndex);
				float attractionScore = 0.001f;
				Optional<SiteBlockGroupDriveTime> driveTimeOpt = store.getSite().getDriveTimes().stream()
						.filter(dt -> dt.getFips().equals(centroid.getFips()))
						.findFirst();
				if (driveTimeOpt.isPresent() && store.getAreaSales() != null) {
					float salesArea = store.getAreaSales().floatValue();
					float driveSeconds = driveTimeOpt.get().getDriveSeconds().floatValue();
					attractionScore = salesArea / driveSeconds;
				}
				attractionScores[centroidIndex][storeIndex] = attractionScore;
				centroidAttractionSum += attractionScore;
			}
			blockGroupTotals.put(centroid.getFips(), centroidAttractionSum);
		}

		HashMap<String, Float> targetStoreAttractionScores = new HashMap<>();
		for (int centroidIndex = 0; centroidIndex < centroids.size(); centroidIndex++) {
			BlockGroupPopulationCentroid centroid = centroids.get(centroidIndex);
			Float score = attractionScores[centroidIndex][targetStoreIndex];
			targetStoreAttractionScores.put(centroid.getFips(), score);
		}

		MongoClient mongoClient = new MongoClient(mongoDbHost);
		MongoDatabase database = mongoClient.getDatabase("geodb");
		MongoCollection<Document> collection = database.getCollection("blockGroupBoundaries");

		Boundary b = project.getBoundary();
		List<Position> ps = Arrays.stream(b.getBoundary().getCoordinates()).map(coordinate -> new Position(coordinate.x, coordinate.y)).collect(Collectors.toList());
		Polygon atlantaBoundary = new Polygon(ps);

		Bson filter = Filters.geoIntersects("boundary", atlantaBoundary);
		FindIterable<Document> result = collection.find(filter);

		Document d = new Document();
		d.append("type", "FeatureCollection");

		List<Document> features = new ArrayList<>();

		for (Document document : result) {
			String geoid = document.getString("geoid");
			Float score = targetStoreAttractionScores.get(geoid);
			Float blockGroupAttractionTotal = blockGroupTotals.get(geoid);

			Document feature = new Document();
			feature.append("type", "Feature");

			Document properties = new Document();
			properties.append("fips", geoid);
			properties.append("fill-opacity", 0.5);
			if (blockGroupAttractionTotal != null) {
				properties.append("attractionTotal", blockGroupAttractionTotal);
			}
			if (score != null) {
				properties.append("fill", this.getBgColor(score));
				properties.append("storeScore", score);
//				properties.append("marketShare", String.format("%.2f", score * 100));
			} else {
				properties.append("fill", "#555555");
				properties.append("marketShare", "0");
			}

			feature.append("properties", properties);
			feature.append("geometry", document.get("boundary"));
			features.add(feature);
		}

		d.append("features", features);

		return d.toJson();
	}

	private String getBgColor(float score) {
		if (score >= 0.66) return "#FF0000";
//		if (score >= 0.8) return "#FF1C00";
		if (score >= 0.32) return "#FF3800";
//		if (score >= 0.6) return "#FF5500";
		if (score >= 0.13) return "#FF7100";
//		if (score >= 0.4) return "#FF8D00";
		if (score >= 0.3) return "#FFAA00";
//		if (score >= 0.2) return "#FFC600";
//		if (score >= 0.1) return "#FFE200";
		return "#FFFF00";
	}


}
