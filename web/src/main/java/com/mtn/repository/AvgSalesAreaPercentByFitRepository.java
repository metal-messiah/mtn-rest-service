package com.mtn.repository;

import com.mtn.model.domain.AvgSalesAreaPercentByFit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AvgSalesAreaPercentByFitRepository extends JpaRepository<AvgSalesAreaPercentByFit, Integer>, JpaSpecificationExecutor<AvgSalesAreaPercentByFit> {

}
