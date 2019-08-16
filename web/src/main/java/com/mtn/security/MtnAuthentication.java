package com.mtn.security;

import com.mtn.model.domain.UserProfile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class MtnAuthentication implements Authentication {

	private UserProfile userProfile;
	private boolean authenticated;

	public MtnAuthentication( UserProfile userProfile ) {
		this.userProfile = userProfile;
	}

	@Override
	public Collection< ? extends GrantedAuthority > getAuthorities() {
		return userProfile.getPermissions();
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
