package com.mtn.service;

import com.mtn.exception.DeletedEntityReactivationException;
import com.mtn.exception.InvalidEntityException;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.domain.Permission;
import com.mtn.model.domain.Role;
import com.mtn.repository.RoleRepository;
import com.mtn.util.MtnLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
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
public class RoleService extends ValidatingDataService<Role> {

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private UserProfileService userProfileService;
	@Autowired
	private SecurityService securityService;

	@Transactional
	public Role addOne(Role request) {
		try {
			validateForInsert(request);
		} catch (DeletedEntityReactivationException e) {
			return reactivateOne((Role) e.getEntity(), request);
		} catch (InvalidEntityException e) {
			throw e;
		}

		UserProfile currentUser = securityService.getCurrentUser();
		request.setCreatedBy(currentUser);
		request.setUpdatedBy(currentUser);

		return roleRepository.save(request);
	}

	@Transactional
	public Role addOneMemberToRole(Integer roleId, Integer userId) {
		Role role = findOneUsingSpecs(roleId);
		validateNotNull(role);

		UserProfile member = userProfileService.findOneUsingSpecs(userId);
		userProfileService.validateNotNull(member);

		role.getMembers().add(member);
		role.setUpdatedBy(securityService.getCurrentUser());

		return role;
	}

	@Transactional
	public Role addOnePermissionToRole(Integer roleId, Integer permissionId) {
		Role role = findOneUsingSpecs(roleId);
		validateNotNull(role);

		Permission permission = permissionService.findOne(permissionId);
		permissionService.validateNotNull(permission);

		role.getPermissions().add(permission);
		role.setUpdatedBy(securityService.getCurrentUser());

		return role;
	}

	@Transactional
	public void deleteOne(Integer id) {
		Role existing = findOneUsingSpecs(id);
		if (existing == null) {
			throw new IllegalArgumentException("No Role found with this id");
		}

		existing.getMembers().forEach(member -> member.setRole(null));
		existing.setMembers(new HashSet<>());

		existing.getPermissions().forEach(permission -> permission.getRoles().remove(existing));
		existing.setPermissions(new HashSet<>());

		existing.setDeletedBy(securityService.getCurrentUser());
	}

	public Page<Role> findAllUsingSpecs(Pageable page) {
		return roleRepository.findAll(
				where(isNotDeleted())
				, page
		);
	}

	public Page<Role> findAllByNameUsingSpecs(String name, Pageable page) {
		return roleRepository.findAll(
				where(displayNameContains(name))
						.and(isNotDeleted())
				, page
		);
	}

	public Role findOne(Integer id) {
		return roleRepository.findOne(id);
	}

	public Role findOneUsingSpecs(Integer id) {
		return roleRepository.findOne(
				where(idEquals(id))
						.and(isNotDeleted())
		);
	}

	public Role findOneByDisplayName(String displayName) {
		return roleRepository.findOneByDisplayName(displayName);
	}

	@Transactional
	public Role reactivateOne(Role existingRole, Role request) {
		existingRole.setDeletedBy(null);
		existingRole.setDeletedDate(null);

		return updateOne(existingRole, request);
	}

	@Transactional
	public Role removeOneMemberFromRole(Integer roleId, Integer userId) {
		Role role = findOneUsingSpecs(roleId);
		validateNotNull(role);

		role.getMembers().removeIf(member -> member.getId().equals(userId));
		role.setUpdatedBy(securityService.getCurrentUser());

		return role;
	}

	@Transactional
	public Role removeOnePermissionFromRole(Integer roleId, Integer permissionId) {
		Role role = findOneUsingSpecs(roleId);
		validateNotNull(role);

		role.getPermissions().removeIf(permission -> permission.getId().equals(permissionId));
		role.setUpdatedBy(securityService.getCurrentUser());

		return role;
	}

	@Transactional
	public Role updateOne(Integer id, Role request) {
		validateForUpdate(request);

		Role existing = findOneUsingSpecs(id);
		validateNotNull(existing);

		return updateOne(existing, request);
	}

	@Transactional
	public Role updateOne(Role existing, Role request) {
		existing.setDisplayName(request.getDisplayName());
		existing.setDescription(request.getDescription());
		existing.setUpdatedBy(securityService.getCurrentUser());

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

	@Override
	public void validateBusinessRules(Role object) {
		//No special rules to enforce yet
	}

	@Override
	public void validateDoesNotExist(Role object) {
		Role existing = findOneByDisplayName(object.getDisplayName());
		if (existing != null) {
			if (existing.getDeletedDate() != null) {
				throw new DeletedEntityReactivationException(existing);
			} else {
				throw new IllegalArgumentException("Role with this displayName already exists");
			}
		}
	}
}
