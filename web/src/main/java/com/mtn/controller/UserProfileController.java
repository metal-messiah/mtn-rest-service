package com.mtn.controller;

import com.mtn.model.domain.UserProfile;
import com.mtn.model.view.UserProfileView;
import com.mtn.service.UserProfileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserProfileController extends CrudController<UserProfile, UserProfileView> {

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        super(userProfileService, UserProfileView::new);
    }

    @GetMapping
    public ResponseEntity findAll(@RequestParam(value = "q", required = false) String q, Pageable page) {
        Page<UserProfile> domainModels;
        if (StringUtils.isNotBlank(q)) {
            domainModels = ((UserProfileService) this.entityService).query(q, page);
        } else {
            domainModels = this.entityService.findAllUsingSpecs(page);
        }

        return ResponseEntity.ok(domainModels.map(UserProfileView::new));
    }

}
