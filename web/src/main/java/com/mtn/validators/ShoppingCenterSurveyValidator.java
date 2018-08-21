package com.mtn.validators;

import com.mtn.model.domain.ShoppingCenterSurvey;
import com.mtn.model.view.ShoppingCenterSurveyView;
import com.mtn.repository.ShoppingCenterSurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCenterSurveyValidator extends EntityValidator<ShoppingCenterSurvey, ShoppingCenterSurveyView> {

	@Autowired
	public ShoppingCenterSurveyValidator(ShoppingCenterSurveyRepository repository) {
		super(repository);
	}

}
