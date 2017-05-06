package com.mtn.repository;

import com.mtn.model.domain.ShoppingCenterAccess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Allen on 5/6/2017.
 */
public interface ShoppingCenterAccessRepository extends JpaRepository<ShoppingCenterAccess, Integer> {

    List<ShoppingCenterAccess> findAllBySurveyId(Integer id);
}
