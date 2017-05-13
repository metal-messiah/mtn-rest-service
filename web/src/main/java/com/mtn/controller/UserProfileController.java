package com.mtn.controller;

import com.mtn.model.converter.UserProfileToSimpleUserProfileViewConverter;
import com.mtn.model.domain.UserIdentity;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.view.SimpleUserIdentityView;
import com.mtn.model.view.UserProfileView;
import com.mtn.service.SecurityService;
import com.mtn.service.UserIdentityService;
import com.mtn.service.UserProfileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 4/21/2017.
 */
@RestController
@RequestMapping("/api/user")
public class UserProfileController {

    @Autowired
    private UserIdentityService userIdentityService;
    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private SecurityService securityService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addOne(@RequestBody UserProfile request) {
        securityService.checkPermission("USERS_CREATE");

        UserProfile domainModel = userProfileService.addOne(request);
        return ResponseEntity.ok(new UserProfileView(domainModel));
    }

    @RequestMapping(value = "/{id}/identity", method = RequestMethod.POST)
    public ResponseEntity addOneIdentityToUser(@PathVariable("id") Integer userProfileId, @RequestBody UserIdentity request) {
        UserIdentity domainModel = userProfileService.addOneIdentityToUser(userProfileId, request);
        return ResponseEntity.ok(new SimpleUserIdentityView(domainModel));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOne(@PathVariable("id") Integer id) {
        securityService.checkPermission("USERS_DELETE");

        userProfileService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAll(@RequestParam(value = "q", required = false) String q, Pageable page) {
        securityService.checkPermission("USERS_READ");

        Page<UserProfile> domainModels;
        if (StringUtils.isNotBlank(q)) {
            domainModels = userProfileService.query(q, page);
        } else {
            domainModels = userProfileService.findAllUsingSpecs(page);
        }

        return ResponseEntity.ok(domainModels.map(new UserProfileToSimpleUserProfileViewConverter()));
    }

    @RequestMapping(value = "/{id}/identity", method = RequestMethod.GET)
    public ResponseEntity findAllIdentitiesById(@PathVariable("id") Integer userProfileId) {
        List<UserIdentity> domainModels = userIdentityService.findAllByUserProfileId(userProfileId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleUserIdentityView::new).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findOne(@PathVariable("id") String id) {
        securityService.checkPermission("USERS_READ");

        UserProfile domainModel;
        if (StringUtils.isNumeric(id)) {
            domainModel = userProfileService.findOneUsingSpecs(Integer.parseInt(id));
        } else {
            domainModel = userProfileService.findOneUsingSpecs(id);
        }

        if (domainModel != null) {
            return ResponseEntity.ok(new UserProfileView(domainModel));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateOne(@PathVariable("id") Integer id, @RequestBody UserProfile request) {
        securityService.checkPermission("USERS_UPDATE");

        UserProfile domainModel = userProfileService.updateOne(id, request);
        return ResponseEntity.ok(new UserProfileView(domainModel));
    }
}
