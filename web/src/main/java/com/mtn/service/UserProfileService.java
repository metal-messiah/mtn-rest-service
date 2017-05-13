package com.mtn.service;

import com.mtn.exception.DeletedEntityReactivationException;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.domain.auth.Group;
import com.mtn.model.domain.auth.Role;
import com.mtn.repository.UserProfileRepository;
import org.apache.commons.lang3.StringUtils;
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
public class UserProfileService extends ValidatingDataService<UserProfile> {

    @Autowired
    private GroupService groupService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private SecurityService securityService;

    @Transactional
    public UserProfile addOne(UserProfile request) {
        try {
            validateForInsert(request);
        } catch (DeletedEntityReactivationException e) {
            return reactivateOne((UserProfile) e.getEntity(), request);
        }

        UserProfile currentUser = securityService.getCurrentPersistentUser();
        request.setCreatedBy(currentUser);
        request.setUpdatedBy(currentUser);

        request.setEmail(request.getEmail().toLowerCase());

        return userProfileRepository.save(request);
    }

    @Transactional
    public UserProfile addOneBySystemAdministrator(UserProfile request) {
        try {
            validateForInsert(request);
        } catch (DeletedEntityReactivationException e) {
            return reactivateOne((UserProfile) e.getEntity(), request);
        }

        UserProfile systemAdministrator = findSystemAdministrator();
        request.setCreatedBy(systemAdministrator);
        request.setUpdatedBy(systemAdministrator);

        request.setEmail(request.getEmail().toLowerCase());

        return userProfileRepository.save(request);
    }

    @Transactional
    public void deleteOne(Integer id) {
        UserProfile existing = findOneUsingSpecs(id);
        if (existing == null) {
            throw new IllegalArgumentException("No UserProfile found with this id");
        }

        existing.setDeletedBy(securityService.getCurrentPersistentUser());
    }

    public List<UserProfile> findAllByGroupIdUsingSpecs(Integer groupId) {
        return userProfileRepository.findAll(
                where(groupIdEquals(groupId))
                        .and(isNotSystemAdministrator())
                        .and(isNotDeleted())
        );
    }

    public List<UserProfile> findAllByRoleIdUsingSpecs(Integer roleId) {
        return userProfileRepository.findAll(
                where(roleIdEquals(roleId))
                        .and(isNotSystemAdministrator())
                        .and(isNotDeleted())
        );
    }

    public Page<UserProfile> findAllUsingSpecs(Pageable page) {
        return userProfileRepository.findAll(
                where(isNotSystemAdministrator())
                        .and(isNotDeleted()),
                page
        );
    }

    /**
     * Does an unconditional find by id
     * <p>
     * FOR INTERNAL USE ONLY - to protect deleted and system administrator records!
     */
    public UserProfile findOne(Integer id) {
        return userProfileRepository.findOne(id);
    }

    /**
     * Safe find, using query specs.
     * <p>
     * Will not return a deleted or system adminstrator record.
     * <p>
     * Should be used for any client-facing logic.
     */
    public UserProfile findOneUsingSpecs(Integer id) {
        return userProfileRepository.findOne(
                where(idEquals(id))
                        .and(isNotSystemAdministrator())
                        .and(isNotDeleted())
        );
    }

    public UserProfile findOneByEmail(String email) {
        return userProfileRepository.findOneByEmail(email);
    }

    public UserProfile findSystemAdministrator() {
        return findOne(1);
    }

    public Page<UserProfile> query(String q, Pageable page) {
        return userProfileRepository.findAll(
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

    @Transactional
    public UserProfile reactivateOne(UserProfile existing, UserProfile request) {
        existing.setDeletedBy(null);
        existing.setDeletedDate(null);

        return updateOne(existing, request);
    }

    @Transactional
    public UserProfile updateOne(Integer id, UserProfile request) {
        validateNotNull(request);
        validateForUpdate(request);

        request.setEmail(request.getEmail().toLowerCase());

        UserProfile existing = findOneUsingSpecs(id);
        if (existing == null) {
            throw new IllegalArgumentException("No UserProfile found with this id");
        }

        if (!existing.getEmail().equalsIgnoreCase(request.getEmail())) {
            validateDoesNotExist(request);
        }

        return updateOne(existing, request);
    }

    @Transactional
    public UserProfile updateOne(UserProfile existing, UserProfile request) {
        existing.setEmail(request.getEmail());
        existing.setFirstName(request.getFirstName());
        existing.setLastName(request.getLastName());
        existing.setUpdatedBy(securityService.getCurrentPersistentUser());

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
    public void validateDoesNotExist(UserProfile object) {
        UserProfile existing = findOneByEmail(object.getEmail().toLowerCase());
        if (existing != null) {
            if (existing.getDeletedDate() != null) {
                throw new DeletedEntityReactivationException(existing);
            } else {
                throw new IllegalArgumentException("UserProfile with this email already exists");
            }
        }
    }

    @Override
    public void validateForInsert(UserProfile object) {
        super.validateForInsert(object);
    }

    @Override
    public void validateBusinessRules(UserProfile object) {
        if (StringUtils.isBlank(object.getEmail())) {
            throw new IllegalArgumentException("UserProfile email must be provided");
        }
    }
}
