package com.mtn.controller;

import com.mtn.model.domain.ShoppingCenterAccess;
import com.mtn.model.domain.ShoppingCenterSurvey;
import com.mtn.model.domain.ShoppingCenterTenant;
import com.mtn.model.view.ShoppingCenterAccessView;
import com.mtn.model.view.ShoppingCenterSurveyView;
import com.mtn.model.view.ShoppingCenterTenantView;
import com.mtn.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/shopping-center-survey")
public class ShoppingCenterSurveyController extends CrudController<ShoppingCenterSurvey, ShoppingCenterSurveyView> {

    private final ShoppingCenterAccessService accessService;
    private final ShoppingCenterTenantService tenantService;

    @Autowired
    public ShoppingCenterSurveyController(ShoppingCenterAccessService accessService, ShoppingCenterSurveyService shoppingCenterSurveyService, ShoppingCenterTenantService tenantService) {
        super(shoppingCenterSurveyService, ShoppingCenterSurveyView::new);
        this.accessService = accessService;
        this.tenantService = tenantService;
    }

    @PostMapping(path = "/{id}/accesses")
    public ResponseEntity createAccessForSurvey(@PathVariable("id") Integer surveyId, @RequestBody ShoppingCenterAccessView request) {
        ShoppingCenterSurvey survey = this.entityService.findOneUsingSpecs(surveyId);
        ShoppingCenterAccess domainModel = accessService.addOne(request, survey);
        return ResponseEntity.ok(new ShoppingCenterAccessView(domainModel));
    }

    @GetMapping(path = "/{id}/accesses")
    public ResponseEntity findAllAccessesForSurvey(@PathVariable("id") Integer surveyId) {
        List<ShoppingCenterAccess> domainModels = accessService.findAllBySurveyIdUsingSpecs(surveyId);
        return ResponseEntity.ok(domainModels.stream().map(ShoppingCenterAccessView::new).collect(Collectors.toList()));
    }

    @PostMapping(path = "/{id}/tenants")
    public ResponseEntity createTenantsForSurvey(@PathVariable("id") Integer surveyId, @RequestBody List<ShoppingCenterTenantView> requestTenants) {
        ShoppingCenterSurvey survey = this.entityService.findOneUsingSpecs(surveyId);
        List<ShoppingCenterTenant> domainModels = tenantService.addMultiple(requestTenants, survey);
        return ResponseEntity.ok(domainModels.stream()
                .map(ShoppingCenterTenantView::new)
                .collect(Collectors.toList()));
    }

    @GetMapping(path = "/{id}/tenants")
    public ResponseEntity findAllTenantsForSurvey(@PathVariable("id") Integer surveyId) {
        List<ShoppingCenterTenant> domainModels = tenantService.findAllBySurveyIdUsingSpecs(surveyId);
        return ResponseEntity.ok(domainModels.stream().map(ShoppingCenterTenantView::new).collect(Collectors.toList()));
    }

}
