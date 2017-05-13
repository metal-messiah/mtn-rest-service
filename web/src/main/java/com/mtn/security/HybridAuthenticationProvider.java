package com.mtn.security;

import com.auth0.json.auth.UserInfo;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.spring.security.api.authentication.JwtAuthentication;
import com.mtn.cache.AuthenticationCache;
import com.mtn.model.MtnUserDetails;
import com.mtn.model.domain.UserProfile;
import com.mtn.service.Auth0Client;
import com.mtn.service.UserProfileService;
import com.mtn.util.MtnLogger;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Allen on 5/10/2017.
 */
@Component
@PropertySource("classpath:application.properties")
public class HybridAuthenticationProvider implements AuthenticationProvider {

    @Value("${auth0.issuer}")
    private String issuer;

    @Value("${auth0.api-audience}")
    private String apiAudience;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private Auth0Client auth0Client;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationCache authenticationCache;

    @Autowired
    private UserProfileService userProfileService;

    private static final String TOKEN_HEADER = "mtn-access-token";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            //Validate JWT
            getJwtAuthenticationProvider().authenticate(authentication);

            //Check cache for existing authentication
            MtnAuthentication mtnAuthentication = authenticationCache.get(getAccessTokenHeader());

            //If not cached, retrieve the user profile and build the authentication
            if (mtnAuthentication == null) {
                MtnUserDetails userDetails = getUserDetails();
                mtnAuthentication = new MtnAuthentication(userDetails);
            }

            //Cache the authentication
            authenticationCache.put(getAccessTokenHeader(), mtnAuthentication);

            return mtnAuthentication;
        } catch (AuthenticationException e) {
            //Clear any associated MtnAuthentication from the cache if an accessToken is provided
            if (StringUtils.isNotBlank(httpServletRequest.getHeader(TOKEN_HEADER))) {
                authenticationCache.remove(httpServletRequest.getHeader(TOKEN_HEADER));
            }

            List<String> headerValues = new ArrayList<>();
            Enumeration<String> headerElements = httpServletRequest.getHeaderNames();
            while (headerElements.hasMoreElements()) {
                String headerName = headerElements.nextElement();
                String headerValue = httpServletRequest.getHeader(headerName);
                String header = String.format("\"%s\"=\"%s\"", headerName, headerValue);
                headerValues.add(header);
            }

            String requestUri = httpServletRequest.getRequestURI();
            String requestMethod = httpServletRequest.getMethod();
            String requestHeaders = StringUtils.join(headerValues, " ");

            MtnLogger.warn(String.format("Unauthorized Access Attempt - method=\"%s\" uri=\"%s\" headers=[%s]", requestUri, requestMethod, requestHeaders));

            throw e;
        }
    }

    private CustomJwtAuthenticationProvider getJwtAuthenticationProvider() {
        JwkProvider jwkProvider = (new JwkProviderBuilder(issuer)).build();
        return new CustomJwtAuthenticationProvider(jwkProvider, issuer, apiAudience);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.isAssignableFrom(authentication);
    }

    private MtnUserDetails getUserDetails() {
        String accessToken = getAccessTokenHeader();
        UserInfo auth0Profile = getAuth0Profile(accessToken);
        return getOrCreateMtnUserDetails(auth0Profile);
    }

    private String getAccessTokenHeader() {
        String header = httpServletRequest.getHeader(TOKEN_HEADER);
        if (StringUtils.isBlank(header)) {
            throw new MtnAuthenticationException("Not Authorized");
        }

        return header;
    }

    private UserInfo getAuth0Profile(String accessToken) {
        UserInfo auth0Profile = auth0Client.getUserProfile(accessToken);
        if (auth0Profile == null || StringUtils.isBlank((String) auth0Profile.getValues().get("email"))) {
            throw new MtnAuthenticationException("Not Authorized");
        }
        return auth0Profile;
    }

    private MtnUserDetails getOrCreateMtnUserDetails(UserInfo auth0Profile) {
        String email = (String) auth0Profile.getValues().get("email");
        MtnUserDetails userDetails = (MtnUserDetails) userDetailsService.loadUserByUsername(email);
        if (userDetails == null) {
            UserProfile userProfile = new UserProfile();
            userProfile.setEmail(email);
            userProfileService.addOne(userProfile);
            userDetails = (MtnUserDetails) userDetailsService.loadUserByUsername(email);
        }
        return userDetails;
    }

    public class MtnAuthenticationException extends AuthenticationException {

        public MtnAuthenticationException(String message) {
            super(message);
        }
    }

    public class MtnAuthentication implements Authentication {

        private MtnUserDetails mtnUserDetails;

        public MtnAuthentication(MtnUserDetails mtnUserDetails) {
            this.mtnUserDetails = mtnUserDetails;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return mtnUserDetails.getAuthorities();
        }

        @Override
        public Object getCredentials() {
            return null;
        }

        @Override
        public Object getDetails() {
            return mtnUserDetails;
        }

        @Override
        public Object getPrincipal() {
            return mtnUserDetails;
        }

        @Override
        public boolean isAuthenticated() {
            return true;
        }

        @Override
        public void setAuthenticated(boolean b) throws IllegalArgumentException {

        }

        @Override
        public String getName() {
            return mtnUserDetails.getEmail();
        }
    }
}
