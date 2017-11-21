package com.mtn.service;

import com.mtn.model.domain.UserProfile;
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
	 * Adding a userProfile to the cache is intended to prevent us from needing to check the database
	 * for the userProfile's record on every request.
	 */
	public void addOne( String accessToken, UserProfile userProfile ) {
		this.authenticationCache.add( new AuthenticationCacheRecord( accessToken, userProfile ) );
	}

	/**
	 * The idea is that during authentication, we'll check this cache for our user record before
	 * we query the database. Authentication happens on EVERY request, and there's no reason to
	 * query the database that frequently.
	 */
	public UserProfile findOne( UserProfile userProfile ) {
		return authenticationCache.stream()
				.filter( record -> record.getUserProfile()
						.getEmail()
						.equals( userProfile.getEmail() ) )
				.map( AuthenticationCacheRecord::getUserProfile )
				.findFirst()
				.orElse( null );
	}

	public UserProfile findOneByAccessToken( String accessToken ) {
		return authenticationCache.stream()
				.filter( record -> record.getAccessToken()
						.equals( accessToken ) )
				.map( AuthenticationCacheRecord::getUserProfile )
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
		private UserProfile userProfile;

		AuthenticationCacheRecord( final String accessToken, final UserProfile userProfile ) {
			this.userProfile = userProfile;
			this.accessToken = accessToken;
			this.expirationDate = LocalDateTime.now()
					.plusHours( 2 );
		}

		LocalDateTime getExpirationDate() {
			return expirationDate;
		}

		UserProfile getUserProfile() {
			return userProfile;
		}

		String getAccessToken() { return accessToken; }
	}
}