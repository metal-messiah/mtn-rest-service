package com.mtn.service;

import com.mtn.model.domain.UserProfile;
import com.mtn.security.MtnAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
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

    public UserProfile getCurrentUser() {
        MtnAuthentication currentAuthentication = getCurrentAuthentication();
        if (currentAuthentication != null) {
            return (UserProfile) currentAuthentication.getPrincipal();
        } else {
            return userProfileService.findOne(1);
        }
    }

    private MtnAuthentication getCurrentAuthentication() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext != null) {
            return (MtnAuthentication) securityContext.getAuthentication();
        }
        return null;
    }
}
