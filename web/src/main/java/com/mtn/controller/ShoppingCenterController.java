package com.mtn.controller;

import com.mtn.model.converter.ShoppingCenterToShoppingCenterViewConverter;
import com.mtn.model.converter.ShoppingCenterToSimpleShoppingCenterViewConverter;
import com.mtn.model.converter.SimpleSiteViewToSiteConverter;
import com.mtn.model.converter.SiteToSimpleSiteViewConverter;
import com.mtn.model.domain.ShoppingCenter;
import com.mtn.model.domain.Site;
import com.mtn.model.view.SimpleSiteView;
import com.mtn.service.ShoppingCenterService;
import com.mtn.service.SiteService;
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

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addOne(@RequestBody ShoppingCenter request) {
        ShoppingCenter domainModel = shoppingCenterService.addOne(request);
        return ResponseEntity.ok(ShoppingCenterToShoppingCenterViewConverter.build(domainModel));
    }

    @RequestMapping(value = "/{id}/site", method = RequestMethod.POST)
    public ResponseEntity addOneSiteToShoppingCenter(@PathVariable("id") Integer shoppingCenterId, @RequestBody SimpleSiteView request) {
        Site transformedRequest = SimpleSiteViewToSiteConverter.build(request);
        Site domainModel = shoppingCenterService.addOneSiteToShoppingCenter(shoppingCenterId, transformedRequest);
        return ResponseEntity.ok(SiteToSimpleSiteViewConverter.build(domainModel));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOne(@PathVariable("id") Integer id) {
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

    @RequestMapping(value = "/{id}/site", method = RequestMethod.GET)
    public ResponseEntity findAllSitesForShoppingCenter(@PathVariable("id") Integer shoppingCenterId) {
        List<Site> domainModels = siteService.findAllByShoppingCenterIdUsingSpecs(shoppingCenterId);
        return ResponseEntity.ok(domainModels.stream().map(SiteToSimpleSiteViewConverter::build).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        ShoppingCenter domainModel = shoppingCenterService.findOneUsingSpecs(id);
        if (domainModel != null) {
            return ResponseEntity.ok(ShoppingCenterToShoppingCenterViewConverter.build(domainModel));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateOne(@PathVariable("id") Integer id, @RequestBody ShoppingCenter request) {
        ShoppingCenter domainModel = shoppingCenterService.updateOne(id, request);
        return ResponseEntity.ok(ShoppingCenterToShoppingCenterViewConverter.build(domainModel));
    }
}
