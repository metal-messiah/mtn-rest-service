package com.mtn.service;

import com.mtn.model.domain.UserProfile;
import com.mtn.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

	private final UserProfileRepository userProfileRepository;

	@Autowired
	public SecurityService(UserProfileRepository userProfileRepository) {
		this.userProfileRepository = userProfileRepository;
	}

	public UserProfile getCurrentUser() {
		Authentication currentAuthentication = getCurrentAuthentication();
		if (currentAuthentication != null && !currentAuthentication.getPrincipal().equals("anonymousUser")) {
			return (UserProfile) currentAuthentication.getPrincipal();
		}
		return userProfileRepository.findOne(1);
	}

	private Authentication getCurrentAuthentication() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if (securityContext != null) {
			return securityContext.getAuthentication();
		}
		return null;
	}
}
