package com.mtn.controller;

import com.mtn.model.domain.*;
import com.mtn.model.simpleView.*;
import com.mtn.model.view.*;
import com.mtn.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private ProjectService projectService;

    @RequestMapping(value = "/{id}/store-casing", method = RequestMethod.POST)
    public ResponseEntity addOneStoreCasingToStore(@PathVariable("id") Integer storeId, @RequestBody StoreCasing request) {
        StoreCasing domainModel = storeService.addOneCasingToStore(storeId, request);
        return ResponseEntity.ok(new StoreCasingView(domainModel));
    }

    @RequestMapping(value = "/{id}/store-survey", method = RequestMethod.POST)
    public ResponseEntity addOneStoreSurveyToStore(@PathVariable("id") Integer storeId, @RequestBody StoreSurvey request) {
        StoreSurvey domainModel = storeService.addOneSurveyToStore(storeId, request);
        return ResponseEntity.ok(new StoreSurveyView(domainModel));
    }

    @RequestMapping(value = "/{id}/store-volume", method = RequestMethod.POST)
    public ResponseEntity addOneStoreVolumeToStore(@PathVariable("id") Integer storeId, @RequestBody StoreVolume request) {
        StoreVolume domainModel = storeService.addOneVolumeToStore(storeId, request);
        return ResponseEntity.ok(new SimpleStoreVolumeView(domainModel));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOne(@PathVariable("id") Integer id) {
        storeService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}/store-casing", method = RequestMethod.GET)
    public ResponseEntity findAllCasingsForStore(@PathVariable("id") Integer storeId) {
        List<StoreCasing> domainModels = casingService.findAllByStoreIdUsingSpecs(storeId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleStoreCasingView::new).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/{id}/store-model", method = RequestMethod.GET)
    public ResponseEntity findAllModelsForStore(@PathVariable("id") Integer storeId) {
        List<StoreModel> domainModels = modelService.findAllByStoreIdUsingSpecs(storeId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleStoreModelView::new).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/{id}/project", method = RequestMethod.GET)
    public ResponseEntity findAllProjectsForStore(@PathVariable("id") Integer storeId) {
        List<Project> domainModels = projectService.findAllByStoreId(storeId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleProjectView::new).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/{id}/store-survey", method = RequestMethod.GET)
    public ResponseEntity findAllSurveysForStore(@PathVariable("id") Integer storeId) {
        List<StoreSurvey> domainModels = surveyService.findAllByStoreIdUsingSpecs(storeId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleStoreSurveyView::new).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/{id}/store-volume", method = RequestMethod.GET)
    public ResponseEntity findAllVolumesForStore(@PathVariable("id") Integer storeId) {
        List<StoreVolume> domainModels = volumeService.findAllByStoreIdUsingSpecs(storeId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleStoreVolumeView::new).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        Store domainModel = storeService.findOneUsingSpecs(id);
        if (domainModel == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(new StoreView(domainModel));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateOne(
            @PathVariable("id") Integer id,
            @RequestParam(value = "overrideActiveStore", defaultValue = "false") Boolean overrideActiveStore,
            @RequestBody StoreView request) {
        Store domainModel = storeService.updateOne(id, new Store((StoreView) request), overrideActiveStore); //Cast ensures correct constructor is called
        return ResponseEntity.ok(new StoreView(domainModel));
    }

    @RequestMapping(value = "/{storeId}/company/{companyId}", method = RequestMethod.PUT)
    public ResponseEntity updateOneParentCompany(@PathVariable("storeId") Integer storeId, @PathVariable("companyId") Integer companyId) {
        Store domainModel = storeService.updateOneParentCompany(storeId, companyId);
        return ResponseEntity.ok(new StoreView(domainModel));
    }
}
