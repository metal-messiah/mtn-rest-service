package com.mtn.validators;

import com.mtn.model.domain.AuditingEntity;
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
	public AuditingEntity getPotentialDuplicate(Company object) {
		return getEntityService().findOneByCompanyName(object.getCompanyName());
	}

}
