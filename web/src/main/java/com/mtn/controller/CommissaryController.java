package com.mtn.controller;

import com.mtn.service.CommissaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/commissary")
public class CommissaryController {

	private final CommissaryService commissaryService;

	@Autowired
	public CommissaryController(CommissaryService commissaryService) {
		this.commissaryService = commissaryService;
	}

	@GetMapping()
	public ResponseEntity scrapeCommissaryData() {
		this.commissaryService.scrapeCommissaryData();
		return ResponseEntity.ok("Scraping");
	}
}
