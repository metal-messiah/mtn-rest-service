package com.mtn.controller;

import com.mtn.model.domain.ShoppingCenterCasing;
import com.mtn.model.view.ShoppingCenterCasingView;
import com.mtn.service.SecurityService;
import com.mtn.service.ShoppingCenterCasingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shopping-center-casing")
public class ShoppingCenterCasingController {

    @Autowired
    private ShoppingCenterCasingService casingService;
    @Autowired
    private SecurityService securityService;

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOne(@PathVariable("id") Integer id) {
        securityService.checkPermission("SHOPPING_CENTER_CASINGS_DELETE");

        casingService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        securityService.checkPermission("SHOPPING_CENTER_CASINGS_READ");

        ShoppingCenterCasing domainModel = casingService.findOneUsingSpecs(id);
        if (domainModel != null) {
            return ResponseEntity.ok(new ShoppingCenterCasingView(domainModel));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateOne(@PathVariable("id") Integer id, @RequestBody ShoppingCenterCasing request) {
        securityService.checkPermission("SHOPPING_CENTER_CASINGS_UPDATE");

        ShoppingCenterCasing domainModel = casingService.updateOne(id, request);
        return ResponseEntity.ok(new ShoppingCenterCasingView(domainModel));
    }
}
