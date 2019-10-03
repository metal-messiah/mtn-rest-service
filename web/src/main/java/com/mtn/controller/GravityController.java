package com.mtn.controller;

import com.mtn.service.GravityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gravity")
public class GravityController {

	final private GravityService gravityService;

	@Autowired
	public GravityController(GravityService gravityService) {
		this.gravityService = gravityService;
	}

	@GetMapping("scores")
	public ResponseEntity getStoreScores(@RequestParam("project-id") Integer projectId,
										 @RequestParam("site-id") Integer siteId,
										 @RequestParam("banner-sister-factor") float bannerSisterFactor,
										 @RequestParam("fit-sister-factor") float fitSisterFactor,
										 @RequestParam(name = "market-share-threshold", defaultValue = "0.0") float marketShareThreshold) {
		Map<String, Float> scores = this.gravityService.getStoreBlockGroupMarketShares(projectId, siteId, bannerSisterFactor, fitSisterFactor, marketShareThreshold);
		return ResponseEntity.ok(scores);
	}

	@GetMapping("run-model")
	public ResponseEntity runModel(@RequestParam("project-id") Integer projectId,
								   @RequestParam("banner-sister-factor") float bannerSisterFactor,
								   @RequestParam("fit-sister-factor") float fitSisterFactor,
								   @RequestParam("distance-factor") float distanceFactor) {
		try {
			Map modelData = this.gravityService.runModel(projectId, bannerSisterFactor, fitSisterFactor, distanceFactor);
			return ResponseEntity.ok(modelData);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}


	@GetMapping("attraction-totals")
	public ResponseEntity getAttractionTotalsForProject(@RequestParam("project-id") Integer projectId,
														@RequestParam("banner-sister-factor") float bannerSisterFactor,
														@RequestParam("fit-sister-factor") float fitSisterFactor) {
		try {
			List<Map<String, Object>> modelData = this.gravityService.getCalculatedModelData(projectId, bannerSisterFactor, fitSisterFactor);
			return ResponseEntity.ok(modelData);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}


	@GetMapping("block-group-web")
	public ResponseEntity getBlockGroupWeb(@RequestParam("fips") String fips,
										   @RequestParam("project-id") Integer projectId,
										   @RequestParam("banner-sister-factor") float bannerSisterFactor,
										   @RequestParam("fit-sister-factor") float fitSisterFactor,
										   @RequestParam(name = "market-share-threshold", defaultValue = "0.0") float marketShareThreshold
	) {
		try {
			String geoJson = this.gravityService.getBlockGroupWeb(fips, projectId, bannerSisterFactor, fitSisterFactor, marketShareThreshold);
			return ResponseEntity.ok(geoJson);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity getModelChoropleth(@RequestParam("project-id") Integer projectId) {
		try {
			String geoJson = this.gravityService.getBlockGroupGeoJsonForProject(projectId);
			return ResponseEntity.ok(geoJson);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
