package com.mtn.repository;

import com.mtn.model.domain.UserBoundary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserBoundaryRepository extends EntityRepository<UserBoundary> {

	@Query(value = "select u.* from user_boundary u where u.user_profile_id = :userId and u.deleted_date is null", nativeQuery = true)
	List<UserBoundary> findUserBoundaries(@Param("userId") Integer userId);

}
