package com.mtn.test;

import com.mtn.BaseTest;
import com.mtn.model.MtnUserDetails;
import com.mtn.model.view.auth.MtnUserDetailsView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AuthTest extends BaseTest {

    @Test(expectedExceptions = {RestClientException.class})
    public void getUserShouldReturn401IfNotAuthenticated() {
        try {
            unauthorizedRestTemplate().getForObject(buildUrl("/api/auth/user"), MtnUserDetails.class);
        } catch (RestClientResponseException e) {
            Assert.assertEquals(e.getRawStatusCode(), 401);
            throw e;
        }
    }

    @Test
    public void getUserShouldReturn200WithFullUserProfileIfAuthenticated() {
        ResponseEntity<MtnUserDetailsView> response = restTemplate().getForEntity(buildUri("/api/auth/user"), MtnUserDetailsView.class);

        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatusCode().value(), 200);

        MtnUserDetailsView mtnUserDetails = response.getBody();
        Assert.assertNotNull(mtnUserDetails);
        Assert.assertNotNull(mtnUserDetails.getEmail());
        Assert.assertNotNull(mtnUserDetails.getFirstName());
        Assert.assertNotNull(mtnUserDetails.getLastName());
        Assert.assertFalse(mtnUserDetails.getPermissions().isEmpty());
    }
}
