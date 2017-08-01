package com.mtn.repository;

import com.mtn.model.domain.ShoppingCenterCasing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by Allen on 5/6/2017.
 */
public interface ShoppingCenterCasingRepository extends JpaRepository<ShoppingCenterCasing, Integer>, JpaSpecificationExecutor<ShoppingCenterCasing> {

    List<ShoppingCenterCasing> findAllByShoppingCenterId(Integer shoppingCenterId);

    List<ShoppingCenterCasing> findAllByInteractionsProjectIdAndDeletedDateIsNull(Integer id);

}
