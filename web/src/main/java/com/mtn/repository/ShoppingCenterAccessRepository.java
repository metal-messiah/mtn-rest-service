package com.mtn.repository;

import com.mtn.model.domain.ShoppingCenterAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by Allen on 5/6/2017.
 */
public interface ShoppingCenterAccessRepository extends JpaRepository<ShoppingCenterAccess, Integer>, JpaSpecificationExecutor<ShoppingCenterAccess> {

    List<ShoppingCenterAccess> findAllBySurveyId(Integer id);
}
