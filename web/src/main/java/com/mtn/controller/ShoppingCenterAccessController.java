package com.mtn.controller;

import com.mtn.model.domain.ShoppingCenterAccess;
import com.mtn.model.view.ShoppingCenterAccessView;
import com.mtn.service.ShoppingCenterAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Allen on 5/4/2017.
 */
@RestController
@RequestMapping("/api/shopping-center-access")
public class ShoppingCenterAccessController {

    @Autowired
    private ShoppingCenterAccessService accessService;

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOne(@PathVariable("id") Integer id) {
        accessService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        ShoppingCenterAccess domainModel = accessService.findOneUsingSpecs(id);
        if (domainModel == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(new ShoppingCenterAccessView(domainModel));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateOne(@PathVariable("id") Integer id, @RequestBody ShoppingCenterAccess request) {
        ShoppingCenterAccess domainModel = accessService.updateOne(id, request);
        return ResponseEntity.ok(new ShoppingCenterAccessView(domainModel));
    }
}
