package com.mtn.service;

import com.mtn.model.domain.UserProfile;
import com.mtn.model.domain.Permission;
import com.mtn.model.domain.Role;
import com.mtn.repository.RoleRepository;
import com.mtn.validators.RoleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.mtn.repository.specification.RoleSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 5/6/2017.
 */
@Service
public class RoleServiceImpl extends EntityServiceImpl<Role> implements RoleService {

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private RoleValidator roleValidator;

	@Override
	@Transactional
	public Role addOneMemberToRole(Integer roleId, Integer userId) {
		Role role = findOneUsingSpecs(roleId);
		getValidator().validateNotNull(role);

		UserProfile member = userProfileService.findOneUsingSpecs(userId);
		userProfileService.getValidator().validateNotNull(member);

		role.getMembers().add(member);
		role.setUpdatedBy(securityService.getCurrentUser());

		return role;
	}

	@Override
	@Transactional
	public Role addOnePermissionToRole(Integer roleId, Integer permissionId) {
		Role role = findOneUsingSpecs(roleId);
		getValidator().validateNotNull(role);

		Permission permission = permissionService.findOne(permissionId);
		permissionService.getValidator().validateNotNull(permission);

		role.getPermissions().add(permission);
		role.setUpdatedBy(securityService.getCurrentUser());

		return role;
	}

	@Override
	public void handleAssociationsOnDeletion(Role existing) {
		existing.getMembers().forEach(member -> member.setRole(null));
		existing.setMembers(new HashSet<>());

		existing.getPermissions().forEach(permission -> permission.getRoles().remove(existing));
		existing.setPermissions(new HashSet<>());
	}

	@Override
	public void handleAssociationsOnCreation(Role request) {
		Set<Permission> permissions = new HashSet<>();
		request.getPermissions().forEach(permission -> permissions.add(permissionService.findOne(permission.getId())));
		request.setPermissions(permissions);
	}

	@Override
	public RoleRepository getEntityRepository() {
		return roleRepository;
	}

	@Override
	public RoleValidator getValidator() {
		return roleValidator;
	}

	@Override
	public Page<Role> findAllUsingSpecs(Pageable page) {
		return getEntityRepository().findAll(
				where(isNotDeleted())
						.and(isNotAdmin())
				, page
		);
	}

	@Override
	public Page<Role> findAllByNameUsingSpecs(String name, Pageable page) {
		return getEntityRepository().findAll(
				where(displayNameContains(name))
						.and(isNotDeleted())
						.and(isNotAdmin())
				, page
		);
	}

	@Override
	public Role findOneUsingSpecs(Integer id) {
		return getEntityRepository().findOne(
				where(idEquals(id))
						.and(isNotDeleted())
		);
	}

	@Override
	public Role findOneByDisplayName(String displayName) {
		return getEntityRepository().findOneByDisplayNameIgnoreCase(displayName);
	}

	@Override
	@Transactional
	public Role removeOneMemberFromRole(Integer roleId, Integer userId) {
		Role role = findOneUsingSpecs(roleId);
		getValidator().validateNotNull(role);

		role.getMembers().removeIf(member -> member.getId().equals(userId));
		role.setUpdatedBy(securityService.getCurrentUser());

		return role;
	}

	@Override
	@Transactional
	public Role removeOnePermissionFromRole(Integer roleId, Integer permissionId) {
		Role role = findOneUsingSpecs(roleId);
		getValidator().validateNotNull(role);

		role.getPermissions().removeIf(permission -> permission.getId().equals(permissionId));
		role.setUpdatedBy(securityService.getCurrentUser());

		return role;
	}

	@Override
	public Role updateEntityAttributes(Role existing, Role request) {
		existing.setDisplayName(request.getDisplayName());
		existing.setDescription(request.getDescription());

		updateMembers(existing, request);
		updatePermissions(existing, request);

		return existing;
	}

	private void updateMembers(Role existingRole, Role request) {
		//Remove members
		List<UserProfile> removedRoles = new ArrayList<>();

		for (UserProfile member : existingRole.getMembers()) {
			boolean found = request.getMembers().stream()
					.anyMatch(roleMember -> roleMember.getId().equals(member.getId()));

			if (!found) {
				member.setRole(null);
				removedRoles.add(member);
			}
		}
		existingRole.getMembers().removeAll(removedRoles);


		//Add members
		for (UserProfile member : request.getMembers()) {
			boolean found = existingRole.getMembers().stream()
					.anyMatch(roleMember -> roleMember.getId().equals(member.getId()));
			if (!found) {
				UserProfile existingMember = userProfileService.findOneUsingSpecs(member.getId());
				if (existingMember != null) {
					existingRole.getMembers().add(existingMember);
					existingMember.setRole(existingRole);
				}
			}
		}
	}

	private void updatePermissions(Role existingRole, Role request) {
		//Remove permissions
		List<Permission> removedPermissions = new ArrayList<>();
		for (Permission permission : existingRole.getPermissions()) {
			if (!request.getPermissions().contains(permission)) {
				removedPermissions.add(permission);
			}
		}
		existingRole.getPermissions().removeAll(removedPermissions);

		//Add permissions
		for (Permission permission : request.getPermissions()) {
			if (!existingRole.getPermissions().contains(permission)) {
				Permission existingPermission = permissionService.findOne(permission.getId());
				if (existingPermission != null) {
					existingRole.getPermissions().add(existingPermission);
					existingPermission.getRoles().remove(existingRole);
				}
			}
		}
	}

	@Override
	public String getEntityName() {
		return "Role";
	}
}
