package com.mtn.controller;

import com.mtn.model.converter.UserProfileToSimpleUserProfileViewConverter;
import com.mtn.model.converter.UserProfileToUserProfileViewConverter;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.view.UserProfileView;
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
public class UserProfileController extends CrudControllerImpl<UserProfile> {

    @Autowired
    private UserProfileService userProfileService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAll(@RequestParam(value = "q", required = false) String q, @RequestParam(value = "simple", required = false) String simple, Pageable page) {
        Page<UserProfile> domainModels;
        if (StringUtils.isNotBlank(q)) {
            domainModels = userProfileService.query(q, page);
        } else {
            domainModels = userProfileService.findAllUsingSpecs(page);
        }

        if(simple != null && simple.equals("false")) {
            return ResponseEntity.ok(domainModels.map(new UserProfileToUserProfileViewConverter()));
        }
        return ResponseEntity.ok(domainModels.map(new UserProfileToSimpleUserProfileViewConverter()));
    }

    @Override
    public UserProfileService getEntityService() {
        return userProfileService;
    }

    @Override
    public UserProfileView getViewFromModel(Object model) {
        return new UserProfileView((UserProfile) model);
    }
}
