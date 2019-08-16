package com.mtn.repository;

import com.mtn.model.domain.AvgSalesAreaPercentByBanner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AvgSalesAreaPercentByBannerRepository extends JpaRepository<AvgSalesAreaPercentByBanner, Integer>, JpaSpecificationExecutor<AvgSalesAreaPercentByBanner> {

}
