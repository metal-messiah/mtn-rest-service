package com.mtn.test;

import com.mtn.BaseTest;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.view.UserProfileView;
import com.mtn.model.view.auth.SimpleAccessTokenView;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AuthTest extends BaseTest {

    @Test(expectedExceptions = {RestClientException.class})
    public void getUserShouldReturn401IfNotAuthenticated() {
        try {
            unauthorizedRestTemplate().getForObject(buildUrl("/api/auth/user"), UserProfile.class);
        } catch (RestClientResponseException e) {
            Assert.assertEquals(e.getRawStatusCode(), 401);
            throw e;
        }
    }

    @Test
    public void getUserShouldReturn200WithFullUserProfileIfAuthenticated() {
        ResponseEntity<UserProfileView> response = restTemplate().getForEntity(buildUri("/api/auth/user"), UserProfileView.class);

        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatusCode().value(), 200);

        UserProfileView mtnUserDetails = response.getBody();
        Assert.assertNotNull(mtnUserDetails);
        Assert.assertNotNull(mtnUserDetails.getEmail());
        Assert.assertNotNull(mtnUserDetails.getFirstName());
        Assert.assertNotNull(mtnUserDetails.getLastName());
//        Assert.assertFalse(mtnUserDetails.getPermissions().isEmpty());
    }

    @Test(expectedExceptions = {RestClientException.class})
    public void getTokenShouldReturn401IfNoMtnClientIdHeaderSent() {
        try {
            restTemplate().getForEntity(buildUri("/api/auth/token"), SimpleAccessTokenView.class);
        } catch (RestClientResponseException e) {
            Assert.assertEquals(e.getRawStatusCode(), 401);
            throw e;
        }
    }

    @Test(expectedExceptions = {RestClientException.class})
    public void getTokenShouldReturn401IfInvalidMtnClientIdHeaderSent() {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("mtn-client-id", "abc123");

            HttpEntity httpEntity = new HttpEntity(httpHeaders);

            restTemplate().exchange(buildUri("/api/auth/token"), HttpMethod.GET, httpEntity, SimpleAccessTokenView.class);
        } catch (RestClientResponseException e) {
            Assert.assertEquals(e.getRawStatusCode(), 401);
            throw e;
        }
    }

    @Test
    public void getTokenShouldReturn200WithTokenIfValidMtnClientIdHeaderSent() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("mtn-client-id", "YVQTbbTwp4ZIgmC9LtpuoG5gx0i8lUaR");

        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<SimpleAccessTokenView> response = restTemplate().exchange(buildUri("/api/auth/token"), HttpMethod.GET, httpEntity, SimpleAccessTokenView.class);

        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatusCode().value(), 200);

        SimpleAccessTokenView accessTokenView = response.getBody();
        Assert.assertNotNull(accessTokenView);
        Assert.assertNotNull(accessTokenView.getToken());
    }
}
