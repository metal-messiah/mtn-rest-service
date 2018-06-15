package com.mtn.controller;

import com.mtn.model.domain.Project;
import com.mtn.model.domain.ShoppingCenterAccess;
import com.mtn.model.domain.ShoppingCenterSurvey;
import com.mtn.model.domain.ShoppingCenterTenant;
import com.mtn.model.simpleView.SimpleShoppingCenterSurveyView;
import com.mtn.model.view.ShoppingCenterAccessView;
import com.mtn.model.view.ShoppingCenterSurveyView;
import com.mtn.model.simpleView.SimpleProjectView;
import com.mtn.model.simpleView.SimpleShoppingCenterAccessView;
import com.mtn.model.simpleView.SimpleShoppingCenterTenantView;
import com.mtn.model.view.ShoppingCenterTenantView;
import com.mtn.service.*;
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
public class ShoppingCenterSurveyController extends CrudControllerImpl<ShoppingCenterSurvey> {

    @Autowired
    private ShoppingCenterAccessService accessService;
    @Autowired
    private ShoppingCenterSurveyService shoppingCenterSurveyService;
    @Autowired
    private ShoppingCenterTenantService tenantService;

    @RequestMapping(path = "/{id}/accesses", method = RequestMethod.POST)
    public ResponseEntity addOneAccessToSurvey(@PathVariable("id") Integer surveyId, @RequestBody ShoppingCenterAccess request) {
        ShoppingCenterAccess domainModel = getEntityService().addOneAccessToSurvey(surveyId, request);
        return ResponseEntity.ok(new SimpleShoppingCenterAccessView(domainModel));
    }

    @RequestMapping(path = "/{id}/accesses", method = RequestMethod.GET)
    public ResponseEntity findAllAccessesForSurvey(@PathVariable("id") Integer surveyId) {
        List<ShoppingCenterAccess> domainModels = accessService.findAllBySurveyIdUsingSpecs(surveyId);
        return ResponseEntity.ok(domainModels.stream().map(ShoppingCenterAccessView::new).collect(Collectors.toList()));
    }

    @RequestMapping(path = "/{id}/tenants", method = RequestMethod.POST)
    public ResponseEntity createNewTenantsForSurvey(@PathVariable("id") Integer surveyId, @RequestBody List<ShoppingCenterTenant> request) {
        List<ShoppingCenterTenant> domainModels = getEntityService().createNewTenantsForSurvey(surveyId, request);
        return ResponseEntity.ok(domainModels.stream()
                .map(ShoppingCenterTenantView::new)
                .collect(Collectors.toList()));
    }

    @RequestMapping(path = "/{id}/tenants", method = RequestMethod.GET)
    public ResponseEntity findAllTenantsForSurvey(@PathVariable("id") Integer surveyId) {
        List<ShoppingCenterTenant> domainModels = tenantService.findAllBySurveyIdUsingSpecs(surveyId);
        return ResponseEntity.ok(domainModels.stream().map(ShoppingCenterTenantView::new).collect(Collectors.toList()));
    }

    @Override
    public ShoppingCenterSurveyService getEntityService() {
        return shoppingCenterSurveyService;
    }

    @Override
    public Object getViewFromModel(ShoppingCenterSurvey model) {
        return new ShoppingCenterSurveyView(model);
    }

    @Override
    public Object getSimpleViewFromModel(ShoppingCenterSurvey model) {
        return new SimpleShoppingCenterSurveyView(model);
    }
}
