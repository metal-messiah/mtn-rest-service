package com.mtn.controller;

import com.mtn.model.converter.UserProfileToSimpleUserProfileViewConverter;
import com.mtn.model.converter.UserProfileToUserProfileViewConverter;
import com.mtn.model.domain.UserProfile;
import com.mtn.service.UserProfileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Allen on 4/21/2017.
 */
@RestController
@RequestMapping("/api/user")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addOne(@RequestBody UserProfile request) {
        UserProfile domainModel = userProfileService.addOne(request);
        return ResponseEntity.ok(UserProfileToUserProfileViewConverter.build(domainModel));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAll(@RequestParam(value = "q", required = false) String q, Pageable page) {
        Page<UserProfile> domainModels;
        if (StringUtils.isNotBlank(q)) {
            domainModels = userProfileService.query(q, page);
        } else {
            domainModels = userProfileService.findAll(page);
        }

        return ResponseEntity.ok(domainModels.map(new UserProfileToSimpleUserProfileViewConverter()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findOne(@PathVariable("id") String id) {
        UserProfile domainModel;
        if (StringUtils.isNumeric(id)) {
            domainModel = userProfileService.findOne(Integer.parseInt(id));
        } else {
            domainModel = userProfileService.findOne(id);
        }

        if (domainModel != null) {
            return ResponseEntity.ok(UserProfileToUserProfileViewConverter.build(domainModel));
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
