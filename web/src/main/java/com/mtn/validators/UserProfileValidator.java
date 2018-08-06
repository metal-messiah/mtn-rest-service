package com.mtn.validators;

import com.mtn.model.domain.AuditingEntity;
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
	public AuditingEntity getPotentialDuplicate(UserProfile object) {
		return getEntityService().findOneByEmail(object.getEmail().toLowerCase());
	}
}
