package com.mtn.controller;

import com.mtn.model.domain.*;
import com.mtn.model.simpleView.SimpleShoppingCenterCasingView;
import com.mtn.model.simpleView.SimpleShoppingCenterSurveyView;
import com.mtn.model.simpleView.SimpleShoppingCenterView;
import com.mtn.model.simpleView.SimpleSiteView;
import com.mtn.model.view.*;
import com.mtn.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 4/23/2017.
 */
@RestController
@RequestMapping("/api/shopping-center")
public class ShoppingCenterController extends CrudControllerImpl<ShoppingCenter> {

    @Autowired
    private ShoppingCenterService shoppingCenterService;
    @Autowired
    private SiteService siteService;
    @Autowired
    private ShoppingCenterSurveyService surveyService;
    @Autowired
    private ShoppingCenterCasingService casingService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAll(
            @RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "owner", required = false) String owner,
            Pageable page
    ) {
        Page<ShoppingCenter> domainModels;
        if (StringUtils.isNotBlank(q)) {
            domainModels = shoppingCenterService.findAllByNameOrOwnerUsingSpecs(q, page);
        } else if (StringUtils.isNotBlank(name)) {
            domainModels = shoppingCenterService.findAllByNameUsingSpecs(name, page);
        } else if (StringUtils.isNotBlank(owner)) {
            domainModels = shoppingCenterService.findAllByOwnerUsingSpecs(owner, page);
        } else {
            domainModels = shoppingCenterService.findAllUsingSpecs(page);
        }

        return ResponseEntity.ok(domainModels.map(this::getSimpleViewFromModel));
    }

    @RequestMapping(value = "/{id}/shopping-center-casing", method = RequestMethod.GET)
    public ResponseEntity findAllShoppingCenterCasingsForShoppingCenter(@PathVariable("id") Integer shoppingCenterId) {
        List<ShoppingCenterCasing> domainModels = casingService.findAllByShoppingCenterIdUsingSpecs(shoppingCenterId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleShoppingCenterCasingView::new).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/{id}/shopping-center-survey", method = RequestMethod.GET)
    public ResponseEntity findAllShoppingCenterSurveysForShoppingCenter(@PathVariable("id") Integer shoppingCenterId) {
        List<ShoppingCenterSurvey> domainModels = surveyService.findAllByShoppingCenterIdUsingSpecs(shoppingCenterId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleShoppingCenterSurveyView::new).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/{id}/site", method = RequestMethod.GET)
    public ResponseEntity findAllSitesForShoppingCenter(@PathVariable("id") Integer shoppingCenterId) {
        List<Site> domainModels = siteService.findAllByShoppingCenterIdUsingSpecs(shoppingCenterId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleSiteView::new).collect(Collectors.toList()));
    }

    @Override
    public ShoppingCenterService getEntityService() {
        return shoppingCenterService;
    }

    @Override
    public Object getViewFromModel(ShoppingCenter model) {
        return new ShoppingCenterView(model);
    }

    @Override
    public Object getSimpleViewFromModel(ShoppingCenter model) {
        return new SimpleShoppingCenterView(model);
    }
}
