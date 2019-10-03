package com.mtn.repository;

import com.mtn.model.domain.AvgByBanner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AvgByBannerRepository extends JpaRepository<AvgByBanner, Integer>, JpaSpecificationExecutor<AvgByBanner> {

}
