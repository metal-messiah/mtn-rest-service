package com.mtn.repository;

import com.mtn.model.domain.BlockGroupPopulationCentroid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BlockGroupPopulationCentroidRepository extends JpaRepository<BlockGroupPopulationCentroid, Integer>, JpaSpecificationExecutor<BlockGroupPopulationCentroid> {

	Optional<BlockGroupPopulationCentroid> findByFipsEquals(String fips);

}
