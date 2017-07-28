package com.mtn.controller;

import com.mtn.model.domain.ShoppingCenterAccess;
import com.mtn.model.view.ShoppingCenterAccessView;
import com.mtn.service.SecurityService;
import com.mtn.service.ShoppingCenterAccessService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private SecurityService securityService;

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOne(@PathVariable("id") Integer id) {
        securityService.checkPermission("SHOPPING_CENTER_SURVEYS_DELETE");

        accessService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        securityService.checkPermission("SHOPPING_CENTER_SURVEYS_READ");

        ShoppingCenterAccess domainModel = accessService.findOne(id);
        if (domainModel != null) {
            return ResponseEntity.ok(new ShoppingCenterAccessView(domainModel));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateOne(@PathVariable("id") Integer id, @RequestBody ShoppingCenterAccess request) {
        securityService.checkPermission("SHOPPING_CENTER_SURVEYS_UPDATE");

        ShoppingCenterAccess domainModel = accessService.updateOne(id, request);
        return ResponseEntity.ok(new ShoppingCenterAccessView(domainModel));
    }
}
