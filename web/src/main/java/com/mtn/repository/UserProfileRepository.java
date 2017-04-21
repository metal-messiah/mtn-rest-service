package com.mtn.repository;

import com.mtn.model.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Allen on 4/21/2017.
 */
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {

}
