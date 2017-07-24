package com.mtn.model.view.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Allen on 5/11/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleAccessTokenView {

    private String token;

    public SimpleAccessTokenView() {
    }

    public SimpleAccessTokenView(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
