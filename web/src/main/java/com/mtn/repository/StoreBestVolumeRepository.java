package com.mtn.repository;

import com.mtn.model.domain.StoreBestVolume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface StoreBestVolumeRepository extends JpaRepository<StoreBestVolume, Integer>, JpaSpecificationExecutor<StoreBestVolume> {

	Optional<StoreBestVolume> findOneByStoreIdEquals(Integer storeId);

}
