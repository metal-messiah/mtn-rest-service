package com.mtn.controller;

import com.mtn.model.domain.*;
import com.mtn.model.simpleView.*;
import com.mtn.model.view.*;
import com.mtn.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 4/25/2017.
 */
@RestController
@RequestMapping("/api/store")
public class StoreController extends CrudControllerImpl<Store> {

	@Autowired
	private StoreService storeService;
	@Autowired
	private StoreSurveyService surveyService;
	@Autowired
	private StoreVolumeService volumeService;
	@Autowired
	private StoreCasingService casingService;
	@Autowired
	private StoreModelService modelService;
	@Autowired
	StoreStatusService storeStatusService;

	@RequestMapping(method = RequestMethod.GET, params = {"north", "south", "east", "west"})
	public ResponseEntity findAllInBounds(
			@RequestParam("north") Float north,
			@RequestParam("south") Float south,
			@RequestParam("east") Float east,
			@RequestParam("west") Float west,
			@RequestParam("store_type") String storeType,
			Pageable page) {
		Page<Store> domainModels = storeService.findAllOfTypeInBounds(north, south, east, west, storeType, page);
		return ResponseEntity.ok(domainModels.map(this::getSimpleViewFromModel));
	}

	@RequestMapping(value = "/{id}/store-casings", method = RequestMethod.POST)
	public ResponseEntity createOneStoreCasingForStore(
			@PathVariable("id") Integer storeId,
			@RequestBody StoreCasing request,
			@RequestParam(value = "store_remodeled", required = false) boolean storeRemodeled,
			@RequestParam(value = "shopping_center_redeveloped", required = false) boolean shoppingCenterRedeveloped) {
		StoreCasing domainModel = storeService.addOneCasingToStore(storeId, request, storeRemodeled, shoppingCenterRedeveloped);
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

	@RequestMapping(value = "/{id}/current-store-status/{statusId}", method = RequestMethod.PUT)
	public ResponseEntity setCurrentStoreStatus(@PathVariable("id") Integer storeId, @PathVariable("statusId") Integer statusId) {
		Store domainModel = storeService.setCurrentStoreStatus(storeId, statusId);
		return ResponseEntity.ok(new StoreView(domainModel));
	}

	@RequestMapping(value = "/{id}/store-statuses", method = RequestMethod.POST)
	public ResponseEntity addOneStoreStatusToStore(@PathVariable("id") Integer storeId, @RequestBody StoreStatus storeStatus) {
		StoreStatus createdStatus = storeService.addOneStatusToStore(storeId, storeStatus);
		Store domainModel = storeService.findOne(storeId);
		return ResponseEntity.ok(new StoreView(domainModel));
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
		return ResponseEntity.ok(domainModels.stream().map(SimpleStoreVolumeView::new).collect(Collectors.toList()));
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
