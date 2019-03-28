package com.mtn.controller;

import com.mtn.model.domain.Project;
import com.mtn.model.domain.ShoppingCenter;
import com.mtn.model.domain.Site;
import com.mtn.model.domain.Store;
import com.mtn.model.simpleView.SimpleSiteView;
import com.mtn.model.simpleView.SimpleStoreView;
import com.mtn.model.simpleView.SitePoint;
import com.mtn.model.view.ShoppingCenterView;
import com.mtn.model.view.SiteMergeRequest;
import com.mtn.model.view.SiteView;
import com.mtn.model.view.StoreView;
import com.mtn.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/site")
public class SiteController extends CrudController<Site, SiteView> {

	private final StoreService storeService;
	private final ProjectService projectService;
	private final ShoppingCenterService shoppingCenterService;
	private final MergeService mergeService;

	@Autowired
	public SiteController(SiteService siteService,
						  StoreService storeService,
						  ProjectService projectService,
						  MergeService mergeService,
						  ShoppingCenterService shoppingCenterService) {
		super(siteService, SiteView::new);
		this.storeService = storeService;
		this.projectService = projectService;
		this.shoppingCenterService = shoppingCenterService;
		this.mergeService = mergeService;
	}

	@GetMapping(value = "/{id}/store")
	public ResponseEntity<List<SimpleStoreView>> findAllStoresForSite(@PathVariable("id") Integer siteId) {
		List<Store> domainModels = storeService.findAllBySiteIdUsingSpecs(siteId);
		return ResponseEntity.ok(domainModels.stream().map(SimpleStoreView::new).collect(Collectors.toList()));
	}

	@PutMapping(value = "{siteId}", params = {"is-duplicate"})
	public ResponseEntity<SimpleSiteView> updateIsDuplicate(@PathVariable("siteId") Integer siteId, @RequestParam("is-duplicate") Boolean isDuplicate) {
		Site site = this.entityService.findOne(siteId);
		site.setDuplicate(isDuplicate);
		SiteView request = new SiteView(site);
		return ResponseEntity.ok(new SimpleSiteView(this.entityService.updateOne(request)));
	}

	@GetMapping(params = {"geojson"})
	public ResponseEntity<List<SimpleSiteView>> findAllWithinGeoJson(@RequestParam("geojson") String geoJson) {
		List<Site> sites = ((SiteService) this.entityService).findAllInGeoJson(geoJson);
		return ResponseEntity.ok(sites.stream().map(SimpleSiteView::new).collect(Collectors.toList()));
	}

	@GetMapping(value = "/points", params = {"north", "south", "east", "west"})
	public ResponseEntity<List<SitePoint>> findAllSitePointsWithinBounds(@RequestParam("north") Float north,
														@RequestParam("south") Float south,
														@RequestParam("east") Float east,
														@RequestParam("west") Float west) {
		List<Site> domainModels = ((SiteService) this.entityService).findAllInBoundsUsingSpecs(north, south, east, west);
		return ResponseEntity.ok(domainModels.stream().map(SitePoint::new).collect(Collectors.toList()));
	}

	@GetMapping(value = "/points", params = {"projectId"})
	public ResponseEntity<List<SitePoint>> findAllSitesPointsWithinProjectBoundary(@RequestParam Integer projectId) {
		Project project = this.projectService.findOne(projectId);
		if (project.getBoundary() == null) {
			throw new EntityNotFoundException(String.format("Project %s does not have a boundary", projectId));
		}
		List<Site> domainModels = ((SiteService) this.entityService).findAllInShape(project.getBoundary().getBoundary());
		return ResponseEntity.ok(domainModels.stream().map(SitePoint::new).collect(Collectors.toList()));
	}

	@Override
	@PostMapping
	final public ResponseEntity<SiteView> addOne(@RequestBody SiteView request) {
		ShoppingCenterView scRequest = new ShoppingCenterView();
		ShoppingCenter sc = shoppingCenterService.addOne(scRequest);
		Site domainModel = ((SiteService) this.entityService).addOne(request, sc);
		return ResponseEntity.ok(new SiteView(domainModel));
	}

	@PostMapping(value = "/assign-to-user")
	public ResponseEntity<List<SimpleSiteView>> assignToUser(@RequestBody List<Integer> siteIds, @RequestParam(value = "user-id", required = false) Integer userId) {
		List<Site> sites = ((SiteService) this.entityService).assignSitesToUser(siteIds, userId);
		return ResponseEntity.ok(sites.stream().map(SimpleSiteView::new).collect(Collectors.toList()));
	}

	@PostMapping(value = "{siteId}/assign-to-user")
	public ResponseEntity<SiteView> assignToUser(@PathVariable("siteId") Integer siteId,
															 @RequestParam(value = "user-id", required = false) Integer userId) {
		Site site = ((SiteService) this.entityService).assignSiteToUser(siteId, userId);
		return ResponseEntity.ok(new SiteView(site));
	}

	@PostMapping(value = "/{id}/store")
	public ResponseEntity<StoreView> addOneStoreToSite(
			@PathVariable("id") Integer siteId,
			@RequestParam(value = "overrideActiveStore", defaultValue = "false") Boolean overrideActiveStore,
			@RequestBody StoreView request) {
		Site site = this.entityService.findOne(siteId);
		Store domainModel = storeService.createStoreForSiteFromRequest(request, site, overrideActiveStore);
		return ResponseEntity.ok(new StoreView(domainModel));
	}

	@PostMapping("merge")
	public ResponseEntity<SiteView> mergeSites(@RequestBody SiteMergeRequest siteMergeRequest) {
		Site mergedSite = this.mergeService.mergeSites(siteMergeRequest);
		return ResponseEntity.ok(new SiteView(mergedSite));
	}

}
