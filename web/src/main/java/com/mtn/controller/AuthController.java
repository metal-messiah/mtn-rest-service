package com.mtn.controller;

import com.mtn.model.domain.UserProfile;
import com.mtn.model.view.UserProfileView;
import com.mtn.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Allen on 5/11/2017.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private SecurityService securityService;

    /**
     * Returns the current user's profile
     */
    @GetMapping(value = "/user")
    public ResponseEntity getUserProfile() {
        UserProfile userProfile = securityService.getCurrentUser();
        if (userProfile != null) {
            return ResponseEntity.ok(new UserProfileView(userProfile));
        } else {
            throw new SecurityException();
        }
    }
}
