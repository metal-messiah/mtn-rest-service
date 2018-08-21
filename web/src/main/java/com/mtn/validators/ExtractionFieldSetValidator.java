package com.mtn.validators;

import com.mtn.model.domain.ExtractionFieldSet;
import com.mtn.model.view.ExtractionFieldSetView;
import com.mtn.repository.ExtractionFieldSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExtractionFieldSetValidator extends EntityValidator<ExtractionFieldSet, ExtractionFieldSetView> {

	@Autowired
	public ExtractionFieldSetValidator(ExtractionFieldSetRepository repository) {
		super(repository);
	}
}
