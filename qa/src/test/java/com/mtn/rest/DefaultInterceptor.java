package com.mtn.rest;

import com.mtn.util.Auth0Util;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class DefaultInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        httpRequest.getHeaders().set(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", Auth0Util.apiAccessToken));
        httpRequest.getHeaders().set("mtn-access-token", Auth0Util.userAccessToken);
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }
}
