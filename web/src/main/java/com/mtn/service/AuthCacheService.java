package com.mtn.service;

import com.auth0.json.auth.UserInfo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthCacheService {

	private Set< AuthenticationCacheRecord > authenticationCache = new HashSet<>();

	/**
	 * Adding a UserInfo to the cache is intended to prevent us from needing to request from Auth0
	 * the UserInfo's record on every request.
	 */
	public void addOne( String accessToken, UserInfo userInfo ) {
		this.authenticationCache.add( new AuthenticationCacheRecord( accessToken, userInfo ) );
	}

	/**
	 * The idea is that during authentication, we'll check this cache for a Auth0.UserInfo record before
	 * we request it again. Authentication happens on EVERY request, and there's no reason to
	 * request it from Auth0 every time. That would lead to a Too Many Requests error.
	 */
	public UserInfo findOne( UserInfo userInfo ) {
		return authenticationCache.stream()
				.filter( record -> record.getUserInfo().getValues().get("email")
						.equals( userInfo.getValues().get("email") ) )
				.map( AuthenticationCacheRecord::getUserInfo )
				.findFirst()
				.orElse( null );
	}

	public UserInfo findOneByAccessToken(String accessToken ) {
		return authenticationCache.stream()
				.filter( record -> record.getAccessToken()
						.equals( accessToken ) )
				.map( AuthenticationCacheRecord::getUserInfo )
				.findFirst()
				.orElse( null );
	}

	@Scheduled( cron = "0 * * * * ?" ) //Every minute on the minute
	protected void clearExpiredCacheRecords() {
		List< AuthenticationCacheRecord > expiredRecords = authenticationCache.stream()
				.filter( record -> record.getExpirationDate()
						.isBefore( LocalDateTime.now() ) )
				.collect( Collectors.toList() );
		authenticationCache.removeAll( expiredRecords );
	}

	private class AuthenticationCacheRecord {

		private LocalDateTime expirationDate;
		private String accessToken;
		private UserInfo userInfo;

		AuthenticationCacheRecord( final String accessToken, final UserInfo userInfo ) {
			this.userInfo = userInfo;
			this.accessToken = accessToken;
			this.expirationDate = LocalDateTime.now()
					.plusHours( 2 );
		}

		LocalDateTime getExpirationDate() {
			return expirationDate;
		}

		UserInfo getUserInfo() {
			return userInfo;
		}

		String getAccessToken() { return accessToken; }
	}
}