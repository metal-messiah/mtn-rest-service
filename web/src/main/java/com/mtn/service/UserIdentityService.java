package com.mtn.service;

import com.mtn.constant.Provider;
import com.mtn.model.domain.UserIdentity;
import com.mtn.repository.UserIdentityRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Allen on 4/22/2017.
 */
@Service
public class UserIdentityService extends ValidatingDataService<UserIdentity> {

    @Autowired
    private UserIdentityRepository userIdentityRepository;

    @Transactional
    public UserIdentity addOne(UserIdentity request) {
        validateForInsert(request);
        return userIdentityRepository.save(request);
    }

    @Transactional
    public void deleteOne(Integer id) {
        userIdentityRepository.delete(id);
    }

    public List<UserIdentity> findAllByUserProfileId(Integer userProfileId) {
        return userIdentityRepository.findAllByUserProfileId(userProfileId);
    }

    public UserIdentity findOne(Integer id) {
        return userIdentityRepository.findOne(id);
    }

    public UserIdentity findOneByProviderAndProviderUserId(Provider provider, String providerUserId) {
        return userIdentityRepository.findOneByProviderAndProviderUserId(provider, providerUserId);
    }

    @Transactional
    public UserIdentity updateOne(Integer id, UserIdentity request) {
        validateNotNull(request);
        validateForUpdate(request);

        UserIdentity existing = findOne(id);
        if (existing == null) {
            throw new IllegalArgumentException("No UserIdentity found with this id");
        }

        existing.setProvider(request.getProvider());
        existing.setProviderUserId(request.getProviderUserId());

        return existing;
    }

    @Override
    public String getEntityName() {
        return "UserIdentity";
    }

    @Override
    public void validateDoesNotExist(UserIdentity object) {
        UserIdentity existing = findOneByProviderAndProviderUserId(object.getProvider(), object.getProviderUserId());
        if (existing != null) {
            throw new IllegalArgumentException("UserIdentity with this provider and providerUserId already exists");
        }
    }

    @Override
    public void validateForInsert(UserIdentity object) {
        super.validateForInsert(object);

        if (object.getUserProfile() == null) {
            throw new IllegalStateException("UserIdentity was not mapped to UserProfile before saving!");
        }
    }

    @Override
    public void validateBusinessRules(UserIdentity object) {
        if (object.getProvider() == null) {
            throw new IllegalArgumentException(String.format("UserIdentity provider must be one of: %s", StringUtils.join(Provider.values())));
        } else if (StringUtils.isBlank(object.getProviderUserId())) {
            throw new IllegalArgumentException("UserIdentity providerUserId must be provided");
        }
    }
}
