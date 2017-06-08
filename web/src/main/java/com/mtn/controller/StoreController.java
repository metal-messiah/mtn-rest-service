package com.mtn.controller;

import com.mtn.model.domain.Store;
import com.mtn.model.view.StoreView;
import com.mtn.service.SecurityService;
import com.mtn.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Allen on 4/25/2017.
 */
@RestController
@RequestMapping("/api/store")
public class StoreController {

    @Autowired
    private StoreService storeService;
    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOne(@PathVariable("id") Integer id) {
        securityService.checkPermission("STORES_DELETE");

        storeService.deleteOne(id);
        return ResponseEntity.noContent().build();
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
}
