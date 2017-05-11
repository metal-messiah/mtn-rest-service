package com.mtn.service;

import com.mtn.model.MtnUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Created by Allen on 5/11/2017.
 */
@Service
public class SecurityService {

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
}
