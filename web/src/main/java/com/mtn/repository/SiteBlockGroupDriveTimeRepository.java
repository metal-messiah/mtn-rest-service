package com.mtn.repository;

import com.mtn.model.domain.SiteBlockGroupDriveTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SiteBlockGroupDriveTimeRepository extends JpaRepository<SiteBlockGroupDriveTime, Integer>, JpaSpecificationExecutor<SiteBlockGroupDriveTime> {

	@Query(value = "SELECT dt.* from site_block_group_drive_time dt JOIN store st ON st.site_id = dt.site_id WHERE st.store_id = :storeId",
			nativeQuery = true)
	List<SiteBlockGroupDriveTime> findAllByStoreId(@Param("storeId") Integer storeId);

	@Query(value = "SELECT dt.* from site_block_group_drive_time dt WHERE dt.site_id = :siteId",
			nativeQuery = true)
	List<SiteBlockGroupDriveTime> findAllBySiteId(@Param("siteId") Integer siteId);

	List<SiteBlockGroupDriveTime> findAllByFipsEquals(String fips);
}
