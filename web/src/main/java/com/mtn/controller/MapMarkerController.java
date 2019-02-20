package com.mtn.controller;

import com.mtn.model.domain.Site;
import com.mtn.model.simpleView.SiteMarker;
import com.mtn.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/map-marker")
public class MapMarkerController {

	private final SiteService siteService;

	@Autowired
	public MapMarkerController(SiteService siteService) {
		this.siteService = siteService;
	}

	@GetMapping(params = {"north", "south", "east", "west"})
	public ResponseEntity<Page<SiteMarker>> findAllSitePointsWithinBounds(@RequestParam("north") Float north,
																		  @RequestParam("south") Float south,
																		  @RequestParam("east") Float east,
																		  @RequestParam("west") Float west,
																		  Pageable page) {
		Page<Site> domainModels = this.siteService.findAllInBoundsUsingSpecs(page, north, south, east, west);
		return ResponseEntity.ok(domainModels.map(SiteMarker::new));
	}

}
