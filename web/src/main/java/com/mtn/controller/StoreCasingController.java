package com.mtn.controller;

import com.mtn.model.domain.StoreCasing;
import com.mtn.model.view.SimpleStoreCasingView;
import com.mtn.service.SecurityService;
import com.mtn.service.StoreCasingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/store-casing")
public class StoreCasingController {

    @Autowired
    private StoreCasingService storeCasingService;
    @Autowired
    private SecurityService securityService;


    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOne(@PathVariable("id") Integer id) {
        securityService.checkPermission("STORE_CASINGS_DELETE");

        storeCasingService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        securityService.checkPermission("STORE_CASINGS_READ");

        StoreCasing domainModel = storeCasingService.findOneUsingSpecs(id);
        if (domainModel != null) {
            return ResponseEntity.ok(new SimpleStoreCasingView(domainModel));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateOne(@PathVariable("id") Integer id, @RequestBody StoreCasing request) {
        securityService.checkPermission("STORE_CASINGS_UPDATE");

        StoreCasing domainModel = storeCasingService.updateOne(id, request);
        return ResponseEntity.ok(new SimpleStoreCasingView(domainModel));
    }


}
