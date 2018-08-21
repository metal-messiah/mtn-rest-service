package com.mtn.validators;

import com.mtn.model.domain.StoreSurvey;
import com.mtn.model.view.StoreSurveyView;
import com.mtn.repository.StoreSurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreSurveyValidator extends EntityValidator<StoreSurvey, StoreSurveyView> {

	@Autowired
	public StoreSurveyValidator(StoreSurveyRepository repository) {
		super(repository);
	}

}
