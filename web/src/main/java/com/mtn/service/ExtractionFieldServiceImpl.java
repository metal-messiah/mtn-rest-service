package com.mtn.service;

import com.mtn.model.domain.ExtractionField;
import com.mtn.validators.ExtractionFieldValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Tyler on 2/14/2018.
 */
@Service
public class ExtractionFieldServiceImpl extends EntityServiceImpl<ExtractionField> implements ExtractionFieldService {

	@Autowired
	private ExtractionFieldValidator extractionFieldValidator;

	@Override
	public ExtractionField updateEntityAttributes(ExtractionField existing, ExtractionField request) {
		existing.setExtractionDataType(request.getExtractionDataType());
		existing.setFieldMapping(request.getFieldMapping());
		existing.setHeader(request.getHeader());

		return existing;
	}

	@Override
	public String getEntityName() {
		return "ExtractionField";
	}

	@Override
	public ExtractionFieldValidator getValidator() {
		return extractionFieldValidator;
	}
}
