package com.mtn.validators;

import com.mtn.model.domain.UserBoundary;
import com.mtn.model.view.UserBoundaryView;;
import com.mtn.repository.UserBoundaryRepository;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBoundaryValidator extends EntityValidator<UserBoundary, UserBoundaryView> {

	@Autowired
	public UserBoundaryValidator(UserBoundaryRepository entityRepository) {
		super(entityRepository);
	}
}
