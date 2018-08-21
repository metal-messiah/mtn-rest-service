package com.mtn.service;

import com.mtn.model.domain.UserProfile;
import com.mtn.model.domain.Group;
import com.mtn.model.domain.Role;
import com.mtn.repository.EntityRepository;
import com.mtn.repository.UserProfileRepository;
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

import static com.mtn.repository.specification.UserProfileSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 4/21/2017.
 */
@Service
public class UserProfileService extends EntityServiceImpl<UserProfile> {

	@Autowired
	private GroupService groupService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserProfileRepository userProfileRepository;
	@Autowired
	private UserProfileValidator userProfileValidator;

	@Override
	public void handleAssociationsOnCreation(UserProfile request) {
		request.setEmail(request.getEmail().toLowerCase());

		if (request.getRole() != null) {
			Role role = roleService.findOne(request.getRole().getId());
			if (role != null) {
				request.setRole(role);
			}
		}

		if (request.getGroup() != null) {
			Group group = groupService.findOne(request.getGroup().getId());
			if (group != null) {
				request.setGroup(group);
			}
		}
	}

	@Override
	public List<UserProfile> findAllByGroupIdUsingSpecs(Integer groupId) {
		return userProfileRepository.findAll(
				where(groupIdEquals(groupId))
						.and(isNotSystemAdministrator())
						.and(isNotDeleted())
		);
	}

	@Override
	public List<UserProfile> findAllByRoleIdUsingSpecs(Integer roleId) {
		return userProfileRepository.findAll(
				where(roleIdEquals(roleId))
						.and(isNotSystemAdministrator())
						.and(isNotDeleted())
		);
	}

	@Override
	public Page<UserProfile> findAllUsingSpecs(Pageable page) {
		Specification<UserProfile> spec = where(isNotSystemAdministrator()).and(isNotDeleted());
		return userProfileRepository.findAll(spec, page);
	}

	@Override
	public List<UserProfile> findAllUsingSpecs() {
		Specification<UserProfile> spec = where(isNotSystemAdministrator()).and(isNotDeleted());
		return userProfileRepository.findAll(spec);
	}

	@Override
	public UserProfile findOneUsingSpecs(Integer id) {
		UserProfile userProfile =  userProfileRepository.findOne(
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
	public UserProfile findOneByEmailUsingSpecs(String email) {
		return userProfileRepository.findOne(Specifications.where(UserProfileSpecifications.emailEqualsIgnoreCase(email))
				.and(UserProfileSpecifications.isNotDeleted()));
	}

	@Override
	public UserProfile findOneByEmail(String email) {
		return userProfileRepository.findOne(Specifications.where(UserProfileSpecifications.emailEqualsIgnoreCase(email)));
	}

	@Override
	public Page<UserProfile> query(String q, Pageable page) {
		return userProfileRepository.findAll(
				where(
						where(UserProfileSpecifications.emailContains(q))
								.or(UserProfileSpecifications.firstNameContains(q))
								.or(UserProfileSpecifications.lastNameContains(q)))
						.and(UserProfileSpecifications.isNotSystemAdministrator())
						.and(UserProfileSpecifications.isNotDeleted())
				, page
		);
	}

	@Override
	public UserProfile updateEntityAttributes(UserProfile existing, UserProfile request) {
		existing.setEmail(request.getEmail().toLowerCase());
		existing.setFirstName(request.getFirstName());
		existing.setLastName(request.getLastName());

		Group group = null;
		if (request.getGroup() != null) {
			group = groupService.findOneUsingSpecs(request.getGroup().getId());
		}
		existing.setGroup(group);

		Role role = null;
		if (request.getRole() != null) {
			role = roleService.findOneUsingSpecs(request.getRole().getId());
		}
		existing.setRole(role);

		return existing;
	}

}
