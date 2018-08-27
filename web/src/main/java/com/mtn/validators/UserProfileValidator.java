package com.mtn.validators;

import com.mtn.model.domain.UserProfile;
import com.mtn.model.view.UserProfileView;
import com.mtn.repository.UserProfileRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileValidator extends EntityValidator<UserProfile, UserProfileView> {

	private final UserProfileRepository userProfileRepository;

	@Autowired
	public UserProfileValidator(UserProfileRepository userProfileRepository) {
		super(userProfileRepository);
		this.userProfileRepository = userProfileRepository;
	}

	@Override
	protected void validateUpdateInsertBusinessRules(UserProfileView request) {
		if (request.getId() != null && request.getId() == 1) {
			throw new IllegalArgumentException("This user may not be updated");
		}
		if (StringUtils.isBlank(request.getEmail())) {
			throw new IllegalArgumentException("UserProfile email must be provided");
		}

		// If another UserProfile (different ID) with the same email already exists
		List<UserProfile> usersWithEmail = userProfileRepository.findAllByEmail(request.getEmail().toLowerCase());
		if (usersWithEmail.stream()
				.anyMatch(userProfile -> userProfile.getEmail().toLowerCase().equals(request.getEmail().toLowerCase()) &&
						!userProfile.getId().equals(request.getId()))) {
			throw new IllegalArgumentException(String.format("User Profile with email '%s' already exists!", request.getEmail()));
		}
	}

	@Override
	protected void validateDeletionBusinessRules(Integer id) {
		if (id.equals(1)) {
			throw new IllegalArgumentException("You may not delete this User Profile!");
		}
	}
}
