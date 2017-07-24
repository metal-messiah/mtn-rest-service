package com.mtn.util;

import org.springframework.http.HttpHeaders;

public class RequestUtil {

    private static final String MTN_ACCESS_TOKEN_HEADER = "mtn-access-token";

    public static HttpHeaders getDefaultHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, getAuthorizationHeaderValue());
        httpHeaders.set(MTN_ACCESS_TOKEN_HEADER, Auth0Util.apiAccessToken);
        return httpHeaders;
    }

    private static String getAuthorizationHeaderValue() {
        return String.format("Bearer %s", Auth0Util.userAccessToken);
    }
}
