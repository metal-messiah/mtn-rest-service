package com.mtn.service;

import com.mtn.model.domain.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface CompanyService extends EntityService<Company> {
	Set<Integer> findAllChildCompanyIdsRecursive(Company company);

	Page<Company> findAllWhereNameLike(String name, Pageable page);

	Company findOneByName(String name);

	Company updateOneParentCompany(Integer childCompanyId, Integer parentCompanyId);
}
