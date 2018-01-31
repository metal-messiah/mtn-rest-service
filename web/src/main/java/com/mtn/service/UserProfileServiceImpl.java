package com.mtn.service;

import com.mtn.model.domain.UserProfile;
import com.mtn.model.domain.Group;
import com.mtn.model.domain.Role;
import com.mtn.repository.UserProfileRepository;
import com.mtn.validators.UserProfileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mtn.repository.specification.UserProfileSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 4/21/2017.
 */
@Service
public class UserProfileServiceImpl extends EntityServiceImpl<UserProfile> implements UserProfileService {

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
    public UserProfileRepository getEntityRepository() {
        return userProfileRepository;
    }

    @Override
    public UserProfileValidator getValidator() {
        return userProfileValidator;
    }

    @Override
    public List<UserProfile> findAllByGroupIdUsingSpecs(Integer groupId) {
        return getEntityRepository().findAll(
                where(groupIdEquals(groupId))
                        .and(isNotSystemAdministrator())
                        .and(isNotDeleted())
        );
    }

    @Override
    public List<UserProfile> findAllByRoleIdUsingSpecs(Integer roleId) {
        return getEntityRepository().findAll(
                where(roleIdEquals(roleId))
                        .and(isNotSystemAdministrator())
                        .and(isNotDeleted())
        );
    }

    public Page<UserProfile> findAllUsingSpecs(Pageable page) {
        return getEntityRepository().findAll(
                where(isNotSystemAdministrator())
                        .and(isNotDeleted()),
                page
        );
    }

    @Override
    public UserProfile findOneUsingSpecs(Integer id) {
        return getEntityRepository().findOne(
                where(idEquals(id))
                        .and(isNotSystemAdministrator())
                        .and(isNotDeleted())
        );
    }

    @Override
    public UserProfile findOneByEmail(String email) {
        return getEntityRepository().findOneByEmailIgnoreCase(email);
    }

    @Override
    public UserProfile findSystemAdministrator() {
        return findOne(1);
    }

    @Override
    public Page<UserProfile> query(String q, Pageable page) {
        return getEntityRepository().findAll(
                where(
                        where(
                                emailContains(q))
                                .or(firstNameContains(q))
                                .or(lastNameContains(q)))
                        .and(isNotSystemAdministrator())
                        .and(isNotDeleted())
                , page
        );
    }

    @Override
    public UserProfile getUpdatedEntity(UserProfile existing, UserProfile request) {
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

    @Override
    public String getEntityName() {
        return "UserProfile";
    }

    @Override
    public void handleAssociationsOnDeletion(UserProfile existing) {
        // No Associations
    }

    /**
     * This is to be used during the authentication process, in which we retrieve the Auth0 UserInfo, and either update
     * an existing UserProfile record or create a new one.
     */
    @Override
    @Transactional
    public UserProfile findAndUpdateOrAddOneByEmail(String email) {
        UserProfile userProfile = findOneByEmail( email );
        return userProfile != null ? mergeOne( userProfile, email ) : addOne( UserProfile.build( email ) );
    }

    @Transactional
    protected UserProfile mergeOne( UserProfile existing, String email ) {
        existing.setEmail( email );
        return existing;
    }
}
