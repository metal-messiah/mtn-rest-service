package com.mtn.controller;

import com.mtn.model.view.SimpleUserProfile;
import com.mtn.service.UserProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Allen on 4/21/2017.
 */
@RestController
@RequestMapping("/api/user")
@Api(tags = "Users", description = "MTN Rest Service Users")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(
            value = "Retrieve all user profiles",
            response = SimpleUserProfile.class,
            responseContainer = "List"
    )
    public ResponseEntity findAll() {
        List<SimpleUserProfile> results = SimpleUserProfile.build(userProfileService.findAll());
        return ResponseEntity.ok(results);
    }
}
