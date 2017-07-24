package com.mtn;

import com.mtn.constant.Constants;
import com.mtn.rest.DefaultInterceptor;
import com.mtn.util.FlywayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.net.URI;
import java.net.URISyntaxException;

public class BaseTest {

    protected static final Logger logger = LoggerFactory.getLogger("BaseTest");

    @BeforeSuite
    public void beforeSuite() {
        FlywayUtil.initDatabase();
    }

    @AfterSuite
    public void afterSuite() {
        FlywayUtil.cleanDatabase();
    }

    protected RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new DefaultInterceptor());
        return restTemplate;
    }

    protected RestTemplate unauthorizedRestTemplate() {
        return new RestTemplate();
    }

    protected String buildUrl(String relativePath) {
        return String.format("%s%s", Constants.SERVER_HOST, relativePath);
    }

    protected URI buildUri(String relativePath) {
        try {
            return new URI(buildUrl(relativePath));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
}
