package com.mtn.validators;

import com.mtn.model.domain.StoreSurvey;
import com.mtn.service.StoreSurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreSurveyValidator extends ValidatingDataService<StoreSurvey> {

	@Autowired
	private StoreSurveyService storeSurveyService;

	@Override
	public StoreSurveyService getEntityService() {
		return storeSurveyService;
	}

	@Override
	public void validateForInsert(StoreSurvey object) {
		super.validateForInsert(object);

		if (object.getStore() == null) {
			throw new IllegalStateException("StoreSurvey was not mapped to Store before saving!");
		}
	}

}
