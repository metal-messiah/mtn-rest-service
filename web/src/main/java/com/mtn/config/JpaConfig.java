package com.mtn.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by Allen on 4/21/2017.
 */
@Configuration
@EnableJpaRepositories(basePackages = {"com.mtn.repository"})
@EntityScan(basePackages = {"com.mtn.model.domain"}, basePackageClasses = {org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.class})
public class JpaConfig {
}
