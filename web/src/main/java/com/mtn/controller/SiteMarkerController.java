package com.mtn.controller;

import com.mtn.model.domain.Site;
import com.mtn.model.simpleView.SiteMarker;
import com.mtn.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/site-marker")
public class SiteMarkerController {

	private final SiteService siteService;

	@Autowired
	public SiteMarkerController(SiteService siteService) {
		this.siteService = siteService;
	}

	@GetMapping(params = {"north", "south", "east", "west"})
	public ResponseEntity<Page<SiteMarker>> findAllInBounds(@RequestParam("north") Float north,
															@RequestParam("south") Float south,
															@RequestParam("east") Float east,
															@RequestParam("west") Float west,
															Pageable page) {
		Page<Site> domainModels = this.siteService.findAllInBoundsUsingSpecs(page, north, south, east, west);
		return ResponseEntity.ok(domainModels.map(SiteMarker::new));
	}

	@GetMapping(params = {"latitude", "longitude", "radiusMeters"})
	public ResponseEntity<Page<SiteMarker>> findAllInRadius(Pageable page,
															@RequestParam("latitude") Float latitude,
															@RequestParam("longitude") Float longitude,
															@RequestParam("radiusMeters") Float radiusMeters) {
		Page<Site> sites = siteService.findAllInRadius(page, latitude, longitude, radiusMeters);
		return ResponseEntity.ok(sites.map(SiteMarker::new));
	}

	@GetMapping(params = {"geojson"})
	public ResponseEntity<Page<SiteMarker>> findAllInGeoJson(Pageable page, @RequestParam("geojson") String geoJson) {
		Page<Site> sites = siteService.findAllInGeoJson(page, geoJson);
		return ResponseEntity.ok(sites.map(SiteMarker::new));
	}

	@GetMapping(params = {"site-id"})
	public ResponseEntity<SiteMarker> findBySiteId(@RequestParam("site-id") Integer siteId) {
		Site site = this.siteService.findOneUsingSpecs(siteId);
		return ResponseEntity.ok(new SiteMarker(site));
	}

}
