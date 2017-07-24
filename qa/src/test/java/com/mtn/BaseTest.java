package com.mtn;

import com.mtn.constant.Constants;
import com.mtn.rest.DefaultInterceptor;
import com.mtn.util.FlywayUtil;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.net.URI;
import java.net.URISyntaxException;

public class BaseTest {

    protected static final Logger logger = LoggerFactory.getLogger("BaseTest");

    protected static NamedParameterJdbcTemplate jdbcTemplate;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        FlywayUtil.initDatabase();
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        FlywayUtil.cleanDatabase();
    }

    /**
     * We want a single instance of JdbcTemplate
     */
    protected NamedParameterJdbcTemplate jdbcTemplate() {
        if (jdbcTemplate == null) {
            DataSource dataSource = new DataSource();
            dataSource.setDriverClassName(Constants.DATASOURCE_DRIVER);
            dataSource.setUrl(Constants.DATASOURCE_URL);
            dataSource.setUsername(Constants.DATASOURCE_USERNAME);
            dataSource.setPassword(Constants.DATASOURCE_PASSWORD);
            jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        }
        return jdbcTemplate;
    }

    /**
     * But there's no reason we can't have more than one RestTemplate
     */
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
