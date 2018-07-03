package com.mtn.repository;

import com.mtn.model.domain.ClientAccessKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Tyler on 7/3/2018.
 */
public interface ClientAccessKeyRepository extends JpaRepository<ClientAccessKey, Integer>, JpaSpecificationExecutor<ClientAccessKey> {
}
