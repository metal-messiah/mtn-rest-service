package com.mtn.validators;

import com.mtn.exception.DeletedEntityReactivationException;
import com.mtn.model.domain.UserProfile;
import com.mtn.service.UserProfileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileValidator extends ValidatingDataService<UserProfile> {

	@Autowired
	private UserProfileService userProfileService;

	@Override
	public UserProfileService getEntityService() {
		return userProfileService;
	}

	@Override
	public void validateBusinessRules(UserProfile object) {
		if (StringUtils.isBlank(object.getEmail())) {
			throw new IllegalArgumentException("UserProfile email must be provided");
		}
	}

	@Override
	public void validateUnique(UserProfile object) {
		UserProfile existing = getEntityService().findOneByEmail(object.getEmail().toLowerCase());
		if (existing != null && !existing.getVersion().equals(object.getVersion())) {
			throw new IllegalArgumentException("UserProfile with this email already exists");
		}
	}
}
