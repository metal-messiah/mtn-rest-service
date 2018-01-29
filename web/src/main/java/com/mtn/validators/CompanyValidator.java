package com.mtn.validators;

import com.mtn.model.domain.Company;
import com.mtn.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyValidator extends ValidatingDataService<Company> {

	@Autowired
	private CompanyService companyService;

	@Override
	public CompanyService getEntityService() {
		return companyService;
	}

	@Override
	public void validateUnique(Company object) {
		Company existing = getEntityService().findOneByName(object.getName());
		if (existing != null && object.getId().equals(existing.getId())) {
			throw new IllegalArgumentException("Company with this name already exists");
		}
	}

}
