package com.mtn.controller;

import com.mtn.model.domain.ShoppingCenterAccess;
import com.mtn.model.domain.ShoppingCenterSurvey;
import com.mtn.model.domain.ShoppingCenterTenant;
import com.mtn.model.view.ShoppingCenterSurveyView;
import com.mtn.model.view.SimpleShoppingCenterAccessView;
import com.mtn.model.view.SimpleShoppingCenterTenantView;
import com.mtn.service.ShoppingCenterAccessService;
import com.mtn.service.ShoppingCenterSurveyService;
import com.mtn.service.ShoppingCenterTenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 5/4/2017.
 */
@RestController
@RequestMapping("/api/shopping-center-survey")
public class ShoppingCenterSurveyController {

    @Autowired
    private ShoppingCenterAccessService accessService;
    @Autowired
    private ShoppingCenterSurveyService surveyService;
    @Autowired
    private ShoppingCenterTenantService tenantService;

    @RequestMapping(path = "/{id}/shopping-center-access", method = RequestMethod.POST)
    public ResponseEntity addOneAccessToSurvey(@PathVariable("id") Integer surveyId, @RequestBody ShoppingCenterAccess request) {
        ShoppingCenterAccess domainModel = surveyService.addOneAccessToSurvey(surveyId, request);
        return ResponseEntity.ok(new SimpleShoppingCenterAccessView(domainModel));
    }

    @RequestMapping(path = "/{id}/shopping-center-tenant", method = RequestMethod.POST)
    public ResponseEntity addOneTenantToSurvey(@PathVariable("id") Integer surveyId, @RequestBody ShoppingCenterTenant request) {
        ShoppingCenterTenant domainModel = surveyService.addOneTenantToSurvey(surveyId, request);
        return ResponseEntity.ok(new SimpleShoppingCenterTenantView(domainModel));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOne(@PathVariable("id") Integer id) {
        surveyService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "/{id}/shopping-center-access", method = RequestMethod.GET)
    public ResponseEntity findAllAccessesForSurvey(@PathVariable("id") Integer surveyId) {
        List<ShoppingCenterAccess> domainModels = accessService.findAllBySurveyId(surveyId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleShoppingCenterAccessView::new).collect(Collectors.toList()));
    }

    @RequestMapping(path = "/{id}/shopping-center-tenant", method = RequestMethod.GET)
    public ResponseEntity findAllTenantsForSurvey(@PathVariable("id") Integer surveyId) {
        List<ShoppingCenterTenant> domainModels = tenantService.findAllBySurveyId(surveyId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleShoppingCenterTenantView::new).collect(Collectors.toList()));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        ShoppingCenterSurvey domainModel = surveyService.findOneUsingSpecs(id);
        if (domainModel != null) {
            return ResponseEntity.ok(new ShoppingCenterSurveyView(domainModel));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateOne(@PathVariable("id") Integer id, @RequestBody ShoppingCenterSurvey request) {
        ShoppingCenterSurvey domainModel = surveyService.updateOne(id, request);
        return ResponseEntity.ok(new ShoppingCenterSurveyView(domainModel));
    }
}