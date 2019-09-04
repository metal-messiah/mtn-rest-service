package com.mtn.repository;

import com.mtn.model.domain.SiteBlockGroupDriveTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SiteBlockGroupDriveTimeRepository extends JpaRepository<SiteBlockGroupDriveTime, Integer>, JpaSpecificationExecutor<SiteBlockGroupDriveTime> {

}
