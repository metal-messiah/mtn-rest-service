package com.mtn.controller;

import com.mtn.model.domain.Project;
import com.mtn.model.domain.ShoppingCenterAccess;
import com.mtn.model.domain.ShoppingCenterSurvey;
import com.mtn.model.domain.ShoppingCenterTenant;
import com.mtn.model.simpleView.SimpleShoppingCenterSurveyView;
import com.mtn.model.view.ShoppingCenterSurveyView;
import com.mtn.model.simpleView.SimpleProjectView;
import com.mtn.model.simpleView.SimpleShoppingCenterAccessView;
import com.mtn.model.simpleView.SimpleShoppingCenterTenantView;
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
//    @Autowired
//    private ProjectService projectService;

    @RequestMapping(path = "/{id}/shopping-center-access", method = RequestMethod.POST)
    public ResponseEntity addOneAccessToSurvey(@PathVariable("id") Integer surveyId, @RequestBody ShoppingCenterAccess request) {
        ShoppingCenterAccess domainModel = getEntityService().addOneAccessToSurvey(surveyId, request);
        return ResponseEntity.ok(new SimpleShoppingCenterAccessView(domainModel));
    }

    @RequestMapping(path = "/{id}/shopping-center-tenant", method = RequestMethod.POST)
    public ResponseEntity addOneTenantToSurvey(@PathVariable("id") Integer surveyId, @RequestBody ShoppingCenterTenant request) {
        ShoppingCenterTenant domainModel = getEntityService().addOneTenantToSurvey(surveyId, request);
        return ResponseEntity.ok(new SimpleShoppingCenterTenantView(domainModel));
    }

    @RequestMapping(path = "/{id}/shopping-center-access", method = RequestMethod.GET)
    public ResponseEntity findAllAccessesForSurvey(@PathVariable("id") Integer surveyId) {
        List<ShoppingCenterAccess> domainModels = accessService.findAllBySurveyIdUsingSpecs(surveyId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleShoppingCenterAccessView::new).collect(Collectors.toList()));
    }

//    @RequestMapping(value = "/{id}/project", method = RequestMethod.GET)
//    public ResponseEntity findAllProjectsForShoppingCenterSurvey(@PathVariable("id") Integer shoppingCenterSurveyId) {
//        List<Project> domainModels = projectService.findAllByShoppingCenterSurveyId(shoppingCenterSurveyId);
//        return ResponseEntity.ok(domainModels.stream().map(SimpleProjectView::new).collect(Collectors.toList()));
//    }

    @RequestMapping(path = "/{id}/shopping-center-tenant", method = RequestMethod.GET)
    public ResponseEntity findAllTenantsForSurvey(@PathVariable("id") Integer surveyId) {
        List<ShoppingCenterTenant> domainModels = tenantService.findAllBySurveyIdUsingSpecs(surveyId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleShoppingCenterTenantView::new).collect(Collectors.toList()));
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
