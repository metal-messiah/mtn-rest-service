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
	private final StoreSurveyService surveyService;
	private final StoreVolumeService volumeService;
	private final StoreCasingService casingService;
	private final StoreModelService modelService;

	@Autowired
	public StoreController(StoreService storeService, StoreSurveyService surveyService, StoreVolumeService volumeService, StoreCasingService casingService, StoreModelService modelService) {
		this.storeService = storeService;
		this.surveyService = surveyService;
		this.volumeService = volumeService;
		this.casingService = casingService;
		this.modelService = modelService;
	}

	@RequestMapping(method = RequestMethod.GET, params = {"north", "south", "east", "west"})
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

	@RequestMapping(value = "/{id}/store-casings", method = RequestMethod.POST)
	public ResponseEntity createOneStoreCasingForStore(
			@PathVariable("id") Integer storeId,
			@RequestBody StoreCasing request) {
		StoreCasing domainModel = storeService.addOneCasingToStore(storeId, request);
		return ResponseEntity.ok(new StoreCasingView(domainModel));
	}

	@RequestMapping(value = "/{id}/store-casings", method = RequestMethod.GET)
	public ResponseEntity findAllCasingsForStore(@PathVariable("id") Integer storeId) {
		List<StoreCasing> domainModels = casingService.findAllByStoreIdUsingSpecs(storeId);
		return ResponseEntity.ok(domainModels.stream().map(SimpleStoreCasingView::new).collect(Collectors.toList()));
	}

	@RequestMapping(value = "/{id}/store-model", method = RequestMethod.GET)
	public ResponseEntity findAllModelsForStore(@PathVariable("id") Integer storeId) {
		List<StoreModel> domainModels = modelService.findAllByStoreIdUsingSpecs(storeId);
		return ResponseEntity.ok(domainModels.stream().map(SimpleStoreModelView::new).collect(Collectors.toList()));
	}

	@RequestMapping(value = "/{id}/store-surveys", method = RequestMethod.POST)
	public ResponseEntity addOneStoreSurveyToStore(@PathVariable("id") Integer storeId, @RequestBody StoreSurvey request) {
		StoreSurvey domainModel = storeService.addOneSurveyToStore(storeId, request);
		return ResponseEntity.ok(new StoreSurveyView(domainModel));
	}

	@RequestMapping(value = "/{id}/store-surveys", method = RequestMethod.GET)
	public ResponseEntity findAllSurveysForStore(@PathVariable("id") Integer storeId) {
		List<StoreSurvey> domainModels = surveyService.findAllByStoreIdUsingSpecs(storeId);
		return ResponseEntity.ok(domainModels.stream().map(SimpleStoreSurveyView::new).collect(Collectors.toList()));
	}

	@RequestMapping(value = "/{storeId}/banner/{bannerId}", method = RequestMethod.PUT)
	public ResponseEntity updateOneBanner(@PathVariable("storeId") Integer storeId, @PathVariable("bannerId") Integer bannerId) {
		Store domainModel = storeService.updateOneBanner(storeId, bannerId);
		return ResponseEntity.ok(new StoreView(domainModel));
	}

	@RequestMapping(value = "/{id}/store-statuses", method = RequestMethod.POST)
	public ResponseEntity addOneStoreStatusToStore(@PathVariable("id") Integer storeId, @RequestBody StoreStatus storeStatus) {
		StoreStatus createdStatus = storeService.addOneStatusToStore(storeId, storeStatus);
		Store domainModel = storeService.findOne(storeId);
		return ResponseEntity.ok(new StoreView(domainModel));
	}

	@PutMapping(value = "{storeId}", params = {"is-float"})
	public ResponseEntity updateIsDuplicate(@PathVariable("storeId") Integer storeId, @RequestParam("is-float") Boolean isFloat) {
		Store store = storeService.findOne(storeId);
		store.setFloating(isFloat);
		return ResponseEntity.ok(new SimpleStoreView(storeService.updateOne(store.getId(), store)));
	}

	@RequestMapping(value = "/{id}/store-statuses/{statusId}", method = RequestMethod.DELETE)
	public ResponseEntity deleteStoreStatus(@PathVariable("id") Integer storeId, @PathVariable Integer statusId) {
		storeService.deleteStoreStatus(storeId, statusId);
		Store domainModel = storeService.findOne(storeId);
		return ResponseEntity.ok(new StoreView(domainModel));
	}

	@RequestMapping(value = "/{id}/store-volumes", method = RequestMethod.GET)
	public ResponseEntity findAllVolumesForStore(@PathVariable("id") Integer storeId) {
		List<StoreVolume> domainModels = volumeService.findAllByStoreIdUsingSpecs(storeId);
		return ResponseEntity.ok(domainModels.stream().map(StoreVolumeView::new).collect(Collectors.toList()));
	}

	@RequestMapping(value = "/{id}/store-volumes", method = RequestMethod.POST)
	public ResponseEntity addOneStoreVolumeToStore(@PathVariable("id") Integer storeId, @RequestBody StoreVolume request) {
		StoreVolume createdVolume = storeService.addOneVolumeToStore(storeId, request);
		Store domainModel = storeService.findOne(storeId);
		return ResponseEntity.ok(new StoreView(domainModel));
	}

	@RequestMapping(value = "/{id}/store-volumes/{volumeId}", method = RequestMethod.DELETE)
	public ResponseEntity deleteStoreVolume(@PathVariable("id") Integer storeId, @PathVariable Integer volumeId) {
		storeService.deleteStoreVolume(storeId, volumeId);
		Store domainModel = storeService.findOne(storeId);
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
