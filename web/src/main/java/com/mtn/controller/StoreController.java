package com.mtn.controller;

import com.mtn.model.domain.*;
import com.mtn.model.view.*;
import com.mtn.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 4/25/2017.
 */
@RestController
@RequestMapping("/api/store")
public class StoreController {

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
    private SecurityService securityService;

    @RequestMapping(value = "/{id}/store-casing", method = RequestMethod.POST)
    public ResponseEntity addOneStoreCasingToStore(@PathVariable("id") Integer storeId, @RequestBody StoreCasing request) {
        securityService.checkPermission("STORE_CASINGS_CREATE");

        StoreCasing domainModel = storeService.addOneCasingToStore(storeId, request);
        return ResponseEntity.ok(new StoreCasingView(domainModel));
    }

    @RequestMapping(value = "/{id}/store-survey", method = RequestMethod.POST)
    public ResponseEntity addOneStoreSurveyToStore(@PathVariable("id") Integer storeId, @RequestBody StoreSurvey request) {
        securityService.checkPermission("STORE_SURVEYS_CREATE");

        StoreSurvey domainModel = storeService.addOneSurveyToStore(storeId, request);
        return ResponseEntity.ok(new StoreSurveyView(domainModel));
    }

    @RequestMapping(value = "/{id}/store-volume", method = RequestMethod.POST)
    public ResponseEntity addOneStoreVolumeToStore(@PathVariable("id") Integer storeId, @RequestBody StoreVolume request) {
        securityService.checkPermission("STORE_VOLUMES_CREATE");

        StoreVolume domainModel = storeService.addOneVolumeToStore(storeId, request);
        return ResponseEntity.ok(new SimpleStoreVolumeView(domainModel));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOne(@PathVariable("id") Integer id) {
        securityService.checkPermission("STORES_DELETE");

        storeService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}/store-casing", method = RequestMethod.GET)
    public ResponseEntity findAllCasingsForStore(@PathVariable("id") Integer storeId) {
        securityService.checkPermission("STORE_CASINGS_READ");

        List<StoreCasing> domainModels = casingService.findAllByStoreIdUsingSpecs(storeId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleStoreCasingView::new).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/{id}/store-model", method = RequestMethod.GET)
    public ResponseEntity findAllModelsForStore(@PathVariable("id") Integer storeId) {
        securityService.checkPermission("STORE_MODELS_READ");

        List<StoreModel> domainModels = modelService.findAllByStoreIdUsingSpecs(storeId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleStoreModelView::new).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/{id}/store-survey", method = RequestMethod.GET)
    public ResponseEntity findAllSurveysForStore(@PathVariable("id") Integer storeId) {
        securityService.checkPermission("STORE_SURVEYS_READ");

        List<StoreSurvey> domainModels = surveyService.findAllByStoreIdUsingSpecs(storeId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleStoreSurveyView::new).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/{id}/store-volume", method = RequestMethod.GET)
    public ResponseEntity findAllVolumesForStore(@PathVariable("id") Integer storeId) {
        securityService.checkPermission("STORE_VOLUMES_READ");

        List<StoreVolume> domainModels = volumeService.findAllByStoreIdUsingSpecs(storeId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleStoreVolumeView::new).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        securityService.checkPermission("STORES_READ");

        Store domainModel = storeService.findOneUsingSpecs(id);
        if (domainModel != null) {
            return ResponseEntity.ok(new StoreView(domainModel));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateOne(
            @PathVariable("id") Integer id,
            @RequestParam(value = "overrideActiveStore", defaultValue = "false") Boolean overrideActiveStore,
            @RequestBody StoreView request) {
        securityService.checkPermission("STORES_UPDATE");

        Store domainModel = storeService.updateOne(id, new Store((StoreView) request), overrideActiveStore); //Cast ensures correct constructor is called
        return ResponseEntity.ok(new StoreView(domainModel));
    }

    @RequestMapping(value = "/{storeId}/company/{companyId}", method = RequestMethod.PUT)
    public ResponseEntity updateOneParentCompany(@PathVariable("storeId") Integer storeId, @PathVariable("companyId") Integer companyId) {
        securityService.checkPermission("STORES_UPDATE");
        securityService.checkPermission("COMPANIES_UPDATE");

        Store domainModel = storeService.updateOneParentCompany(storeId, companyId);
        return ResponseEntity.ok(new StoreView(domainModel));
    }
}
