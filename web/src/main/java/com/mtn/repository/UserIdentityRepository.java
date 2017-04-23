package com.mtn.repository;

import com.mtn.constant.Provider;
import com.mtn.model.domain.UserIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Allen on 4/22/2017.
 */
public interface UserIdentityRepository extends JpaRepository<UserIdentity, Integer> {

    List<UserIdentity> findAllByUserProfileId(Integer userProfileId);

    UserIdentity findOneByProviderAndProviderUserId(Provider provider, String providerUserId);
}
