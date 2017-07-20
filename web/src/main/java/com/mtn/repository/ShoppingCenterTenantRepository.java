package com.mtn.repository;

import com.mtn.model.domain.ShoppingCenterTenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by Allen on 5/6/2017.
 */
public interface ShoppingCenterTenantRepository extends JpaRepository<ShoppingCenterTenant, Integer>, JpaSpecificationExecutor<ShoppingCenterTenant> {

    List<ShoppingCenterTenant> findAllBySurveyId(Integer id);
}
