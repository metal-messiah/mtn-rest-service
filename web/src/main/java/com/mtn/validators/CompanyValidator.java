package com.mtn.validators;

import com.mtn.model.domain.Company;
import com.mtn.model.view.CompanyView;
import com.mtn.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyValidator extends EntityValidator<Company, CompanyView> {

	@Autowired
	public CompanyValidator(CompanyRepository repository) {
		super(repository);
	}

}
