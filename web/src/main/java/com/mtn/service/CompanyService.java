package com.mtn.service;

import com.mtn.model.domain.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface CompanyService extends EntityService<Company> {
	Set<Company> findAllChildCompaniesRecursive(Company company);

	Page<Company> findAllByCompanyNameLike(String name, Pageable page);

	Company findOneByCompanyName(String name);

	Company updateOneParentCompany(Integer childCompanyId, Integer parentCompanyId);
}
