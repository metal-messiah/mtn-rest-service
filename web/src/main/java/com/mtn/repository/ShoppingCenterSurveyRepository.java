package com.mtn.repository;

import com.mtn.model.domain.ShoppingCenterSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by Allen on 5/6/2017.
 */
public interface ShoppingCenterSurveyRepository extends JpaRepository<ShoppingCenterSurvey, Integer>, JpaSpecificationExecutor<ShoppingCenterSurvey> {

    List<ShoppingCenterSurvey> findAllByShoppingCenterId(Integer shoppingCenterId);

    List<ShoppingCenterSurvey> findAllByInteractionsProjectIdAndDeletedDateIsNull(Integer id);
}
