package com.mtn.service;

import com.auth0.client.auth.AuthAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.UserInfo;
import com.auth0.net.Request;
import com.mtn.util.MtnLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by Allen on 5/10/2017.
 */
@Service
public class Auth0Client {

    @Value("${auth0.client-id}")
    private String clientId;

    @Value("${auth0.domain}")
    private String domain;

    @Value("${auth0.secret}")
    private String secret;

    private AuthAPI authAPI;

    @PostConstruct
    public void init() {
        authAPI = new AuthAPI(domain, clientId, secret);
    }

    public UserInfo getUserProfile(String accessToken) {
        Request<UserInfo> request = authAPI.userInfo(accessToken);
        try {
            return request.execute();
        } catch (Auth0Exception e) {
            MtnLogger.error("Failed to retrieve Auth0 profile", e);
            return null;
        }
    }
}
