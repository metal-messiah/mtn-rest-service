package com.mtn.service;

import com.mtn.model.domain.AuditingEntity;
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

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.Specifications.not;
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

	@Override
	public Page<Role> findAllUsingSpecs(Pageable page) {
		return this.repository.findAll(where(not(RoleSpecifications.isAdmin()))
						.and(RoleSpecifications.isNotDeleted()), page);
	}

	public Role updatePermissions(Integer roleId, List<Integer> newPermissionIds) {
		// Get the affected role
		Role role = this.findOneUsingSpecs(roleId);

		// Remove any permissions not included in set
		role.getPermissions().removeIf(p -> !newPermissionIds.contains(p.getId()));

		// get the list of permission ids remaining
		List<Integer> remainingPermissionIds = role.getPermissions().stream().map(AuditingEntity::getId).collect(Collectors.toList());

		// Add permissions if not already present
		newPermissionIds.stream().filter(pId -> !remainingPermissionIds.contains(pId)).forEach(pId -> {
			Permission permission = this.permissionRepository.findOne(pId);
			role.getPermissions().add(permission);
		});

		// Save the changes
		return this.updateOne(role);
	}

	@Override
	protected void setEntityAttributesFromRequest(Role role, RoleView request) {
		role.setDisplayName(request.getDisplayName());
		role.setDescription(request.getDescription());
	}

	@Override
	public void handleAssociationsOnDeletion(Role existing) {
		existing.getMembers().forEach(member -> member.setRole(null));
		existing.getMembers().clear();
	}
}
