package com.mtn.controller;

import com.mtn.service.SiteWiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/site-wise")
public class SiteWiseController {

	private final SiteWiseService siteWiseService;


	@Autowired
	public SiteWiseController(SiteWiseService siteWiseService) {
		this.siteWiseService = siteWiseService;
	}

	@GetMapping()
	public ResponseEntity submitFile() {
		this.siteWiseService.buildAndTransmitExtraction();
		return ResponseEntity.ok("File Submitted");
	}

}
