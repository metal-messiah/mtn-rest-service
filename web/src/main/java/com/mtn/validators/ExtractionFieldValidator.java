package com.mtn.validators;

import com.mtn.model.domain.ExtractionField;
import com.mtn.model.view.ExtractionFieldView;
import com.mtn.repository.ExtractionFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExtractionFieldValidator extends EntityValidator<ExtractionField, ExtractionFieldView> {

	@Autowired
	public ExtractionFieldValidator(ExtractionFieldRepository repository) {
		super(repository);
	}

}
