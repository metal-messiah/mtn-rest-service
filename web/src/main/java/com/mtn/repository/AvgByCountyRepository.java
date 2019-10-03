package com.mtn.repository;

import com.mtn.model.domain.AvgByCounty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AvgByCountyRepository extends JpaRepository<AvgByCounty, Integer>, JpaSpecificationExecutor<AvgByCounty> {

}
