package com.mtn.config;

import com.auth0.spring.security.api.JwtWebSecurityConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by Allen on 5/10/2017.
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${auth0.issuer}")
    private String issuer;

    @Value("${auth0.api-audience}")
    private String apiAudience;

    @Autowired
    private HybridAuthenticationProvider hybridAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().disable();
        http.csrf().disable();

        JwtWebSecurityConfigurer
                .forRS256(apiAudience, issuer, hybridAuthenticationProvider)
                .configure(http)
                .authorizeRequests()
                .anyRequest().authenticated();
    }
}
