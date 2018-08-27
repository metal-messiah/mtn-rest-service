package com.mtn.service;

import com.mtn.model.domain.Permission;
import com.mtn.model.domain.Role;
import com.mtn.model.view.RoleView;
import com.mtn.repository.PermissionRepository;
import com.mtn.repository.RoleRepository;
import com.mtn.repository.specification.RoleSpecifications;
import com.mtn.validators.RoleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.Specifications.where;

@Service
public class RoleService extends EntityService<Role, RoleView> {

	private final PermissionRepository permissionRepository;

	@Autowired
	public RoleService(SecurityService securityService,
					   RoleRepository repository,
					   RoleValidator validator,
					   PermissionRepository permissionRepository) {
		super(securityService, repository, validator, Role::new);
		this.permissionRepository = permissionRepository;
	}

	public Page<Role> findAllByNameUsingSpecs(String name, Pageable page) {
		return this.repository.findAll(
				where(RoleSpecifications.displayNameContains(name))
						.and(RoleSpecifications.isNotDeleted())
						.and(RoleSpecifications.isNotAdmin())
				, page
		);
	}

	@Override
	protected void setEntityAttributesFromRequest(Role role, RoleView request) {
		role.setDisplayName(request.getDisplayName());
		role.setDescription(request.getDescription());

		Set<Permission> permissions = request.getPermissions().stream()
				.map(simplePermissionView -> this.permissionRepository.findOne(simplePermissionView.getId()))
				.collect(Collectors.toSet());

		role.setPermissions(permissions);
	}

	@Override
	public void handleAssociationsOnDeletion(Role existing) {
		existing.getMembers().forEach(member -> member.setRole(null));
		existing.getMembers().clear();
	}
}
