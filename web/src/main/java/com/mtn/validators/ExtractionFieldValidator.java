package com.mtn.validators;

import com.mtn.model.domain.Banner;
import com.mtn.model.domain.ExtractionField;
import com.mtn.service.BannerService;
import com.mtn.service.ExtractionFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExtractionFieldValidator extends ValidatingDataService<ExtractionField> {

	@Autowired
	private ExtractionFieldService extractionFieldService;

	@Override
	public ExtractionFieldService getEntityService() {
		return extractionFieldService;
	}

}
