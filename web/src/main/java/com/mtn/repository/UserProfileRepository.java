package com.mtn.repository;

import com.mtn.model.domain.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Allen on 4/21/2017.
 */
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {

    @Query("FROM UserProfile up WHERE LOWER(up.email) LIKE LOWER(:q)")
    Page<UserProfile> findAllByQueryString(@Param("q") String q, Pageable page);

    @Query("FROM UserProfile up JOIN FETCH up.identities i WHERE i.providerUserId = :providerUserId")
    UserProfile findOneByProviderUserId(@Param("providerUserId") String providerUserId);
}