package com.mtn.util;

import com.auth0.client.auth.AuthAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.TokenHolder;
import com.auth0.net.AuthRequest;
import com.mtn.constant.Constants;

public class Auth0Util {

    private static final AuthAPI auth;
    public static final String apiAccessToken;
    public static final String userAccessToken;

    static {
        auth = new AuthAPI(Constants.AUTH0_DOMAIN, Constants.AUTH0_CLIENT_ID, Constants.AUTH0_SECRET);
        apiAccessToken = getApiAccessToken();
        userAccessToken = getUserAccessToken();
    }

    private static String getUserAccessToken() {
        AuthRequest authRequest = auth.login(Constants.TEST_USER_USERNAME, Constants.TEST_USER_PASSWORD, Constants.AUTH0_REALM);
        try {
            TokenHolder tokenHolder = authRequest.execute();
            return tokenHolder.getAccessToken();
        } catch (Auth0Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private static String getApiAccessToken() {
        AuthRequest authRequest = new AuthAPI(Constants.AUTH0_DOMAIN, Constants.AUTH0_API_CLIENT_ID, Constants.AUTH0_API_SECRET).requestToken(Constants.AUTH0_API_AUDIENCE);
        try {
            TokenHolder tokenHolder = authRequest.execute();
            return tokenHolder.getAccessToken();
        } catch (Auth0Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
