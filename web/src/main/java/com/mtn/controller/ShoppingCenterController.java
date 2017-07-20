package com.mtn.controller;

import com.mtn.model.converter.ShoppingCenterToSimpleShoppingCenterViewConverter;
import com.mtn.model.domain.ShoppingCenter;
import com.mtn.model.domain.ShoppingCenterCasing;
import com.mtn.model.domain.ShoppingCenterSurvey;
import com.mtn.model.domain.Site;
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
public class ShoppingCenterController {

    @Autowired
    private ShoppingCenterService shoppingCenterService;
    @Autowired
    private SiteService siteService;
    @Autowired
    private ShoppingCenterSurveyService surveyService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private ShoppingCenterCasingService casingService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addOne(@RequestBody ShoppingCenter request) {
        securityService.checkPermission("SHOPPING_CENTERS_CREATE");

        ShoppingCenter domainModel = shoppingCenterService.addOne(request);
        return ResponseEntity.ok(new ShoppingCenterView(domainModel));
    }

    @RequestMapping(value = "/{id}/shopping-center-casing", method = RequestMethod.POST)
    public ResponseEntity addOneShoppingCenterCasingToShoppingCenter(@PathVariable("id") Integer shoppingCenterId, @RequestBody ShoppingCenterCasing request) {
        securityService.checkPermission("SHOPPING_CENTER_CASINGS_CREATE");

        ShoppingCenterCasing domainModel = shoppingCenterService.addOneCasingToShoppingCenter(shoppingCenterId, request);
        return ResponseEntity.ok(new SimpleShoppingCenterCasingView(domainModel));
    }

    @RequestMapping(value = "/{id}/shopping-center-survey", method = RequestMethod.POST)
    public ResponseEntity addOneShoppingCenterSurveyToShoppingCenter(@PathVariable("id") Integer shoppingCenterId, @RequestBody ShoppingCenterSurvey request) {
        securityService.checkPermission("SHOPPING_CENTER_SURVEYS_CREATE");

        ShoppingCenterSurvey domainModel = shoppingCenterService.addOneSurveyToShoppingCenter(shoppingCenterId, request);
        return ResponseEntity.ok(new ShoppingCenterSurveyView(domainModel));
    }

    @RequestMapping(value = "/{id}/site", method = RequestMethod.POST)
    public ResponseEntity addOneSiteToShoppingCenter(@PathVariable("id") Integer shoppingCenterId, @RequestBody SimpleSiteView request) {
        securityService.checkPermission("SITES_CREATE");

        Site transformedRequest = new Site(request);
        Site domainModel = shoppingCenterService.addOneSiteToShoppingCenter(shoppingCenterId, transformedRequest);
        return ResponseEntity.ok(new SimpleSiteView(domainModel));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOne(@PathVariable("id") Integer id) {
        securityService.checkPermission("SHOPPING_CENTERS_DELETE");

        shoppingCenterService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAll(
            @RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "owner", required = false) String owner,
            Pageable page
    ) {
        securityService.checkPermission("SHOPPING_CENTERS_READ");

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

        return ResponseEntity.ok(domainModels.map(new ShoppingCenterToSimpleShoppingCenterViewConverter()));
    }

    @RequestMapping(value = "/{id}/shopping-center-casing", method = RequestMethod.GET)
    public ResponseEntity findAllShoppingCenterCasingsForShoppingCenter(@PathVariable("id") Integer shoppingCenterId) {
        securityService.checkPermission("SHOPPING_CENTER_CASINGS_READ");

        List<ShoppingCenterCasing> domainModels = casingService.findAllByShoppingCenterId(shoppingCenterId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleShoppingCenterCasingView::new).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/{id}/shopping-center-survey", method = RequestMethod.GET)
    public ResponseEntity findAllShoppingCenterSurveysForShoppingCenter(@PathVariable("id") Integer shoppingCenterId) {
        securityService.checkPermission("SHOPPING_CENTER_SURVEYS_READ");

        List<ShoppingCenterSurvey> domainModels = surveyService.findAllByShoppingCenterId(shoppingCenterId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleShoppingCenterSurveyView::new).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/{id}/site", method = RequestMethod.GET)
    public ResponseEntity findAllSitesForShoppingCenter(@PathVariable("id") Integer shoppingCenterId) {
        securityService.checkPermission("SITES_READ");

        List<Site> domainModels = siteService.findAllByShoppingCenterIdUsingSpecs(shoppingCenterId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleSiteView::new).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        securityService.checkPermission("SHOPPING_CENTERS_READ");

        ShoppingCenter domainModel = shoppingCenterService.findOneUsingSpecs(id);
        if (domainModel != null) {
            return ResponseEntity.ok(new ShoppingCenterView(domainModel));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateOne(@PathVariable("id") Integer id, @RequestBody ShoppingCenter request) {
        securityService.checkPermission("SHOPPING_CENTERS_UPDATE");

        ShoppingCenter domainModel = shoppingCenterService.updateOne(id, request);
        return ResponseEntity.ok(new ShoppingCenterView(domainModel));
    }
}
