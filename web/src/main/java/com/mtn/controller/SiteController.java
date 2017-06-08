package com.mtn.controller;

import com.mtn.model.domain.Site;
import com.mtn.model.domain.Store;
import com.mtn.model.view.SimpleStoreView;
import com.mtn.model.view.SiteView;
import com.mtn.model.view.StoreView;
import com.mtn.service.SecurityService;
import com.mtn.service.SiteService;
import com.mtn.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 4/25/2017.
 */
@RestController
@RequestMapping("/api/site")
public class SiteController {

    @Autowired
    private SiteService siteService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = "/{id}/store", method = RequestMethod.POST)
    public ResponseEntity addOneStoreToSite(
            @PathVariable("id") Integer siteId,
            @RequestParam(value = "overrideActiveStore", defaultValue = "false") Boolean overrideActiveStore,
            @RequestBody Store request) {
        securityService.checkPermission("STORES_CREATE");

        Store domainModel = siteService.addOneStoreToSite(siteId, request, overrideActiveStore);
        return ResponseEntity.ok(new StoreView(domainModel));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOne(@PathVariable("id") Integer id) {
        securityService.checkPermission("SITES_DELETE");

        siteService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}/store", method = RequestMethod.GET)
    public ResponseEntity findAllStoresForSite(@PathVariable("id") Integer siteId) {
        securityService.checkPermission("STORES_READ");

        List<Store> domainModels = storeService.findAllBySiteIdUsingSpecs(siteId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleStoreView::new).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        securityService.checkPermission("SITES_READ");

        Site domainModel = siteService.findOneUsingSpecs(id);
        if (domainModel != null) {
            return ResponseEntity.ok(new SiteView(domainModel));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateOne(@PathVariable("id") Integer id, @RequestBody SiteView request) {
        securityService.checkPermission("SITES_UPDATE");

        Site requestModel = new Site((SiteView) request); //Cast ensures correct constructor is called
        Site domainModel = siteService.updateOne(id, requestModel);
        return ResponseEntity.ok(new SiteView(domainModel));
    }
}
