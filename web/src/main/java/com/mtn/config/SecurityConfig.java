package com.mtn.config;

import com.mtn.constant.PermissionType;
import com.mtn.model.domain.UserProfile;
import com.mtn.security.CustomJwtAuthenticationProvider;
import com.mtn.service.UserProfileService;
import com.mtn.service.AuthCacheService;
import com.mtn.security.MtnAuthentication;

import com.auth0.client.auth.AuthAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.UserInfo;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.net.Request;
import com.auth0.spring.security.api.authentication.JwtAuthentication;
import com.auth0.spring.security.api.authentication.PreAuthenticatedAuthenticationJsonWebToken;
import com.auth0.spring.security.api.JwtWebSecurityConfigurer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Allen on 5/10/2017.
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${auth0.api-audience}")
    private String apiAudience;
    @Value("${auth0.client-id}")
    private String clientId;
    @Value("${auth0.client-secret}")
    private String clientSecret;
    @Value("${auth0.domain}")
    private String domain;
    @Value("${auth0.issuer}")
    private String issuer;

    private AuthAPI authApi;

    @Autowired
    private AuthCacheService authCacheService;
    @Autowired
    private UserProfileService userProfileService;

    @PostConstruct
    private void init() {
        authApi = new AuthAPI(domain, clientId, clientSecret);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.sessionManagement().disable();
        http.csrf().disable();

        JwtWebSecurityConfigurer.forRS256(apiAudience, issuer, new MtnAuthenticationProvider()).configure(http)
                .authorizeRequests().antMatchers("/api/auth/user").authenticated()
                .antMatchers(HttpMethod.POST, "/api/user/**").hasAuthority(PermissionType.USERS_CREATE)
                .antMatchers(HttpMethod.GET, "/api/user/**").hasAuthority(PermissionType.USERS_READ)
                .antMatchers(HttpMethod.PUT, "/api/user/**").hasAuthority(PermissionType.USERS_UPDATE)
                .antMatchers(HttpMethod.DELETE, "/api/user/**").hasAuthority(PermissionType.USERS_DELETE)
                .antMatchers(HttpMethod.POST, "/api/role/**").hasAuthority(PermissionType.ROLES_CREATE)
                .antMatchers(HttpMethod.GET, "/api/role/**").hasAuthority(PermissionType.ROLES_READ)
                .antMatchers(HttpMethod.PUT, "/api/role/**").hasAuthority(PermissionType.ROLES_UPDATE)
                .antMatchers(HttpMethod.DELETE, "/api/role/**").hasAuthority(PermissionType.ROLES_DELETE)
                .antMatchers(HttpMethod.POST, "/api/group/**").hasAuthority(PermissionType.GROUPS_CREATE)
                .antMatchers(HttpMethod.GET, "/api/group/**").hasAuthority(PermissionType.GROUPS_READ)
                .antMatchers(HttpMethod.PUT, "/api/group/**").hasAuthority(PermissionType.GROUPS_UPDATE)
                .antMatchers(HttpMethod.DELETE, "/api/group/**").hasAuthority(PermissionType.GROUPS_DELETE)
                .antMatchers(HttpMethod.GET, "/api/permission/**").hasAuthority(PermissionType.PERMISSIONS_READ)
                .antMatchers(HttpMethod.PUT, "/api/permission/**").hasAuthority(PermissionType.PERMISSIONS_UPDATE)
                .antMatchers(HttpMethod.POST, "/api/site/**").hasAuthority(PermissionType.SITES_CREATE)
                .antMatchers(HttpMethod.GET, "/api/site/**").hasAuthority(PermissionType.SITES_READ)
                .antMatchers(HttpMethod.PUT, "/api/site/**").hasAuthority(PermissionType.SITES_UPDATE)
                .antMatchers(HttpMethod.DELETE, "/api/site/**").hasAuthority(PermissionType.SITES_DELETE)
                .antMatchers(HttpMethod.POST, "/api/store/**").hasAuthority(PermissionType.STORES_CREATE)
                .antMatchers(HttpMethod.GET, "/api/store/**").hasAuthority(PermissionType.STORES_READ)
                .antMatchers(HttpMethod.PUT, "/api/store/**").hasAuthority(PermissionType.STORES_UPDATE)
                .antMatchers(HttpMethod.DELETE, "/api/store/**").hasAuthority(PermissionType.STORES_DELETE)
                .antMatchers(HttpMethod.POST, "/api/banner/**").hasAuthority(PermissionType.STORES_CREATE)
                .antMatchers(HttpMethod.GET, "/api/banner/**").hasAuthority(PermissionType.STORES_READ)
                .antMatchers(HttpMethod.PUT, "/api/banner/**").hasAuthority(PermissionType.STORES_UPDATE)
                .antMatchers(HttpMethod.DELETE, "/api/banner/**").hasAuthority(PermissionType.STORES_DELETE)
                .antMatchers(HttpMethod.POST, "/api/company/**").hasAuthority(PermissionType.STORES_CREATE)
                .antMatchers(HttpMethod.GET, "/api/company/**").hasAuthority(PermissionType.STORES_READ)
                .antMatchers(HttpMethod.PUT, "/api/company/**").hasAuthority(PermissionType.STORES_UPDATE)
                .antMatchers(HttpMethod.DELETE, "/api/company/**").hasAuthority(PermissionType.STORES_DELETE)
                .antMatchers(HttpMethod.POST, "/api/store-casing/**").hasAuthority(PermissionType.STORE_CASINGS_CREATE)
                .antMatchers(HttpMethod.GET, "/api/store-casing/**").hasAuthority(PermissionType.STORE_CASINGS_READ)
                .antMatchers(HttpMethod.PUT, "/api/store-casing/**").hasAuthority(PermissionType.STORE_CASINGS_UPDATE)
                .antMatchers(HttpMethod.DELETE, "/api/store-casing/**")
                .hasAuthority(PermissionType.STORE_CASINGS_DELETE).antMatchers(HttpMethod.POST, "/api/store-survey/**")
                .hasAuthority(PermissionType.STORE_SURVEYS_CREATE).antMatchers(HttpMethod.GET, "/api/store-survey/**")
                .hasAuthority(PermissionType.STORE_SURVEYS_READ).antMatchers(HttpMethod.PUT, "/api/store-survey/**")
                .hasAuthority(PermissionType.STORE_SURVEYS_UPDATE)
                .antMatchers(HttpMethod.DELETE, "/api/store-survey/**")
                .hasAuthority(PermissionType.STORE_SURVEYS_DELETE).antMatchers(HttpMethod.POST, "/api/store-volume/**")
                .hasAuthority(PermissionType.STORE_VOLUMES_CREATE).antMatchers(HttpMethod.GET, "/api/store-volume/**")
                .hasAuthority(PermissionType.STORE_VOLUMES_READ).antMatchers(HttpMethod.PUT, "/api/store-volume/**")
                .hasAuthority(PermissionType.STORE_VOLUMES_UPDATE)
                .antMatchers(HttpMethod.DELETE, "/api/store-volume/**")
                .hasAuthority(PermissionType.STORE_VOLUMES_DELETE)
                .antMatchers(HttpMethod.POST, "/api/shopping-center/**")
                .hasAuthority(PermissionType.SHOPPING_CENTERS_CREATE)
                .antMatchers(HttpMethod.GET, "/api/shopping-center/**")
                .hasAuthority(PermissionType.SHOPPING_CENTERS_READ)
                .antMatchers(HttpMethod.PUT, "/api/shopping-center/**")
                .hasAuthority(PermissionType.SHOPPING_CENTERS_UPDATE)
                .antMatchers(HttpMethod.DELETE, "/api/shopping-center/**")
                .hasAuthority(PermissionType.SHOPPING_CENTERS_DELETE)
                .antMatchers(HttpMethod.POST, "/api/shopping-center-casing/**")
                .hasAuthority(PermissionType.SHOPPING_CENTER_CASINGS_CREATE)
                .antMatchers(HttpMethod.GET, "/api/shopping-center-casing/**")
                .hasAuthority(PermissionType.SHOPPING_CENTER_CASINGS_READ)
                .antMatchers(HttpMethod.PUT, "/api/shopping-center-casing/**")
                .hasAuthority(PermissionType.SHOPPING_CENTER_CASINGS_UPDATE)
                .antMatchers(HttpMethod.DELETE, "/api/shopping-center-casing/**")
                .hasAuthority(PermissionType.SHOPPING_CENTER_CASINGS_DELETE)
                .antMatchers(HttpMethod.POST, "/api/shopping-center-survey/**")
                .hasAuthority(PermissionType.SHOPPING_CENTER_SURVEYS_CREATE)
                .antMatchers(HttpMethod.GET, "/api/shopping-center-survey/**")
                .hasAuthority(PermissionType.SHOPPING_CENTER_SURVEYS_READ)
                .antMatchers(HttpMethod.PUT, "/api/shopping-center-survey/**")
                .hasAuthority(PermissionType.SHOPPING_CENTER_SURVEYS_UPDATE)
                .antMatchers(HttpMethod.DELETE, "/api/shopping-center-survey/**")
                .hasAuthority(PermissionType.SHOPPING_CENTER_SURVEYS_DELETE)
                .antMatchers(HttpMethod.POST, "/api/shopping-center-tenant/**")
                .hasAuthority(PermissionType.SHOPPING_CENTER_SURVEYS_CREATE)
                .antMatchers(HttpMethod.GET, "/api/shopping-center-tenant/**")
                .hasAuthority(PermissionType.SHOPPING_CENTER_SURVEYS_READ)
                .antMatchers(HttpMethod.PUT, "/api/shopping-center-tenant/**")
                .hasAuthority(PermissionType.SHOPPING_CENTER_SURVEYS_UPDATE)
                .antMatchers(HttpMethod.DELETE, "/api/shopping-center-tenant/**")
                .hasAuthority(PermissionType.SHOPPING_CENTER_SURVEYS_DELETE)
                .antMatchers(HttpMethod.POST, "/api/shopping-center-access/**")
                .hasAuthority(PermissionType.SHOPPING_CENTER_SURVEYS_CREATE)
                .antMatchers(HttpMethod.GET, "/api/shopping-center-access/**")
                .hasAuthority(PermissionType.SHOPPING_CENTER_SURVEYS_READ)
                .antMatchers(HttpMethod.PUT, "/api/shopping-center-access/**")
                .hasAuthority(PermissionType.SHOPPING_CENTER_SURVEYS_UPDATE)
                .antMatchers(HttpMethod.DELETE, "/api/shopping-center-access/**")
                .hasAuthority(PermissionType.SHOPPING_CENTER_SURVEYS_DELETE)
                .antMatchers(HttpMethod.POST, "/api/project/**").hasAuthority(PermissionType.PROJECTS_CREATE)
                .antMatchers(HttpMethod.GET, "/api/project/**").hasAuthority(PermissionType.PROJECTS_READ)
                .antMatchers(HttpMethod.PUT, "/api/project/**").hasAuthority(PermissionType.PROJECTS_UPDATE)
                .antMatchers(HttpMethod.DELETE, "/api/project/**").hasAuthority(PermissionType.PROJECTS_DELETE)
                .antMatchers(HttpMethod.POST, "/api/store-source/**").hasAuthority(PermissionType.STORE_SOURCE_CREATE)
                .antMatchers(HttpMethod.GET, "/api/store-source/**").hasAuthority(PermissionType.STORE_SOURCE_READ)
                .antMatchers(HttpMethod.PUT, "/api/store-source/**").hasAuthority(PermissionType.STORE_SOURCE_UPDATE)
                .antMatchers(HttpMethod.DELETE, "/api/store-source/**").hasAuthority(PermissionType.STORE_SOURCE_DELETE)
                .antMatchers(HttpMethod.POST, "/api/banner-source/**").hasAuthority(PermissionType.STORE_SOURCE_CREATE)
                .antMatchers(HttpMethod.GET, "/api/banner-source/**").hasAuthority(PermissionType.STORE_SOURCE_READ)
                .antMatchers(HttpMethod.PUT, "/api/banner-source/**").hasAuthority(PermissionType.STORE_SOURCE_UPDATE)
                .antMatchers(HttpMethod.DELETE, "/api/banner-source/**")
                .hasAuthority(PermissionType.STORE_SOURCE_DELETE).antMatchers(HttpMethod.GET, "/api/planned-grocery/**")
                .authenticated().antMatchers(HttpMethod.POST, "/api/planned-grocery/**").authenticated()
                .antMatchers(HttpMethod.GET, "/api/source-updatable/**").authenticated()
                .antMatchers(HttpMethod.POST, "/api/source-updatable/**").authenticated()
                .antMatchers(HttpMethod.GET, "/api/chainxy/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/chainxy/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/extraction/**").authenticated()
                .antMatchers(HttpMethod.GET, "/api/extraction-field-set/**").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/client-access-key/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/report/**").authenticated()
                .antMatchers(HttpMethod.GET, "/api/report/**").authenticated()
                .antMatchers(HttpMethod.GET, "/v2/api-docs").permitAll().antMatchers(HttpMethod.GET, "/favicon.ico")
                .permitAll().antMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll()
                .antMatchers(HttpMethod.GET, "/webjars/springfox-swagger-ui/**").permitAll()
                .antMatchers(HttpMethod.GET, "/swagger-resources/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/resource-quota/**").authenticated()
                .antMatchers(HttpMethod.POST, "/api/resource-quota/**").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/resource-quota/**").authenticated()
                .antMatchers(HttpMethod.GET, "/api/store-list/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/store-list/**").authenticated()
                .antMatchers(HttpMethod.POST, "/api/store-list/**").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/store-list/**").authenticated().anyRequest().denyAll();
    }

    /**
     * This AuthenticationProvider merges Auth0's JWT token validation with our own
     * custom flow.
     */
    private class MtnAuthenticationProvider implements AuthenticationProvider {

        private final CustomJwtAuthenticationProvider jwtAuthenticationProvider;

        /**
         * We have our own custom JWT AuthenticationProvider based on Auth0's because
         * it's the only way to add "leeway" time to the token check, allowing for a
         * small difference in clock time between the app server and Auth0's own
         * servers. They really need to make that easier to configure...
         */
        MtnAuthenticationProvider() {
            JwkProvider jwkProvider = (new JwkProviderBuilder(issuer)).build();
            this.jwtAuthenticationProvider = new CustomJwtAuthenticationProvider(jwkProvider, issuer, apiAudience);
        }

        /**
         * Calls the Auth0 JWT AuthenticationProvider first to validate the token, then
         * performs our custom userProfile lookup and associates the userProfile record
         * as the Principal in the SecurityContext so we can use it later in the app.
         */
        @Override
        public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
            this.jwtAuthenticationProvider.authenticate(authentication);

            UserProfile userProfile = getUserProfileRecord(authentication);
            return new MtnAuthentication(userProfile);
        }

        /**
         * Checks the authentication cache for the given userProfile, then finds or
         * creates the record in the database as needed.
         */
        private UserProfile getUserProfileRecord(Authentication authentication) {

            String accessToken = ((PreAuthenticatedAuthenticationJsonWebToken) authentication).getToken();

            UserInfo userInfo = getUserInfoFromAuthenticationToken(accessToken);
            if (userInfo == null) {
                throw new AuthenticationServiceException(
                        "Unable to retrieve user information from authentication provider");
            }
            String email = (String) userInfo.getValues().get("email");
            UserProfile persistedUserProfile = userProfileService.findOneByEmailUsingSpecs(email);
            if (persistedUserProfile == null) {
                throw new AuthenticationServiceException("User not found");
            }
            return persistedUserProfile;
        }

        /**
         * Calls Auth0 to retrieve the user's profile so we can use it to create our
         * userProfile record
         */
        private UserInfo getUserInfoFromAuthenticationToken(final String accessToken) {
            UserInfo cached = authCacheService.findOneByAccessToken(accessToken);
            if (cached != null) {
                return cached;
            }
            Request<UserInfo> request = authApi.userInfo(accessToken);
            try {
                UserInfo retrieved = request.execute();
                authCacheService.addOne(accessToken, retrieved);
                return retrieved;
            } catch (Auth0Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public boolean supports(final Class<?> authentication) {
            return JwtAuthentication.class.isAssignableFrom(authentication);
        }
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}
