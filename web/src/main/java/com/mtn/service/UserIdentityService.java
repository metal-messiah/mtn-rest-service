package com.mtn.service;

import com.mtn.constant.Provider;
import com.mtn.model.domain.UserIdentity;
import com.mtn.repository.UserIdentityRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Allen on 4/22/2017.
 */
@Service
public class UserIdentityService extends ValidatingDataService<UserIdentity> {

    @Autowired
    private UserIdentityRepository userIdentityRepository;

    public UserIdentity findOneByProviderAndProviderUserId(Provider provider, String providerUserId) {
        return userIdentityRepository.findOneByProviderAndProviderUserId(provider, providerUserId);
    }

    @Override
    void validateDoesNotExist(UserIdentity object) {
        UserIdentity existing = findOneByProviderAndProviderUserId(object.getProvider(), object.getProviderUserId());
        if (existing != null) {
            throw new IllegalArgumentException("UserIdentity with this provider and providerUserId already exists");
        }
    }

    @Override
    public void validateForInsert(UserIdentity object) {
        validateNotNull(object);

        if (object.getId() != null) {
            throw new IllegalArgumentException("UserIdentity id must be null");
        }

        validateBusinessRules(object);
    }

    @Override
    public void validateForUpdate(UserIdentity object) {
        validateNotNull(object);

        if (object.getId() == null) {
            throw new IllegalArgumentException("UserIdentity id must be provided");
        }

        validateBusinessRules(object);
    }

    @Override
    public void validateBusinessRules(UserIdentity object) {
        if (object.getProvider() == null) {
            throw new IllegalArgumentException(String.format("UserIdentity provider must be one of: %s", StringUtils.join(Provider.values())));
        } else if (StringUtils.isBlank(object.getProviderUserId())) {
            throw new IllegalArgumentException("UserIdentity providerUserId must be provided");
        }
    }

    @Override
    public void validateNotNull(UserIdentity object) {
        if (object == null) {
            throw new IllegalArgumentException("UserIdentity must not be null");
        }
    }
}
