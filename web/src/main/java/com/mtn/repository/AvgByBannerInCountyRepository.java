package com.mtn.repository;

import com.mtn.model.domain.AvgByBannerInCounty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AvgByBannerInCountyRepository extends JpaRepository<AvgByBannerInCounty, Integer>, JpaSpecificationExecutor<AvgByBannerInCounty> {

}
