package com.mtn.validators;

import com.mtn.model.domain.Permission;
import com.mtn.model.view.PermissionView;
import com.mtn.repository.PermissionRepository;
import com.mtn.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionValidator extends EntityValidator<Permission, PermissionView> {

	@Autowired
	public PermissionValidator(PermissionRepository repository) {
		super(repository);
	}

}
