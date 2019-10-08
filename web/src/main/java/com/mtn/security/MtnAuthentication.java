package com.mtn.security;

import com.mtn.model.domain.UserProfile;
import com.mtn.service.PermissionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class MtnAuthentication implements Authentication {

	private UserProfile userProfile;
	private boolean authenticated;

	private final PermissionService permissionService;

	public MtnAuthentication(UserProfile userProfile, PermissionService permissionService) {
		this.userProfile = userProfile;
		this.permissionService = permissionService;
	}

	@Override
	public Collection< ? extends GrantedAuthority > getAuthorities() {
		return this.permissionService.getUserPermissions(this.userProfile.getId());
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getDetails() {
		return this.userProfile;
	}

	@Override
	public Object getPrincipal() {
		return this.userProfile;
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated( final boolean b ) throws IllegalArgumentException {
		this.authenticated = b;
	}

	@Override
	public String getName() {
		return this.userProfile.getEmail();
	}
}
