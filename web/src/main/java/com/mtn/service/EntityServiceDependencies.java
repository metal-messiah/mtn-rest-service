package com.mtn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class EntityServiceDependencies {

	private final UserProfileService userProfileService;
	private final SecurityService securityService;

	@Autowired
	public EntityServiceDependencies(UserProfileService userProfileService, SecurityService securityService) {
		this.userProfileService = userProfileService;
		this.securityService = securityService;
	}

	public UserProfileService getUserProfileService() {
		return userProfileService;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}
}
