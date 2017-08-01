package com.mtn.repository;

import com.mtn.model.domain.ShoppingCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by Allen on 4/23/2017.
 */
public interface ShoppingCenterRepository extends JpaRepository<ShoppingCenter, Integer>, JpaSpecificationExecutor<ShoppingCenter> {

    ShoppingCenter findOneBySurveysId(Integer id);

    ShoppingCenter findOneByCasingsId(Integer id);

    ShoppingCenter findOneBySitesStoresId(Integer id);

    List<ShoppingCenter> findAllByInteractionsProjectIdAndDeletedDateIsNull(Integer id);
}
