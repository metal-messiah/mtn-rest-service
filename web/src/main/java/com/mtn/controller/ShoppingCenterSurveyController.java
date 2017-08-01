package com.mtn.controller;

import com.mtn.model.domain.Project;
import com.mtn.model.domain.ShoppingCenterAccess;
import com.mtn.model.domain.ShoppingCenterSurvey;
import com.mtn.model.domain.ShoppingCenterTenant;
import com.mtn.model.view.ShoppingCenterSurveyView;
import com.mtn.model.view.SimpleProjectView;
import com.mtn.model.view.SimpleShoppingCenterAccessView;
import com.mtn.model.view.SimpleShoppingCenterTenantView;
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
public class ShoppingCenterSurveyController {

    @Autowired
    private ShoppingCenterAccessService accessService;
    @Autowired
    private ShoppingCenterSurveyService surveyService;
    @Autowired
    private ShoppingCenterTenantService tenantService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private ProjectService projectService;

    @RequestMapping(path = "/{id}/shopping-center-access", method = RequestMethod.POST)
    public ResponseEntity addOneAccessToSurvey(@PathVariable("id") Integer surveyId, @RequestBody ShoppingCenterAccess request) {
        securityService.checkPermission("SHOPPING_CENTER_SURVEYS_UPDATE");

        ShoppingCenterAccess domainModel = surveyService.addOneAccessToSurvey(surveyId, request);
        return ResponseEntity.ok(new SimpleShoppingCenterAccessView(domainModel));
    }

    @RequestMapping(path = "/{id}/shopping-center-tenant", method = RequestMethod.POST)
    public ResponseEntity addOneTenantToSurvey(@PathVariable("id") Integer surveyId, @RequestBody ShoppingCenterTenant request) {
        securityService.checkPermission("SHOPPING_CENTER_SURVEYS_UPDATE");

        ShoppingCenterTenant domainModel = surveyService.addOneTenantToSurvey(surveyId, request);
        return ResponseEntity.ok(new SimpleShoppingCenterTenantView(domainModel));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOne(@PathVariable("id") Integer id) {
        securityService.checkPermission("SHOPPING_CENTER_SURVEYS_DELETE");

        surveyService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "/{id}/shopping-center-access", method = RequestMethod.GET)
    public ResponseEntity findAllAccessesForSurvey(@PathVariable("id") Integer surveyId) {
        securityService.checkPermission("SHOPPING_CENTER_SURVEYS_READ");

        List<ShoppingCenterAccess> domainModels = accessService.findAllBySurveyIdUsingSpecs(surveyId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleShoppingCenterAccessView::new).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/{id}/project", method = RequestMethod.GET)
    public ResponseEntity findAllProjectsForShoppingCenterSurvey(@PathVariable("id") Integer shoppingCenterSurveyId) {
        securityService.checkPermission("PROJECTS_READ");

        List<Project> domainModels = projectService.findAllByShoppingCenterSurveyId(shoppingCenterSurveyId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleProjectView::new).collect(Collectors.toList()));
    }

    @RequestMapping(path = "/{id}/shopping-center-tenant", method = RequestMethod.GET)
    public ResponseEntity findAllTenantsForSurvey(@PathVariable("id") Integer surveyId) {
        securityService.checkPermission("SHOPPING_CENTER_SURVEYS_READ");

        List<ShoppingCenterTenant> domainModels = tenantService.findAllBySurveyIdUsingSpecs(surveyId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleShoppingCenterTenantView::new).collect(Collectors.toList()));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        securityService.checkPermission("SHOPPING_CENTER_SURVEYS_READ");

        ShoppingCenterSurvey domainModel = surveyService.findOneUsingSpecs(id);
        if (domainModel != null) {
            return ResponseEntity.ok(new ShoppingCenterSurveyView(domainModel));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateOne(@PathVariable("id") Integer id, @RequestBody ShoppingCenterSurvey request) {
        securityService.checkPermission("SHOPPING_CENTER_SURVEYS_UPDATE");

        ShoppingCenterSurvey domainModel = surveyService.updateOne(id, request);
        return ResponseEntity.ok(new ShoppingCenterSurveyView(domainModel));
    }
}
