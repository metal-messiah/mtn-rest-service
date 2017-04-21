package com.mtn.controller;

import com.mtn.model.converter.UserProfileToSimpleUserProfileConverter;
import com.mtn.model.domain.UserProfile;
import com.mtn.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Allen on 4/21/2017.
 */
@RestController
@RequestMapping("/api/user")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAll(@RequestParam("q") String q, Pageable page) {
        Page<UserProfile> domainModels = userProfileService.findAll(q, page);
        return ResponseEntity.ok(domainModels.map(new UserProfileToSimpleUserProfileConverter()));
    }
}
