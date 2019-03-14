package com.mtn.config;

import com.mtn.correlation.CorrelationIdFilter;
import com.mtn.correlation.RequestLoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean correlationIdFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CorrelationIdFilter());
        registration.addUrlPatterns("/api/*");
        registration.setName("correlationIdFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean requestLoggingFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new RequestLoggingFilter());
        registration.addUrlPatterns("/api/*");
        registration.setName("requestLoggingFilter");
        registration.setOrder(2);
        return registration;
    }
}
