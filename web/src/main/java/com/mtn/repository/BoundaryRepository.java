package com.mtn.repository;

import com.mtn.model.domain.Boundary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoundaryRepository extends EntityRepository<Boundary> {
    @Query(value = "select b.* " + "from boundary b join user_boundary ub on b.boundary_id = ub.boundary_id "
            + "where ub.user_profile_id = :userId", nativeQuery = true)
    List<Boundary> findUserBoundaries(@Param("userId") Integer userId);

}
