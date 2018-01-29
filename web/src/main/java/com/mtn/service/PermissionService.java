package com.mtn.service;

import com.mtn.model.domain.Permission;

import java.util.List;

public interface PermissionService extends EntityService<Permission> {
	List<Permission> findAllByRoleId(Integer roleId);
}
