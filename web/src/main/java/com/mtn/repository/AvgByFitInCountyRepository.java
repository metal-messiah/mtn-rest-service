package com.mtn.repository;

import com.mtn.model.domain.AvgByFitInCounty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AvgByFitInCountyRepository extends JpaRepository<AvgByFitInCounty, Integer>, JpaSpecificationExecutor<AvgByFitInCounty> {

}
