package com.mtn.repository;

import com.mtn.model.domain.BannerSourceSummary;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

public interface BannerSourceSummaryRepository extends Repository<BannerSourceSummary, Integer>, JpaSpecificationExecutor<BannerSourceSummary> {

}
