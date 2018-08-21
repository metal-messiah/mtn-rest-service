package com.mtn.service;

import com.mtn.model.domain.UserProfile;
import com.mtn.model.domain.Permission;
import com.mtn.model.domain.Role;
import com.mtn.model.view.RoleView;
import com.mtn.repository.RoleRepository;
import com.mtn.repository.specification.RoleSpecifications;
import com.mtn.validators.RoleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.data.jpa.domain.Specifications.where;

@Service
public class RoleService extends EntityServiceImpl<Role, RoleView> {

	@Autowired
	public RoleService(EntityServiceDependencies services,
					   RoleRepository repository,
					   RoleValidator validator) {
		super(services, repository, validator);
	}

	public Page<Role> findAllByNameUsingSpecs(String name, Pageable page) {
		return this.repository.findAll(
				where(RoleSpecifications.displayNameContains(name))
						.and(RoleSpecifications.isNotDeleted())
						.and(RoleSpecifications.isNotAdmin())
				, page
		);
	}

	@Transactional
	public Role addOneMemberToRole(Integer roleId, UserProfile user) {
		Role role = findOneUsingSpecs(roleId);
		role.getMembers().add(user);
		return role;
	}

	@Transactional
	public Role addOnePermissionToRole(Integer roleId, Permission permission) {
		Role role = findOneUsingSpecs(roleId);
		role.getPermissions().add(permission);
		return role;
	}

	@Transactional
	public Role removeOneMemberFromRole(Integer roleId, Integer userId) {
		Role role = findOneUsingSpecs(roleId);

		role.getMembers().removeIf(member -> member.getId().equals(userId));
		role.setUpdatedBy(securityService.getCurrentUser());

		return role;
	}

	@Transactional
	public Role removeOnePermissionFromRole(Integer roleId, Integer permissionId) {
		Role role = findOneUsingSpecs(roleId);

		role.getPermissions().removeIf(permission -> permission.getId().equals(permissionId));
		role.setUpdatedBy(securityService.getCurrentUser());

		return role;
	}

	@Override
	protected Role createNewEntity() {
		return new Role();
	}

	@Override
	protected void setEntityAttributesFromRequest(Role entity, RoleView request) {
		entity.setDisplayName(request.getDisplayName());
		entity.setDescription(request.getDescription());
	}

	@Override
	public void handleAssociationsOnDeletion(Role existing) {
		existing.getMembers().forEach(member -> member.setRole(null));
		existing.getMembers().clear();
	}
}
