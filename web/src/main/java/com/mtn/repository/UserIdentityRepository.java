package com.mtn.repository;

import com.mtn.constant.Provider;
import com.mtn.model.domain.UserIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Allen on 4/22/2017.
 */
public interface UserIdentityRepository extends JpaRepository<UserIdentity, Integer> {

    UserIdentity findOneByProviderAndProviderUserId(Provider provider, String providerUserId);
}
