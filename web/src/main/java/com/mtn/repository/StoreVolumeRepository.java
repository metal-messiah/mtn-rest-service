package com.mtn.repository;

import com.mtn.model.domain.StoreVolume;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Tuple;
import java.util.List;

public interface StoreVolumeRepository extends EntityRepository<StoreVolume> {

	@Query(value = "select st.site_id, v.volume_total " +
			"from store_volume v " +
			"join store_casing stc on stc.store_volume_id = v.store_volume_id " +
			"join store_casing_project scp on scp.store_casing_id = stc.store_casing_id " +
			"join store st on st.store_id = stc.store_id " +
			"where scp.project_id = :projectId " +
			"and st.site_id in :siteIds " +
			"and stc.deleted_date is null " +
			"and v.deleted_date is null", nativeQuery = true)
	List<Tuple> findAllForSitesForProject(@Param("siteIds") List<Integer> siteIds, @Param("projectId") Integer projectId);

}
