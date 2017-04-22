package com.mtn.service;

import com.mtn.model.domain.UserProfile;
import com.mtn.repository.UserProfileRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by Allen on 4/21/2017.
 */
@Service
public class UserProfileService implements ValidatingDataService<UserProfile> {

    @Autowired
    private UserIdentityService userIdentityService;

    @Autowired
    private UserProfileRepository userProfileRepository;

    public UserProfile addOne(UserProfile request) {
        validateForInsert(request);
        validateDoesNotExist(request);

        UserProfile systemAdministrator = findSystemAdministrator();
        request.setCreatedBy(systemAdministrator);
        request.setUpdatedBy(systemAdministrator);

        return userProfileRepository.save(request);
    }

    public Page<UserProfile> findAll(Pageable page) {
        return userProfileRepository.findAll(page);
    }

    public UserProfile findOne(Integer id) {
        return userProfileRepository.findOne(id);
    }

    public UserProfile findOne(String providerUserId) {
        return userProfileRepository.findOneByProviderUserId(providerUserId);
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
        return userProfileRepository.findAllByQueryString(q, page);
    }

    public void validateDoesNotExist(UserProfile userProfile) {
        UserProfile existing = findOneByEmail(userProfile.getEmail());
        if (existing != null) {
            throw new IllegalArgumentException("UserProfile with this email already exists");
        }
    }

    @Override
    public void validateForInsert(UserProfile object) {
        validateNotNull(object);
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

        object.getIdentities().forEach(userIdentityService::validateForUpdate);

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
