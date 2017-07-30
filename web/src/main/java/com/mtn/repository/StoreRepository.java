package com.mtn.repository;

import com.mtn.model.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Allen on 4/26/2017.
 */
public interface StoreRepository extends JpaRepository<Store, Integer>, JpaSpecificationExecutor<Store> {

    Store findOneBySurveysId(Integer id);

    Store findOneByCasingsId(Integer id);
}
