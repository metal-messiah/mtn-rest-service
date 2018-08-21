package com.mtn.validators;

import com.mtn.model.domain.Role;
import com.mtn.model.view.RoleView;
import com.mtn.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleValidator extends EntityValidator<Role, RoleView> {

	@Autowired
	public RoleValidator(RoleRepository repository) {
		super(repository);
	}

	@Override
	protected void validateUpdateInsertBusinessRules(RoleView request) {
		if (request.getId() == 1) {
			throw new IllegalArgumentException("This role may not be updated");
		}
	}

	@Override
	protected void validateDeletionBusinessRules(Integer id) {
		if (id.equals(1)) {
			throw new IllegalArgumentException("You may not delete this role!");
		}
	}

}
