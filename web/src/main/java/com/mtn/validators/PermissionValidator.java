package com.mtn.validators;

import com.mtn.model.domain.Permission;
import com.mtn.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionValidator extends ValidatingDataService<Permission> {

	@Autowired
	private PermissionService permissionService;

	@Override
	public PermissionService getEntityService() {
		return permissionService;
	}

}
