package com.mtn.repository;

import com.mtn.model.domain.Boundary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoundaryRepository extends EntityRepository<Boundary> {

	@Query(value = "select b.* " +
			"from boundary b " +
			"join project p on p.boundary_id = b.boundary_id " +
			"where p.project_id = :projectId " +
			"and b.deleted_date is null", nativeQuery = true)
	Boundary getBoundaryForProject(@Param("projectId") Integer projectId);
}
