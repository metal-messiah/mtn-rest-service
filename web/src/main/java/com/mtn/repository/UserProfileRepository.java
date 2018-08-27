package com.mtn.repository;

import com.mtn.model.domain.UserProfile;

import java.util.List;

public interface UserProfileRepository extends EntityRepository<UserProfile> {

	List<UserProfile> findAllByEmail(String email);

}
