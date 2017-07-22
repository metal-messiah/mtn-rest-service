package com.mtn.repository;

import com.mtn.model.domain.StoreModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface StoreModelRepository extends JpaRepository<StoreModel, Integer>, JpaSpecificationExecutor<StoreModel> {

    List<StoreModel> findAllByStoreId(Integer storeId);

    List<StoreModel> findAllByProjectId(Integer projectId);
}

