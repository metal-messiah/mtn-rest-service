package com.mtn.repository;

import com.mtn.model.domain.AvgByFit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AvgByFitRepository extends JpaRepository<AvgByFit, Integer>, JpaSpecificationExecutor<AvgByFit> {

}
