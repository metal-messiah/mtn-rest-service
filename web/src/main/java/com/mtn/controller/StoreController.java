package com.mtn.controller;

import com.mtn.model.domain.Store;
import com.mtn.model.domain.StoreSurvey;
import com.mtn.model.view.SimpleStoreSurveyView;
import com.mtn.model.view.StoreView;
import com.mtn.service.SecurityService;
import com.mtn.service.StoreService;
import com.mtn.service.StoreSurveyService;
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
    private SecurityService securityService;

    @RequestMapping(value = "/{id}/store-survey", method = RequestMethod.POST)
    public ResponseEntity addOneStoreSurveyToStore(@PathVariable("id") Integer storeId, @RequestBody StoreSurvey request) {
        securityService.checkPermission("STORE_SURVEYS_CREATE");

        StoreSurvey domainModel = storeService.addOneSurveyToStore(storeId, request);
        return ResponseEntity.ok(new SimpleStoreSurveyView(domainModel));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOne(@PathVariable("id") Integer id) {
        securityService.checkPermission("STORES_DELETE");

        storeService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}/store-survey", method = RequestMethod.GET)
    public ResponseEntity findAllSurveysForStore(@PathVariable("id") Integer storeId) {
        securityService.checkPermission("STORE_SURVEYS_READ");

        List<StoreSurvey> domainModels = surveyService.findAllByStoreId(storeId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleStoreSurveyView::new).collect(Collectors.toList()));
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
