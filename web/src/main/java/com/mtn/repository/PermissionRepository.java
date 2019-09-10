package com.mtn.repository;

import com.mtn.model.domain.Permission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PermissionRepository extends EntityRepository<Permission> {

	@Query(value = "select ap.* " +
			"from auth_permission ap join user_permission p on p.auth_permission_id = ap.auth_permission_id " +
			"where p.user_profile_id = :userId", nativeQuery = true)
	List<Permission> findUserPermissions(@Param("userId") Integer userId);

}
