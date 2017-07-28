package com.mtn.controller;

import com.mtn.model.domain.StoreModel;
import com.mtn.model.view.SimpleStoreModelView;
import com.mtn.model.view.StoreModelView;
import com.mtn.service.SecurityService;
import com.mtn.service.StoreModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/store-model")
public class StoreModelController {

    @Autowired
    private StoreModelService modelService;
    @Autowired
    private SecurityService securityService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addOne(@RequestBody StoreModel request) {
        securityService.checkPermission("STORE_MODELS_CREATE");

        StoreModel domainModel = modelService.addOne(request);
        return ResponseEntity.ok(new StoreModelView(domainModel));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOne(@PathVariable("id") Integer id) {
        securityService.checkPermission("STORE_MODELS_DELETE");

        modelService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        securityService.checkPermission("STORE_MODELS_READ");

        StoreModel domainModel = modelService.findOneUsingSpecs(id);
        if (domainModel != null) {
            return ResponseEntity.ok(new StoreModelView(domainModel));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateOne(@PathVariable("id") Integer id, @RequestBody StoreModel request) {
        securityService.checkPermission("STORE_MODELS_UPDATE");

        StoreModel domainModel = modelService.updateOne(id, request);
        return ResponseEntity.ok(new SimpleStoreModelView(domainModel));
    }


}
