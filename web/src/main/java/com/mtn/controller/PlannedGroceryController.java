package com.mtn.controller;

import com.mtn.model.simpleView.SimpleStoreView;
import com.mtn.model.view.PlannedGroceryUpdatable;
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
	public PlannedGroceryController(PlannedGroceryService plannedGroceryService, SecurityService securityService) {
		this.plannedGroceryService = plannedGroceryService;
		this.securityService = securityService;
	}

	// Triggers a pull from planned grocery (also scheduled to run nightly)
	@PostMapping
	public ResponseEntity updatePlannedGrocerySources() {
		plannedGroceryService.addAndUpdateSourcesFromPlannedGrocery(securityService.getCurrentUser());
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "updatable", params = {"store-id"})
	public ResponseEntity getUpdatableByStoreId(@RequestParam("store-id") Integer storeId) {
		return ResponseEntity.ok(plannedGroceryService.getUpdatableByStoreId(storeId));
	}

	@GetMapping(value = "updatable", params = {"site-id"})
	public ResponseEntity getUpdatableBySiteId(@RequestParam("site-id") Integer siteId) {
		return ResponseEntity.ok(plannedGroceryService.getUpdatableBySiteId(siteId));
	}

	@GetMapping(value = "updatable", params = {"shopping-center-id"})
	public ResponseEntity getUpdatableByShoppingCenterId(@RequestParam("shopping-center-id") Integer shoppingCenterId) {
		return ResponseEntity.ok(plannedGroceryService.getUpdatableByShoppingCenterId(shoppingCenterId));
	}

	@PostMapping(value = "updatable")
	public ResponseEntity<SimpleStoreView> updateFromUpdatable(@RequestBody PlannedGroceryUpdatable updatable) {
		return ResponseEntity.ok(new SimpleStoreView(plannedGroceryService.updateFromUpdatable(updatable)));
	}

	@GetMapping(value = "{objectId}")
	public ResponseEntity<String> getFeatureByObjectId(@PathVariable("objectId") String objectId) {
		try {
			return plannedGroceryService.getFeatureByObjectId(objectId);
		} catch (IOException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
