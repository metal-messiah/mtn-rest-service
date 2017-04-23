package com.mtn.repository;

import com.mtn.model.domain.ShoppingCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Allen on 4/23/2017.
 */
public interface ShoppingCenterRepository extends JpaRepository<ShoppingCenter, Integer>, JpaSpecificationExecutor<ShoppingCenter> {
}
