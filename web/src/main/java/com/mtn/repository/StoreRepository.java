package com.mtn.repository;

import com.mtn.model.domain.Store;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends EntityRepository<Store> {

	@Query(value = "select st.* " +
			"from store st " +
			"join store_casing sc on st.store_id = sc.store_id " +
			"join store_casing_project scp on scp.store_casing_id = sc.store_casing_id " +
			"where scp.project_id = :projectId " +
			"and sc.deleted_date is null " +
			"and st.deleted_date is null", nativeQuery = true)
	List<Store> getStoresCasedForProject(@Param("projectId") Integer projectId);
}
