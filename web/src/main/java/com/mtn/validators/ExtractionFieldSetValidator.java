package com.mtn.validators;

import com.mtn.model.domain.ExtractionFieldSet;
import com.mtn.service.ExtractionFieldSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExtractionFieldSetValidator extends ValidatingDataService<ExtractionFieldSet> {

	@Autowired
	private ExtractionFieldSetService extractionFieldSetService;

	@Override
	public ExtractionFieldSetService getEntityService() {
		return extractionFieldSetService;
	}

}
