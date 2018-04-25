package com.mtn.controller;

import com.mtn.model.domain.UserProfile;
import com.mtn.model.simpleView.SimpleUserProfileView;
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
    public ResponseEntity findAll(@RequestParam(value = "q", required = false) String q, Pageable page) {
        Page<UserProfile> domainModels;
        if (StringUtils.isNotBlank(q)) {
            domainModels = userProfileService.query(q, page);
        } else {
            domainModels = userProfileService.findAllUsingSpecs(page);
        }

        return ResponseEntity.ok(domainModels.map(this::getViewFromModel));
    }

    @Override
    public UserProfileService getEntityService() {
        return userProfileService;
    }

    @Override
    public Object getViewFromModel(UserProfile model) {
        return new UserProfileView(model);
    }

    @Override
    public Object getSimpleViewFromModel(UserProfile model) {
        return new SimpleUserProfileView(model);
    }
}
