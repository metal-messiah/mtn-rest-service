package com.mtn.controller;

import com.mtn.service.ChainXYService;
import com.mtn.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chainxy")
public class ChainXYController {

	private final ChainXYService chainXYService;
	private final SecurityService securityService;

	@Autowired
	public ChainXYController(ChainXYService chainXYService, SecurityService securityService) {
		this.chainXYService = chainXYService;
		this.securityService = securityService;
	}

	// Triggers a pull from planned grocery (also scheduled to run nightly)
	@PostMapping
	public ResponseEntity updatePlannedGrocerySources() {
		chainXYService.updateDbSources(securityService.getCurrentUser());
		return ResponseEntity.noContent().build();
	}

}
