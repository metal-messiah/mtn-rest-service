package com.mtn.controller;

import com.mtn.constant.StoreType;
import com.mtn.model.domain.*;
import com.mtn.model.simpleView.*;
import com.mtn.model.view.*;
import com.mtn.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/store")
public class StoreController extends CrudController<Store, StoreView> {

	private final StoreSurveyService storeSurveyService;
	private final StoreVolumeService volumeService;
	private final StoreCasingService casingService;
	private final StoreModelService modelService;
	private final StoreCasingService storeCasingService;
	private final ProjectService projectService;
	private final StoreStatusService storeStatusService;
	private final BannerService bannerService;
	private final SiteService siteService;

	@Autowired
	public StoreController(StoreService storeService,
						   StoreSurveyService surveyService,
						   StoreVolumeService volumeService,
						   StoreCasingService casingService,
						   StoreModelService modelService,
						   StoreCasingService storeCasingService,
						   ProjectService projectService,
						   StoreStatusService storeStatusService,
						   BannerService bannerService,
						   SiteService siteService) {
		super(storeService, StoreView::new);
		this.storeSurveyService = surveyService;
		this.volumeService = volumeService;
		this.casingService = casingService;
		this.modelService = modelService;
		this.storeCasingService = storeCasingService;
		this.projectService = projectService;
		this.storeStatusService = storeStatusService;
		this.bannerService = bannerService;
		this.siteService = siteService;
	}

	@GetMapping(params = {"ids"})
	public ResponseEntity findListByIds(@RequestParam(value = "ids") List<Integer> ids) {
		List<Store> stores = ((StoreService) this.entityService).findAllByIdsUsingSpecs(ids);
		return ResponseEntity.ok(stores.stream().map(SimpleStoreView::new).collect(Collectors.toList()));
	}

	@GetMapping(params = {"north", "south", "east", "west"})
	public ResponseEntity findAllInBounds(
			@RequestParam("north") Float north,
			@RequestParam("south") Float south,
			@RequestParam("east") Float east,
			@RequestParam("west") Float west,
			@RequestParam("store_types") List<StoreType> storeTypes,
			@RequestParam(value = "include_project_ids", required = false) boolean includeProjectIds,
			Pageable page) {
		Page<Store> domainModels = ((StoreService) this.entityService).findAllOfTypesInBounds(north, south, east, west, storeTypes, page);
		if (includeProjectIds) {
			return ResponseEntity.ok(domainModels.map(SimpleStoreViewWithProjects::new));
		} else {
			return ResponseEntity.ok(domainModels.map(SimpleStoreView::new));
		}
	}

	@GetMapping(params = {"latitude", "longitude", "radiusMeters"})
	public ResponseEntity<Map<String, List<Integer>>> findAllIdsInRadius(@RequestParam("latitude") Float latitude,
																		 @RequestParam("longitude") Float longitude,
																		 @RequestParam("radiusMeters") Float radiusMeters,
																		 @RequestParam("active") boolean active,
																		 @RequestParam("future") boolean future,
																		 @RequestParam("historical") boolean historical) {
		List<Store> stores = ((StoreService) this.entityService).findAllInRadius(latitude, longitude, radiusMeters, active, future, historical);
		return ResponseEntity.ok(getIdsFromStores(stores));
	}

	@GetMapping(params = {"geojson"})
	public ResponseEntity<Map<String, List<Integer>>> findAllIdsInGeoJson(@RequestParam("geojson") String geoJson,
																		  @RequestParam("active") boolean active,
																		  @RequestParam("future") boolean future,
																		  @RequestParam("historical") boolean historical) {
		List<Store> stores = ((StoreService) this.entityService).findAllInGeoJson(geoJson, active, future, historical);
		return ResponseEntity.ok(getIdsFromStores(stores));
	}

	private Map<String, List<Integer>> getIdsFromStores(List<Store> stores) {
		List<Integer> storeIds = stores.stream().map(AuditingEntity::getId).distinct().collect(Collectors.toList());
		List<Integer> siteIds = stores.stream().map(store -> store.getSite().getId()).distinct().collect(Collectors.toList());
		Map<String, List<Integer>> ids = new HashMap<>();
		ids.put("storeIds", storeIds);
		ids.put("siteIds", siteIds);
		return ids;
	}

	@PutMapping("/{id}/validate")
	public ResponseEntity<SimpleStoreView> validate(@PathVariable("id") Integer storeId) {
		Store store = ((StoreService) this.entityService).validateStore(storeId);
		return ResponseEntity.ok(new SimpleStoreView(store));
	}

	@PutMapping("/{id}/invalidate")
	public ResponseEntity<SimpleStoreView> invalidate(@PathVariable("id") Integer storeId) {
		Store store = ((StoreService) this.entityService).invalidateStore(storeId);
		return ResponseEntity.ok(new SimpleStoreView(store));
	}

	@PostMapping(value = "/{id}/store-casings")
	public ResponseEntity createOneStoreCasingForStore(
			@PathVariable("id") Integer storeId,
			@RequestBody StoreCasingView request) {
		if (request.getStoreSurvey() != null) {
			throw new IllegalArgumentException("Do not include store survey. Will be provided by web service");
		}
		if (request.getShoppingCenterCasing() != null) {
			throw new IllegalArgumentException("Do not include shopping center casing. Will be provided by web service");
		}
		Store store = this.entityService.findOneUsingSpecs(storeId);

		// Create new Casing
		StoreCasing storeCasing = storeCasingService.addOneCasingToStore(request, store);

		// Add project(s)
		request.getProjects().forEach(project -> {
			Project p = projectService.findOne(project.getId());
			storeCasingService.addProject(storeCasing.getId(), p);
		});

		return ResponseEntity.ok(new StoreCasingView(storeCasing));
	}

