package com.mtn.controller;

import com.mtn.model.converter.UserIdentityToSimpleUserIdentityViewConverter;
import com.mtn.model.domain.UserIdentity;
import com.mtn.service.UserIdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Allen on 4/22/2017.
 */
@RestController
@RequestMapping("/api/user/identity")
public class UserIdentityController {

    @Autowired
    private UserIdentityService userIdentityService;

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOne(@PathVariable("id") Integer id) {
        userIdentityService.deleteOne(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        UserIdentity domainModel = userIdentityService.findOne(id);
        if (domainModel != null) {
            return ResponseEntity.ok(UserIdentityToSimpleUserIdentityViewConverter.build(domainModel));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateOne(@PathVariable("id") Integer id, @RequestBody UserIdentity request) {
        UserIdentity domainModel = userIdentityService.updateOne(id, request);
        return ResponseEntity.ok(UserIdentityToSimpleUserIdentityViewConverter.build(domainModel));
    }

}
