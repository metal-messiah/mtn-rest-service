package com.mtn.repository;

import com.mtn.model.domain.StoreCasing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface StoreCasingRepository extends JpaRepository<StoreCasing, Integer>, JpaSpecificationExecutor<StoreCasing> {

    List<StoreCasing> findAllByStoreId(Integer storeId);

    List<StoreCasing> findAllByProjectsIdAndDeletedDateIsNull(Integer id);

}

