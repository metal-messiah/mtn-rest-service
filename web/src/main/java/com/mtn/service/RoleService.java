package com.mtn.service;

import com.mtn.model.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService extends EntityService<Role> {
	Role addOneMemberToRole(Integer roleId, Integer userId);

	Role addOnePermissionToRole(Integer roleId, Integer permissionId);

	Page<Role> findAllByNameUsingSpecs(String name, Pageable page);

	Role findOneByDisplayName(String displayName);

	Role removeOneMemberFromRole(Integer roleId, Integer userId);

	Role removeOnePermissionFromRole(Integer roleId, Integer permissionId);
}
