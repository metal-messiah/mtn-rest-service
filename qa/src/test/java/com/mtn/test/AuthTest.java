package com.mtn.test;

import com.mtn.BaseTest;
import com.mtn.model.MtnUserDetails;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AuthTest extends BaseTest {

    @Test(expectedExceptions = {RestClientException.class})
    public void getUserShouldReturn401IfNotAuthenticated() {
        try {
            unauthorizedRestTemplate().getForObject(buildUrl("/api/user"), MtnUserDetails.class);
        } catch (RestClientResponseException e) {
            Assert.assertEquals(e.getRawStatusCode(), 401);
            throw e;
        }
    }
}
