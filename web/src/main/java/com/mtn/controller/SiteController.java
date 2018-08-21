package com.mtn.controller;

import com.mtn.model.domain.Project;
import com.mtn.model.domain.ShoppingCenter;
import com.mtn.model.domain.Site;
import com.mtn.model.domain.Store;
import com.mtn.model.simpleView.SimpleSiteView;
import com.mtn.model.simpleView.SimpleStoreView;
import com.mtn.model.simpleView.SitePoint;
import com.mtn.model.view.ShoppingCenterView;
import com.mtn.model.view.SiteView;
import com.mtn.model.view.StoreView;
import com.mtn.service.ProjectService;
import com.mtn.service.ShoppingCenterService;
import com.mtn.service.SiteService;
import com.mtn.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 4/25/2017.
 */
@RestController
@RequestMapping("/api/site")
public class SiteController extends CrudControllerImpl<Site> {

	private final SiteService siteService;
	private final StoreService storeService;
	private final ProjectService projectService;
	private final ShoppingCenterService shoppingCenterService;

	@Autowired
	public SiteController(SiteService siteService,
						  StoreService storeService,
						  ProjectService projectService,
						  ShoppingCenterService shoppingCenterService) {
		this.siteService = siteService;
		this.storeService = storeService;
		this.projectService = projectService;
		this.shoppingCenterService = shoppingCenterService;
	}

	@PostMapping(params = {"shopping-center-id"})
	final public ResponseEntity addOne(@RequestBody SiteView request,
									   @RequestParam(required = false, value = "shopping-center-id") Integer shoppingCenterId) {
		ShoppingCenter sc;
		if (shoppingCenterId != null) {
			sc = shoppingCenterService.findOne(shoppingCenterId);
		} else {
			sc = shoppingCenterService.addOne(new ShoppingCenterView(new ShoppingCenter()));
		}
		Site domainModel = this.siteService.addOne(request, sc);
		return ResponseEntity.ok(getViewFromModel(domainModel));
	}

	@PostMapping(value = "/{id}/store")
	public ResponseEntity addOneStoreToSite(
			@PathVariable("id") Integer siteId,
			@RequestParam(value = "overrideActiveStore", defaultValue = "false") Boolean overrideActiveStore,
			@RequestBody StoreView request) {
		Site site = siteService.findOne(siteId);
		Store domainModel = storeService.createStoreForSiteFromRequest(request, site, overrideActiveStore);
		return ResponseEntity.ok(new StoreView(domainModel));
	}

	@RequestMapping(value = "/{id}/store", method = RequestMethod.GET)
	public ResponseEntity findAllStoresForSite(@PathVariable("id") Integer siteId) {
		List<Store> domainModels = storeService.findAllBySiteIdUsingSpecs(siteId);
		return ResponseEntity.ok(domainModels.stream().map(SimpleStoreView::new).collect(Collectors.toList()));
	}

	@PutMapping(value = "{siteId}", params = {"is-duplicate"})
	public ResponseEntity updateIsDuplicate(@PathVariable("siteId") Integer siteId, @RequestParam("is-duplicate") Boolean isDuplicate) {
		Site site = siteService.findOne(siteId);
		site.setDuplicate(isDuplicate);
		return ResponseEntity.ok(new SimpleSiteView(siteService.updateOne(site.getId(), site)));
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity findAll(@RequestParam(value = "north", required = false) Float north,
								  @RequestParam(value = "south", required = false) Float south,
								  @RequestParam(value = "east", required = false) Float east,
								  @RequestParam(value = "west", required = false) Float west,
								  @RequestParam(value = "no_stores", required = false) boolean noStores,
								  @RequestParam(value = "duplicate", required = false) boolean duplicate,
								  Pageable page) {
		Page<Site> domainModels;
		if (north != null && south != null && east != null && west != null) {
			domainModels = siteService.findAllInBoundsWithoutStoresUsingSpecs(north, south, east, west, noStores, page);
		} else if (duplicate) {
			domainModels = siteService.findAllDuplicatesUsingSpecs(page);
		} else {
			domainModels = siteService.findAllUsingSpecs(page);
		}
		return ResponseEntity.ok(domainModels.map(this::getSimpleViewFromModel));
	}

	@GetMapping(params = {"geojson"})
	public ResponseEntity findAllWithinGeoJson(@RequestParam("geojson") String geoJson) {
		List<Site> sites = siteService.findAllInGeoJson(geoJson);
		return ResponseEntity.ok(sites.stream().map(SimpleSiteView::new).collect(Collectors.toList()));
	}

	@RequestMapping(value = "/points", method = RequestMethod.GET, params = {"north", "south", "east", "west"})
	public ResponseEntity findAllSitePointsWithinBounds(@RequestParam("north") Float north,
														@RequestParam("south") Float south,
														@RequestParam("east") Float east,
														@RequestParam("west") Float west) {
		List<Site> domainModels = siteService.findAllInBoundsUsingSpecs(north, south, east, west);
		return ResponseEntity.ok(domainModels.stream().map(SitePoint::new).collect(Collectors.toList()));
	}

	@RequestMapping(value = "/points", method = RequestMethod.GET, params = {"projectId"})
	public ResponseEntity findAllSitesPointsWithinProjectBoundary(@RequestParam Integer projectId) {
		Project project = this.projectService.findOne(projectId);
		if (project.getBoundary() == null) {
			throw new EntityNotFoundException(String.format("Project %s does not have a boundary", projectId));
		}
		List<Site> domainModels = siteService.findAllInShape(project.getBoundary());
		return ResponseEntity.ok(domainModels.stream().map(SitePoint::new).collect(Collectors.toList()));
	}

	@Override
	public SiteService getEntityService() {
		return siteService;
	}

	@Override
	public Object getViewFromModel(Site model) {
		return new SiteView(model);
	}

	@Override
	public Object getSimpleViewFromModel(Site model) {
		return new SimpleSiteView(model);
	}

	@RequestMapping(value = "/assign-to-user", method = RequestMethod.POST)
	public ResponseEntity assignToUser(@RequestBody Integer[] siteIds, @RequestParam(value = "user-id", required = false) Integer userId) {
		List<Site> sites = siteService.assignSitesToUser(siteIds, userId);
		return ResponseEntity.ok(sites.stream().map(SimpleSiteView::new).collect(Collectors.toList()));
	}
}
