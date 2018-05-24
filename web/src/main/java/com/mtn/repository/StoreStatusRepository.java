package com.mtn.repository;

import com.mtn.model.domain.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface StoreStatusRepository extends JpaRepository<StoreStatus, Integer>, JpaSpecificationExecutor<StoreStatus> {

    List<StoreStatus> findAllByStoreId(Integer storeId);
}
