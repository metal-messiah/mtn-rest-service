package com.mtn.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.mtn.service.PlannedGroceryService;
import com.mtn.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/planned-grocery")
public class PlannedGroceryController {

	private final PlannedGroceryService plannedGroceryService;
	private final SecurityService securityService;

	@Autowired
	public PlannedGroceryController(PlannedGroceryService plannedGroceryService,
									SecurityService securityService) {
		this.plannedGroceryService = plannedGroceryService;
		this.securityService = securityService;
	}

	// Triggers a pull from planned grocery (also scheduled to run nightly)
	@PostMapping
	public ResponseEntity updatePlannedGrocerySources() {
		plannedGroceryService.addAndUpdateSourcesFromPlannedGrocery(securityService.getCurrentUser());
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "{objectId}")
	public JsonNode getFeatureByObjectId(@PathVariable("objectId") String objectId) throws IOException {
		return plannedGroceryService.getFeatureByObjectId(objectId);
	}

}
