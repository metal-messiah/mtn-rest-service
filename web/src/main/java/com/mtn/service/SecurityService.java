package com.mtn.service;

import com.mtn.model.domain.UserProfile;
import com.mtn.security.MtnAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
		Authentication currentAuthentication = getCurrentAuthentication();
		if (currentAuthentication != null && !currentAuthentication.getPrincipal().equals("anonymousUser")) {
			return (UserProfile) currentAuthentication.getPrincipal();
		}
		return userProfileService.findOne(1);
	}

	private Authentication getCurrentAuthentication() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if (securityContext != null) {
			return securityContext.getAuthentication();
		}
		return null;
	}
}
