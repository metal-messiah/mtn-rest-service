package com.mtn.controller;

import com.mtn.model.simpleView.SimpleStoreView;
import com.mtn.model.view.SourceUpdatable;
import com.mtn.service.PlannedGroceryService;
import com.mtn.service.SecurityService;
import com.mtn.service.SourceUpdatableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/source-updatable")
public class SourceUpdatableController {

	private final SourceUpdatableService sourceUpdatableService;

	@Autowired
	public SourceUpdatableController(SourceUpdatableService sourceUpdatableService) {
		this.sourceUpdatableService = sourceUpdatableService;
	}

	@GetMapping(value = "updatable", params = {"store-id"})
	public ResponseEntity getUpdatableByStoreId(@RequestParam("store-id") Integer storeId) {
		return ResponseEntity.ok(sourceUpdatableService.getUpdatableByStoreId(storeId));
	}

	@GetMapping(value = "updatable", params = {"site-id"})
	public ResponseEntity getUpdatableBySiteId(@RequestParam("site-id") Integer siteId) {
		return ResponseEntity.ok(sourceUpdatableService.getUpdatableBySiteId(siteId));
	}

	@GetMapping(value = "updatable", params = {"shopping-center-id"})
	public ResponseEntity getUpdatableByShoppingCenterId(@RequestParam("shopping-center-id") Integer shoppingCenterId) {
		return ResponseEntity.ok(sourceUpdatableService.getUpdatableByShoppingCenterId(shoppingCenterId));
	}

	@PostMapping(value = "updatable")
	public ResponseEntity<SimpleStoreView> updateFromUpdatable(@RequestBody SourceUpdatable updatable) {
		return ResponseEntity.ok(new SimpleStoreView(sourceUpdatableService.updateFromUpdatable(updatable)));
	}

}