	@GetMapping(value = "/{id}/store-casings")
	public ResponseEntity findAllCasingsForStore(@PathVariable("id") Integer storeId) {
		List<StoreCasing> domainModels = casingService.findAllByStoreIdUsingSpecs(storeId);
		return ResponseEntity.ok(domainModels.stream().map(SimpleStoreCasingView::new).collect(Collectors.toList()));
	}

	@GetMapping(value = "/{id}/store-model")
	public ResponseEntity findAllModelsForStore(@PathVariable("id") Integer storeId) {
		List<StoreModel> domainModels = modelService.findAllByStoreIdUsingSpecs(storeId);
		return ResponseEntity.ok(domainModels.stream().map(SimpleStoreModelView::new).collect(Collectors.toList()));
	}

	@PostMapping(value = "/{id}/store-surveys")
	public ResponseEntity addOneStoreSurveyToStore(@PathVariable("id") Integer storeId, @RequestBody StoreSurveyView request) {
		Store store = this.entityService.findOneUsingSpecs(storeId);
		StoreSurvey survey = storeSurveyService.addOneToStore(request, store);
		return ResponseEntity.ok(new StoreSurveyView(survey));
	}

	@GetMapping(value = "/{id}/store-surveys")
	public ResponseEntity findAllSurveysForStore(@PathVariable("id") Integer storeId) {
		List<StoreSurvey> domainModels = storeSurveyService.findAllByStoreIdUsingSpecs(storeId);
		return ResponseEntity.ok(domainModels.stream().map(SimpleStoreSurveyView::new).collect(Collectors.toList()));
	}

	@PutMapping(value = "/{storeId}/banner/{bannerId}")
	public ResponseEntity updateOneBanner(@PathVariable("storeId") Integer storeId, @PathVariable("bannerId") Integer bannerId) {
		Banner banner = bannerService.findOneUsingSpecs(bannerId);
		Store domainModel = ((StoreService) this.entityService).updateOneBanner(storeId, banner);
		return ResponseEntity.ok(new StoreView(domainModel));
	}

	@PostMapping(value = "/{id}/store-statuses")
	public ResponseEntity addOneStoreStatusToStore(@PathVariable("id") Integer storeId, @RequestBody StoreStatusView request) {
		Store store = this.entityService.findOneUsingSpecs(storeId);
		storeStatusService.addOneToStore(request, store);
		return ResponseEntity.ok(new StoreView(store));
	}

	@PutMapping(value = "{storeId}", params = {"is-float"})
	public ResponseEntity updateIsDuplicate(@PathVariable("storeId") Integer storeId, @RequestParam("is-float") Boolean isFloat) {
		Store store = this.entityService.findOneUsingSpecs(storeId);
		store.setFloating(isFloat);
		StoreView request = new StoreView(store);
		return ResponseEntity.ok(new SimpleStoreView(this.entityService.updateOne(request)));
	}

	@DeleteMapping(value = "/{id}/store-statuses/{statusId}")
	public ResponseEntity deleteStoreStatus(@PathVariable("id") Integer storeId, @PathVariable Integer statusId) {
		storeStatusService.deleteOne(statusId);
		Store domainModel = this.entityService.findOneUsingSpecs(storeId);
		return ResponseEntity.ok(new StoreView(domainModel));
	}

	@GetMapping(value = "/{id}/store-volumes")
	public ResponseEntity findAllVolumesForStore(@PathVariable("id") Integer storeId) {
		List<StoreVolume> domainModels = volumeService.findAllByStoreIdUsingSpecs(storeId);
		return ResponseEntity.ok(domainModels.stream().map(StoreVolumeView::new).collect(Collectors.toList()));
	}

	@PostMapping(value = "/{id}/store-volumes")
	public ResponseEntity addOneStoreVolumeToStore(@PathVariable("id") Integer storeId, @RequestBody StoreVolumeView request) {
		Store store = this.entityService.findOneUsingSpecs(storeId);
		volumeService.addOneToStore(request, store);
		return ResponseEntity.ok(new StoreView(store));
	}

	@DeleteMapping(value = "/{id}/store-volumes/{volumeId}")
	public ResponseEntity deleteStoreVolume(@PathVariable("id") Integer storeId, @PathVariable Integer volumeId) {
		volumeService.deleteOne(volumeId);
		Store domainModel = this.entityService.findOneUsingSpecs(storeId);
		return ResponseEntity.ok(new StoreView(domainModel));
	}

	@PutMapping("assign-to-user")
	public ResponseEntity<List<SimpleSiteView>> assignToUser(@RequestBody List<Integer> storeIds,
															  @RequestParam(value = "user-id", required = false) Integer userId) {
		List<Store> stores = ((StoreService) this.entityService).findAllByIdsUsingSpecs(storeIds);
		List<Integer> siteIds = stores.stream().map(store -> store.getSite().getId()).distinct().collect(Collectors.toList());
		List<Site> sites = this.siteService.assignSitesToUser(siteIds, userId);
		return ResponseEntity.ok(sites.stream().map(SimpleSiteView::new).collect(Collectors.toList()));
	}

}
