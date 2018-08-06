package com.mtn.service;

import com.mtn.model.domain.ExtractionFieldSet;
import com.mtn.validators.ExtractionFieldSetValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Tyler on 2/14/2018.
 */
@Service
public class ExtractionFieldSetServiceImpl extends EntityServiceImpl<ExtractionFieldSet> implements ExtractionFieldSetService {

	@Autowired
	private ExtractionFieldSetValidator extractionFieldSetValidator;

	@Override
	public ExtractionFieldSet updateEntityAttributes(ExtractionFieldSet existing, ExtractionFieldSet request) {
		existing.setFieldSetName(request.getFieldSetName());

		return existing;
	}

	@Override
	public String getEntityName() {
		return "ExtractionFieldSet";
	}

	@Override
	public ExtractionFieldSetValidator getValidator() {
		return extractionFieldSetValidator;
	}
}
