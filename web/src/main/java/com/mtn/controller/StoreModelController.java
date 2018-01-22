package com.mtn.controller;

import com.mtn.model.domain.StoreModel;
import com.mtn.model.simpleView.SimpleStoreModelView;
import com.mtn.model.view.StoreModelView;
import com.mtn.service.StoreModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/store-model")
public class StoreModelController {

    @Autowired
    private StoreModelService modelService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addOne(@RequestBody StoreModel request) {
        StoreModel domainModel = modelService.addOne(request);
        return ResponseEntity.ok(new StoreModelView(domainModel));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOne(@PathVariable("id") Integer id) {
        modelService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        StoreModel domainModel = modelService.findOneUsingSpecs(id);
        if (domainModel == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateOne(@PathVariable("id") Integer id, @RequestBody StoreModel request) {
        StoreModel domainModel = modelService.updateOne(id, request);
        return ResponseEntity.ok(new SimpleStoreModelView(domainModel));
    }


}
