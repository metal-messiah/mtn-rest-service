package com.mtn.service;

import com.mtn.model.domain.UserProfile;
import com.mtn.security.MtnAuthentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Created by Allen on 5/11/2017.
 */
@Service
public class SecurityService {

    public UserProfile getCurrentUser() {
        MtnAuthentication currentAuthentication = getCurrentAuthentication();
        if (currentAuthentication != null) {
            return (UserProfile) currentAuthentication.getPrincipal();
        }
        return null;
    }

    private MtnAuthentication getCurrentAuthentication() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext != null) {
            return (MtnAuthentication) securityContext.getAuthentication();
        }
        return null;
    }

    public void checkPermission(String permission) {

        MtnAuthentication currentAuthentication = getCurrentAuthentication();

        if (currentAuthentication != null) {
            //Check for system admin
            if (getCurrentUser().getEmail().equals("system.administrator@mtnra.com")) {
                return;
            }

            //Check permissions
            for (GrantedAuthority authority : currentAuthentication.getAuthorities()) {
                if (authority.getAuthority().equalsIgnoreCase(permission)) {
                    return;
                }
            }
        }
        throw new SecurityException();
    }
}
