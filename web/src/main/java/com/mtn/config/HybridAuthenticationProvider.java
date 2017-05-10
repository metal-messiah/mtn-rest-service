package com.mtn.config;

import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.spring.security.api.JwtAuthenticationProvider;
import com.auth0.spring.security.api.authentication.JwtAuthentication;
import com.mtn.model.MtnUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * Created by Allen on 5/10/2017.
 */
@Component
@PropertySource("classpath:application.properties")
public class HybridAuthenticationProvider implements AuthenticationProvider {

    @Value("${auth0.issuer}")
    private String issuer;

    @Value("${auth0.apiAudience}")
    private String apiAudience;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication jwtResult = getJwtAuthenticationProvider().authenticate(authentication);

        //TODO get mtn-access-token header
        //TODO call Auth0 API with accessToken to get user profile
        //TODO retrieve user profile from database
        //TODO build custom authentication
        //TODO store in cache with accessToken as key ("session")
        //TODO check for cached authentication by key before all the above
        //TODO return custom authentication

        //TODO if JWT authentication fails, check for cached authentication and clear it

        return jwtResult;
    }

    public JwtAuthenticationProvider getJwtAuthenticationProvider() {
        JwkProvider jwkProvider = (new JwkProviderBuilder(issuer)).build();
        return new JwtAuthenticationProvider(jwkProvider, issuer, apiAudience);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.isAssignableFrom(authentication);
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
            return null;
        }
    }
}
