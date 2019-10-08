package com.mtn.controller;

import com.mtn.model.domain.Permission;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.simpleView.SimplePermissionView;
import com.mtn.model.view.UserProfileView;
import com.mtn.service.PermissionService;
import com.mtn.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final SecurityService securityService;
    private final PermissionService permissionService;

    @Autowired
    public AuthController(SecurityService securityService,
                          PermissionService permissionService) {
        this.securityService = securityService;
        this.permissionService = permissionService;
    }

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

    @GetMapping("user-permissions")
    public ResponseEntity getUserPermissions() {
        UserProfile userProfile = securityService.getCurrentUser();
        List<Permission> permissions = this.permissionService.getUserPermissions(userProfile.getId());
        return ResponseEntity.ok(permissions.stream().map(SimplePermissionView::new).collect(Collectors.toList()));
    }
}
