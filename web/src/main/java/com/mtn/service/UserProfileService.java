package com.mtn.service;

import com.mtn.model.domain.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserProfileService extends EntityService<UserProfile> {
	List<UserProfile> findAllByGroupIdUsingSpecs(Integer groupId);

	List<UserProfile> findAllByRoleIdUsingSpecs(Integer roleId);

	UserProfile findOneByEmailUsingSpecs(String email);

	UserProfile findOneByEmail(String email);

	Page<UserProfile> query(String q, Pageable page);
}
