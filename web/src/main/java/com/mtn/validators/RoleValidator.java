package com.mtn.validators;

import com.mtn.model.domain.Role;
import com.mtn.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleValidator extends ValidatingDataService<Role> {

	@Autowired
	private RoleService roleService;

	@Override
	public RoleService getEntityService() {
		return roleService;
	}

	@Override
	public void validateUnique(Role object) {
		Role existing = getEntityService().findOneByDisplayName(object.getDisplayName());
		if (existing != null && object.getId().equals(existing.getId())) {
			throw new IllegalArgumentException("Role with this name already exists");
		}
	}

	@Override
	public void validateForDelete(Role object) {
		super.validateForDelete(object);
		// Prevent Deletion of Super Admin Role
		if (object.getId().equals(1)) {
			throw new IllegalArgumentException("You may not delete this role!");
		}
	}

	@Override
	public void validateForUpdate(Role object, Role existing) {
		super.validateForUpdate(object, existing);
		// Prevent Update of Super Admin Role
		if (object.getId().equals(1)) {
			throw new IllegalArgumentException("You may not delete this role!");
		}
	}

}
