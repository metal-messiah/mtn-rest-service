package com.mtn.controller;

import com.mtn.service.MapBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/map-box")
public class MapBoxController {

	private final MapBoxService mapBoxService;

	@Autowired
	public MapBoxController(MapBoxService mapBoxService) {
		this.mapBoxService = mapBoxService;
	}

	@GetMapping("/project/{projectId}/isochrones")
	public ResponseEntity generateIsochronesForProject(@PathVariable("projectId") Integer projectId) {
		this.mapBoxService.generateIsochronesForProject(projectId);
		return ResponseEntity.ok("Isochrones generated");
	}

	@GetMapping("/project/{projectId}/drive-times")
	public ResponseEntity generateDriveTimesForProject(@PathVariable("projectId") Integer projectId) {
		this.mapBoxService.generateDriveTimesForProject(projectId);
		return ResponseEntity.ok("Drive Times generated");
	}

}
