package com.mtn.repository;

import com.mtn.model.domain.ShoppingCenterTenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Allen on 5/6/2017.
 */
public interface ShoppingCenterTenantRepository extends JpaRepository<ShoppingCenterTenant, Integer> {

    List<ShoppingCenterTenant> findAllBySurveyId(Integer id);
}
