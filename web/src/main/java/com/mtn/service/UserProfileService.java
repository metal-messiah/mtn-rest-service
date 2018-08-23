package com.mtn.service;

import com.mtn.model.domain.UserProfile;
import com.mtn.model.domain.Group;
import com.mtn.model.domain.Role;
import com.mtn.model.view.UserProfileView;
import com.mtn.repository.GroupRepository;
import com.mtn.repository.RoleRepository;
import com.mtn.repository.UserProfileRepository;
import com.mtn.repository.specification.AuditingEntitySpecifications;
import com.mtn.repository.specification.UserProfileSpecifications;
import com.mtn.validators.UserProfileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.springframework.data.jpa.domain.Specifications.where;

@Service
public class UserProfileService extends EntityService<UserProfile, UserProfileView> {

	private final GroupRepository groupRepository;
	private final RoleRepository roleRepository;

	@Autowired
	public UserProfileService(SecurityService securityService,
							  UserProfileRepository repository,
							  UserProfileValidator validator,
							  GroupRepository groupRepository,
							  RoleRepository roleRepository) {
		super(securityService, repository, validator, UserProfile::new);
		this.groupRepository = groupRepository;
		this.roleRepository = roleRepository;
	}

	public List<UserProfile> findAllByGroupIdUsingSpecs(Integer groupId) {
		return this.repository.findAll(
				where(UserProfileSpecifications.groupIdEquals(groupId))
						.and(UserProfileSpecifications.isNotSystemAdministrator())
						.and(UserProfileSpecifications.isNotDeleted())
		);
	}

	public List<UserProfile> findAllByRoleIdUsingSpecs(Integer roleId) {
		return this.repository.findAll(
				where(UserProfileSpecifications.roleIdEquals(roleId))
						.and(UserProfileSpecifications.isNotSystemAdministrator())
						.and(UserProfileSpecifications.isNotDeleted())
		);
	}

	@Override
	public Page<UserProfile> findAllUsingSpecs(Pageable page) {
		Specification<UserProfile> spec = where(UserProfileSpecifications.isNotSystemAdministrator())
				.and(UserProfileSpecifications.isNotDeleted());
		return this.repository.findAll(spec, page);
	}

	@Override
	public UserProfile findOneUsingSpecs(Integer id) {
		UserProfile userProfile =  this.repository.findOne(
				where(UserProfileSpecifications.isNotSystemAdministrator())
						.and(UserProfileSpecifications.idEquals(id))
						.and(UserProfileSpecifications.isNotDeleted())
		);
		if (userProfile == null) {
			throw new EntityNotFoundException("User Profile not found");
		}
		return userProfile;
	}

	@Override
	public void handleAssociationsOnDeletion(UserProfile existing) {
		existing.setRole(null);
		existing.setGroup(null);
	}

	@Override
	protected void setEntityAttributesFromRequest(UserProfile userProfile, UserProfileView request) {
		userProfile.setEmail(request.getEmail().toLowerCase());
		userProfile.setFirstName(request.getFirstName());
		userProfile.setLastName(request.getLastName());

		Group group = null;
		if (request.getGroup() != null) {
			group = groupRepository.findOne(
					where(AuditingEntitySpecifications.<Group>idEquals(request.getGroup().getId()))
							.and(AuditingEntitySpecifications.isNotDeleted()));
		}
		userProfile.setGroup(group);

		Role role = null;
		if (request.getRole() != null) {
			role = roleRepository.findOne(
					where(AuditingEntitySpecifications.<Role>idEquals(request.getRole().getId()))
							.and(AuditingEntitySpecifications.isNotDeleted()));
		}
		userProfile.setRole(role);
	}

	public UserProfile findOneByEmailUsingSpecs(String email) {
		return this.repository.findOne(Specifications.where(UserProfileSpecifications.emailEqualsIgnoreCase(email))
				.and(UserProfileSpecifications.isNotDeleted()));
	}

	public Page<UserProfile> query(String q, Pageable page) {
		return this.repository.findAll(
				where(
						where(UserProfileSpecifications.emailContains(q))
								.or(UserProfileSpecifications.firstNameContains(q))
								.or(UserProfileSpecifications.lastNameContains(q)))
						.and(UserProfileSpecifications.isNotSystemAdministrator())
						.and(UserProfileSpecifications.isNotDeleted())
				, page
		);
	}

}
