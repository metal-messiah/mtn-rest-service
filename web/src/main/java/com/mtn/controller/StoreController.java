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

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 4/25/2017.
 */
@RestController
@RequestMapping("/api/store")
public class StoreController extends CrudControllerImpl<Store> {

	private final StoreService storeService;
	private final StoreSurveyService storeSurveyService;
	private final StoreVolumeService volumeService;
	private final StoreCasingService casingService;
	private final StoreModelService modelService;
	private final StoreCasingService storeCasingService;
	private final ProjectService projectService;
	private final StoreStatusService storeStatusService;
	private final BannerService bannerService;
	private final ShoppingCenterCasingService shoppingCenterCasingService;

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
						   ShoppingCenterCasingService shoppingCenterCasingService) {
		this.storeService = storeService;
		this.storeSurveyService = surveyService;
		this.volumeService = volumeService;
		this.casingService = casingService;
		this.modelService = modelService;
		this.storeCasingService = storeCasingService;
		this.projectService = projectService;
		this.storeStatusService = storeStatusService;
		this.bannerService = bannerService;
		this.shoppingCenterCasingService = shoppingCenterCasingService;
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
		Page<Store> domainModels = storeService.findAllOfTypesInBounds(north, south, east, west, storeTypes, page);
		if (includeProjectIds) {
			return ResponseEntity.ok(domainModels.map(SimpleStoreViewWithProjects::new));
		} else {
			return ResponseEntity.ok(domainModels.map(SimpleStoreView::new));
		}
	}

	@GetMapping(params = {"geojson"})
	public ResponseEntity findAllInGeoJson(@RequestParam("geojson") String geoJson) {
		List<Store> stores = storeService.findAllInGeoJson(geoJson);
		List<Integer> ids = stores.stream().map(AuditingEntity::getId).distinct().collect(Collectors.toList());
		return ResponseEntity.ok(ids);
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
		Store store = storeService.findOneUsingSpecs(storeId);
		// Add Casing to project(s)
		StoreCasing storeCasing = storeCasingService.addOneCasingToStore(request, store);
		request.getProjects().forEach(project -> projectService.addStoreCasingToProject(project.getId(), storeCasing));
		storeStatusService.updateStoreStatusesFromCasing(store, storeCasing);

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
		Store store = storeService.findOneUsingSpecs(storeId);
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
		Store domainModel = storeService.updateOneBanner(storeId, banner);
		return ResponseEntity.ok(new StoreView(domainModel));
	}

	@RequestMapping(value = "/{id}/store-statuses", method = RequestMethod.POST)
	public ResponseEntity addOneStoreStatusToStore(@PathVariable("id") Integer storeId, @RequestBody StoreStatusView request) {
		Store store = storeService.findOneUsingSpecs(storeId);
		storeStatusService.addOneToStore(request, store);
		return ResponseEntity.ok(new StoreView(store));
	}

	@PutMapping(value = "{storeId}", params = {"is-float"})
	public ResponseEntity updateIsDuplicate(@PathVariable("storeId") Integer storeId, @RequestParam("is-float") Boolean isFloat) {
		Store store = storeService.findOneUsingSpecs(storeId);
		store.setFloating(isFloat);
		return ResponseEntity.ok(new SimpleStoreView(storeService.updateOne(store.getId(), store)));
	}

	@RequestMapping(value = "/{id}/store-statuses/{statusId}", method = RequestMethod.DELETE)
	public ResponseEntity deleteStoreStatus(@PathVariable("id") Integer storeId, @PathVariable Integer statusId) {
		storeStatusService.deleteOne(statusId);
		Store domainModel = storeService.findOneUsingSpecs(storeId);
		return ResponseEntity.ok(new StoreView(domainModel));
	}

	@RequestMapping(value = "/{id}/store-volumes", method = RequestMethod.GET)
	public ResponseEntity findAllVolumesForStore(@PathVariable("id") Integer storeId) {
		List<StoreVolume> domainModels = volumeService.findAllByStoreIdUsingSpecs(storeId);
		return ResponseEntity.ok(domainModels.stream().map(StoreVolumeView::new).collect(Collectors.toList()));
	}

	@RequestMapping(value = "/{id}/store-volumes", method = RequestMethod.POST)
	public ResponseEntity addOneStoreVolumeToStore(@PathVariable("id") Integer storeId, @RequestBody StoreVolumeView request) {
		Store store = storeService.findOneUsingSpecs(storeId);
		volumeService.addOneToStore(request, store);
		return ResponseEntity.ok(new StoreView(store));
	}

	@RequestMapping(value = "/{id}/store-volumes/{volumeId}", method = RequestMethod.DELETE)
	public ResponseEntity deleteStoreVolume(@PathVariable("id") Integer storeId, @PathVariable Integer volumeId) {
		volumeService.deleteOne(volumeId);
		Store domainModel = storeService.findOneUsingSpecs(storeId);
		return ResponseEntity.ok(new StoreView(domainModel));
	}

	@Override
	public StoreService getEntityService() {
		return storeService;
	}

	@Override
	public Object getViewFromModel(Store model) {
		return new StoreView(model);
	}

	@Override
	public Object getSimpleViewFromModel(Store model) {
		return new SimpleStoreView(model);
	}
}
