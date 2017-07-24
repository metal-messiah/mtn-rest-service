package com.mtn.test;

import com.mtn.BaseTest;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.view.UserProfileView;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class UserTest extends BaseTest {

    @Test(expectedExceptions = {RestClientException.class})
    public void postUserShouldReturn401IfNotAuthenticated() {
        try {
            unauthorizedRestTemplate().postForObject(buildUrl("/api/user"), new UserProfileView(), UserProfileView.class);
        } catch (RestClientResponseException e) {
            Assert.assertEquals(e.getRawStatusCode(), 401);
            throw e;
        }
    }

    @Test(expectedExceptions = {RestClientException.class})
    public void deleteUserShouldReturn401IfNotAuthenticated() {
        try {
            unauthorizedRestTemplate().delete(buildUrl("/api/user"));
        } catch (RestClientResponseException e) {
            Assert.assertEquals(e.getRawStatusCode(), 401);
            throw e;
        }
    }

    @Test(expectedExceptions = {RestClientException.class})
    public void getUserShouldReturn401IfNotAuthenticated() {
        try {
            unauthorizedRestTemplate().exchange(buildUrl("/api/user"), HttpMethod.GET, null, new ParameterizedTypeReference<List<UserProfile>>() {
            });
        } catch (RestClientResponseException e) {
            Assert.assertEquals(e.getRawStatusCode(), 401);
            throw e;
        }
    }

    @Test(expectedExceptions = {RestClientException.class})
    public void getUserByIdShouldReturn401IfNotAuthenticated() {
        try {
            unauthorizedRestTemplate().getForObject(buildUrl("/api/user/1"), UserProfileView.class);
        } catch (RestClientResponseException e) {
            Assert.assertEquals(e.getRawStatusCode(), 401);
            throw e;
        }
    }

    @Test(expectedExceptions = {RestClientException.class})
    public void putUserByIdShouldReturn401IfNotAuthenticated() {
        try {
            unauthorizedRestTemplate().exchange(buildUrl("/api/user/1"), HttpMethod.PUT, new HttpEntity<>(new UserProfileView()), UserProfileView.class);
        } catch (RestClientResponseException e) {
            Assert.assertEquals(e.getRawStatusCode(), 401);
            throw e;
        }
    }


}
