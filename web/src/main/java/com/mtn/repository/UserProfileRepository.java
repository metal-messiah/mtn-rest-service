package com.mtn.repository;

import com.mtn.model.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Allen on 4/21/2017.
 */
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer>, JpaSpecificationExecutor<UserProfile> {

    @Query("FROM UserProfile up JOIN FETCH up.identities i WHERE i.providerUserId = :providerUserId")
    UserProfile findOneByProviderUserId(@Param("providerUserId") String providerUserId);

    UserProfile findOneByEmail(String email);
}
