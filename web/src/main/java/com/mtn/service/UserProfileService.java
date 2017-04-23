package com.mtn.service;

import com.mtn.model.domain.UserIdentity;
import com.mtn.model.domain.UserProfile;
import com.mtn.repository.UserProfileRepository;
import com.mtn.repository.specification.UserProfileSpecifications;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Allen on 4/21/2017.
 */
@Service
public class UserProfileService extends ValidatingDataService<UserProfile> {

    @Autowired
    private UserIdentityService userIdentityService;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Transactional
    public UserProfile addOne(UserProfile request) {
        validateForInsert(request);

        UserProfile systemAdministrator = findSystemAdministrator();
        request.setCreatedBy(systemAdministrator);
        request.setUpdatedBy(systemAdministrator);

        request.setEmail(request.getEmail().toLowerCase());

        for (UserIdentity userIdentity : request.getIdentities()) {
            userIdentity.setUserProfile(request);
        }

        return userProfileRepository.save(request);
    }

    @Transactional
    public UserIdentity addOneIdentityToUser(Integer userProfileId, UserIdentity request) {
        UserProfile existing = findOneUsingSpecs(userProfileId);
        if (existing == null) {
            throw new IllegalArgumentException("No UserProfile found with this id");
        }

        request.setUserProfile(existing);

        return userIdentityService.addOne(request);
    }

    @Transactional
    public void deleteOne(Integer id) {
        UserProfile existing = findOneUsingSpecs(id);
        if (existing == null) {
            throw new IllegalArgumentException("No UserProfile found with this id");
        }

        existing.setDeletedBy(findSystemAdministrator());
    }

    public Page<UserProfile> findAllUsingSpecs(Pageable page) {
        return userProfileRepository.findAll(UserProfileSpecifications.queryWhereNotSystemAdministratorAndNotDeleted(), page);
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
     *
     * Will not return a deleted or system adminstrator record.
     *
     * Should be used for any client-facing logic.
     */
    public UserProfile findOneUsingSpecs(Integer id) {
        return userProfileRepository.findOne(UserProfileSpecifications.queryWhereIdEquals(id));
    }

    /**
     * Does an unconditional find by Identity.providerUserId
     *
     * FOR INTERNAL USE ONLY - to protect deleted and system administrator records!
     */
    public UserProfile findOne(String providerUserId) {
        return userProfileRepository.findOneByProviderUserId(providerUserId);
    }

    /**
     * Safe find, using query specs.
     *
     * Will not return a deleted or system adminstrator record.
     *
     * Should be used for any client-facing logic.
     */
    public UserProfile findOneUsingSpecs(String providerUserId) {
        return userProfileRepository.findOne(UserProfileSpecifications.queryWhereProviderUserIdEquals(providerUserId));
    }

    public UserProfile findOneByEmail(String email) {
        return userProfileRepository.findOneByEmail(email);
    }

    /**
     * TODO Usage of this needs to be replaced with current user ASAP
     */
    public UserProfile findSystemAdministrator() {
        return findOne(1);
    }

    public Page<UserProfile> query(String q, Pageable page) {
        return userProfileRepository.findAll(UserProfileSpecifications.queryWhereEmailOrFirstNameOrLastNameContains(q), page);
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

        existing.setEmail(request.getEmail());
        existing.setFirstName(request.getFirstName());
        existing.setLastName(request.getLastName());

        return existing;
    }

    @Override
    public void validateDoesNotExist(UserProfile object) {
        UserProfile existing = findOneByEmail(object.getEmail().toLowerCase());
        if (existing != null) {
            throw new IllegalArgumentException("UserProfile with this email already exists");
        }

        object.getIdentities().forEach(userIdentityService::validateDoesNotExist);
    }

    @Override
    public void validateForInsert(UserProfile object) {
        validateNotNull(object);
        validateDoesNotExist(object);

        if (object.getId() != null) {
            throw new IllegalArgumentException("UserProfile id must be null");
        }

        object.getIdentities().forEach(userIdentityService::validateForInsert);

        validateBusinessRules(object);
    }

    @Override
    public void validateForUpdate(UserProfile object) {
        validateNotNull(object);
        if (object.getId() == null) {
            throw new IllegalArgumentException("UserProfile id must be provided");
        }

        validateBusinessRules(object);
    }

    @Override
    public void validateBusinessRules(UserProfile object) {
        if (StringUtils.isBlank(object.getEmail())) {
            throw new IllegalArgumentException("UserProfile email must be provided");
        }
    }

    @Override
    public void validateNotNull(UserProfile object) {
        if (object == null) {
            throw new IllegalArgumentException("UserProfile must not be null");
        }
    }
}
