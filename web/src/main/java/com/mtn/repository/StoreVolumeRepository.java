package com.mtn.repository;

import com.mtn.model.domain.StoreVolume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface StoreVolumeRepository extends JpaRepository<StoreVolume, Integer>, JpaSpecificationExecutor<StoreVolume> {

    List<StoreVolume> findAllByStoreId(Integer storeId);
}
