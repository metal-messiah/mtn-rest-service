package com.mtn.controller;

import com.mtn.model.domain.ShoppingCenterTenant;
import com.mtn.model.view.SimpleShoppingCenterTenantView;
import com.mtn.service.ShoppingCenterTenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Allen on 5/4/2017.
 */
@RestController
@RequestMapping("/api/shopping-center-tenant")
public class ShoppingCenterTenantController {

    @Autowired
    private ShoppingCenterTenantService tenantService;

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOne(@PathVariable("id") Integer id) {
        tenantService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        ShoppingCenterTenant domainModel = tenantService.findOne(id);
        if (domainModel != null) {
            return ResponseEntity.ok(new SimpleShoppingCenterTenantView(domainModel));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateOne(@PathVariable("id") Integer id, @RequestBody ShoppingCenterTenant request) {
        ShoppingCenterTenant domainModel = tenantService.updateOne(id, request);
        return ResponseEntity.ok(new SimpleShoppingCenterTenantView(domainModel));
    }
}