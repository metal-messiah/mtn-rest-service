package com.mtn.service;

import com.mtn.model.MtnUserDetails;
import com.mtn.model.domain.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Created by Allen on 5/11/2017.
 */
@Service
public class SecurityService {

    @Autowired
    private UserProfileService userProfileService;

    public MtnUserDetails getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext != null) {
            Authentication authentication = securityContext.getAuthentication();
            if (authentication != null) {
                return (MtnUserDetails) securityContext.getAuthentication().getPrincipal();
            }
        }
        return null;
    }

    public UserProfile getCurrentPersistentUser() {
        MtnUserDetails currentUser = getCurrentUser();
        if (currentUser != null) {
            return userProfileService.findOneByEmail(currentUser.getEmail());
        }
        return null;
    }

    public void checkPermission(String permission) {
        MtnUserDetails currentUser = getCurrentUser();
        if (currentUser != null) {
            //Check for system admin
            if (currentUser.getEmail().equals("system.administrator@mtnra.com")) {
                return;
            }

            //Check permissions
            for (SimpleGrantedAuthority authority : currentUser.getAuthorities()) {
                if (authority.getAuthority().equalsIgnoreCase(permission)) {
                    return;
                }
            }
        }
        throw new SecurityException();
    }
}