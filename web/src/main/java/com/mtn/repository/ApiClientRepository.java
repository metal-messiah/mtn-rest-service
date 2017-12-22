package com.mtn.repository;

import com.mtn.model.domain.ApiClient;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Allen on 5/11/2017.
 */
public interface ApiClientRepository extends JpaRepository<ApiClient, Integer> {

    ApiClient findOneByClientId(String clientId);
}
