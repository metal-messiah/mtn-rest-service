package com.mtn.service;

import com.mtn.model.domain.*;
import com.mtn.model.view.UserProfileView;
import com.mtn.repository.PermissionRepository;
import com.mtn.repository.StoreListRepository;
import com.mtn.repository.UserProfileRepository;
import com.mtn.repository.specification.StoreListSpecifications;
import com.mtn.repository.specification.UserProfileSpecifications;
import com.mtn.validators.UserProfileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.Specifications.where;

@Service
public class UserProfileService extends EntityService<UserProfile, UserProfileView> {

	private final StoreListRepository storeListRepository;
	private final PermissionRepository permissionRepository;

	@Autowired
	public UserProfileService(SecurityService securityService,
							  UserProfileRepository repository,
							  UserProfileValidator validator,
							  StoreListRepository storeListRepository,
							  PermissionRepository permissionRepository) {
		super(securityService, repository, validator, UserProfile::new);
		this.storeListRepository = storeListRepository;
		this.permissionRepository = permissionRepository;
	}

	public List<UserProfile> findAllByGroupIdUsingSpecs(Integer groupId) {
		return this.repository.findAll(where(UserProfileSpecifications.groupIdEquals(groupId))
				.and(UserProfileSpecifications.isNotSystemAdministrator())
				.and(UserProfileSpecifications.isNotDeleted()));
	}

	@Transactional
	public UserProfile setUserBoundary(Integer userID, Boundary boundary) {
		UserProfile userProfile = this.findOne(userID);
		userProfile.addBoundary(boundary);
		return userProfile;
	}

	private boolean isAlreadySubscribed(final UserProfile userProfile, final Integer id) {
		List<StoreList> list = userProfile.getSubscribedStoreLists();
		return list.stream().anyMatch(o -> o.getId().equals(id));
	}

	public UserProfile subscribeToStoreListById(Integer userId, Integer storeListId) {
		UserProfile userProfile = this.repository.findOne(where(UserProfileSpecifications.idEquals(userId)));
		if (userProfile == null) {
			throw new EntityNotFoundException("User Profile not found");
		}
		if (!this.isAlreadySubscribed(userProfile, storeListId)) {
			StoreList storeList = this.storeListRepository
					.findOne(where(StoreListSpecifications.idEquals(storeListId)));
			if (storeList != null) {
				storeList.addSubscriber(userProfile);
				this.storeListRepository.save(storeList);
				userProfile.addSubscribedStoreList(storeList);
			} else {
				throw new EntityNotFoundException("Store List not found");
			}
		}
		return userProfile;
	}

	public UserProfile unsubscribeToStoreListById(Integer userId, Integer storeListId) {
		UserProfile userProfile = this.repository.findOne(where(UserProfileSpecifications.isNotSystemAdministrator())
				.and(UserProfileSpecifications.idEquals(userId)).and(UserProfileSpecifications.isNotDeleted()));
		if (userProfile == null) {
			throw new EntityNotFoundException("User Profile not found");
		}
		if (this.isAlreadySubscribed(userProfile, storeListId)) {
			StoreList storeList = this.storeListRepository
					.findOne(where(StoreListSpecifications.idEquals(storeListId)));
			if (storeList != null) {
				storeList.removeSubscriber(userProfile);
				this.storeListRepository.save(storeList);
				userProfile.removeSubscribedStoreList(storeList);
			}
		}
		return userProfile;
	}

	@Override
	public Page<UserProfile> findAllUsingSpecs(Pageable page) {
		Specification<UserProfile> spec = where(UserProfileSpecifications.isNotSystemAdministrator())
				.and(UserProfileSpecifications.isNotDeleted());
		return this.repository.findAll(spec, page);
	}

	@Override
	public UserProfile findOneUsingSpecs(Integer id) {
		UserProfile userProfile = this.repository.findOne(where(UserProfileSpecifications.isNotSystemAdministrator())
				.and(UserProfileSpecifications.idEquals(id)).and(UserProfileSpecifications.isNotDeleted()));
		if (userProfile == null) {
			throw new EntityNotFoundException("User Profile not found");
		}

		Specification<StoreList> matches = where(StoreListSpecifications.createdByEquals(id));
		List<StoreList> createdStoreLists = this.storeListRepository.findAll(matches);

		userProfile.setCreatedStoreLists(createdStoreLists);

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
	}

	public UserProfile findOneByEmailUsingSpecs(String email) {
		return this.repository.findOne(Specifications.where(UserProfileSpecifications.emailEqualsIgnoreCase(email))
				.and(UserProfileSpecifications.isNotDeleted()));
	}

	public Page<UserProfile> query(String q, Pageable page) {
		return this.repository.findAll(where(where(UserProfileSpecifications.emailContains(q))
				.or(UserProfileSpecifications.firstNameContains(q)).or(UserProfileSpecifications.lastNameContains(q)))
						.and(UserProfileSpecifications.isNotSystemAdministrator())
						.and(UserProfileSpecifications.isNotDeleted()),
				page);
	}

	public UserProfile setUserRole(Integer userId, Role role) {
		UserProfile userProfile = this.findOneUsingSpecs(userId);
		userProfile.setRole(role);
		return this.updateOne(userProfile);
	}

	public UserProfile setUserGroup(Integer userId, Group group) {
		UserProfile userProfile = this.findOneUsingSpecs(userId);
		userProfile.setGroup(group);
		return this.updateOne(userProfile);
	}

	public UserProfile updatePermissions(Integer userProfileId, List<Integer> newPermissionIds) {
		// Get the affected userProfile
		UserProfile userProfile = this.findOneUsingSpecs(userProfileId);

		// Remove any permissions not included in set
		userProfile.getPermissions().removeIf(p -> !newPermissionIds.contains(p.getId()));

		// get the list of permission ids remaining
		List<Integer> remainingPermissionIds = userProfile.getPermissions().stream().map(AuditingEntity::getId).collect(Collectors.toList());

		// Add permissions if not already present
		newPermissionIds.stream().filter(pId -> !remainingPermissionIds.contains(pId)).forEach(pId -> {
			Permission permission = this.permissionRepository.findOne(pId);
			userProfile.getPermissions().add(permission);
		});

		// Save the changes
		return this.updateOne(userProfile);
	}

}
