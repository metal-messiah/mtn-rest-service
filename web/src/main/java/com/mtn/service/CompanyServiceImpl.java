package com.mtn.service;

import com.mtn.model.domain.Company;
import com.mtn.repository.CompanyRepository;
import com.mtn.validators.CompanyValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static com.mtn.repository.specification.CompanySpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 6/10/2017.
 */
@Service
public class CompanyServiceImpl extends EntityServiceImpl<Company> implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private CompanyValidator companyValidator;

	@Override
	public void handleAssociationsOnCreation(Company request) {
		// If company has parent get DB version by id and associated them
		if (request.getParentCompany() != null && request.getParentCompany().getId() != null) {
			Company parentCompany = findOne(request.getParentCompany().getId());
			if (parentCompany == null) {
				throw new IllegalArgumentException("No Company found with this parent company id");
			}

			request.setParentCompany(parentCompany);
			parentCompany.getChildCompanies().add(request);
		}
	}

	@Override
	public void handleAssociationsOnDeletion(Company request) {
		// TODO - figure out what to do with child companies
		// If company has children disassociate them... what is the rule - cascade delete?
	}

	@Override
	public Set<Company> findAllChildCompaniesRecursive(Company company) {
		Set<Company> companies = new HashSet<>();
		companies.add(company);

		for (Company childCompany : company.getChildCompanies()) {
			companies.addAll(findAllChildCompaniesRecursive(childCompany));
		}

		return companies;
	}

	@Override
	public Page<Company> findAllByCompanyNameLike(String name, Pageable page) {
		name = String.format("%%%s%%", name.toLowerCase());
		return getEntityRepository().findAllByCompanyNameLikeAndDeletedDateIsNull(name, page);
	}

	@Override
	public Company findOneByCompanyName(String name) {
		return getEntityRepository().findOneByCompanyName(name);
	}

	@Override
	public Page<Company> findAllUsingSpecs(Pageable page) {
		return getEntityRepository().findAll(
				where(isNotDeleted()),
				page
		);
	}

	@Override
	public Company findOneUsingSpecs(Integer id) {
		return getEntityRepository().findOne(
				where(idEquals(id))
						.and(isNotDeleted())
		);
	}

	@Override
	public Company updateEntityAttributes(Company existing, Company request) {
		existing.setCompanyName(request.getCompanyName());
		existing.setWebsiteUrl(request.getWebsiteUrl());

		return existing;
	}

	@Override
	@Transactional
	public Company updateOneParentCompany(Integer childCompanyId, Integer parentCompanyId) {
		Company child = findOne(childCompanyId);
		if (child == null) {
			throw new IllegalArgumentException("No Company found for this child id");
		}

		Company parent = findOne(parentCompanyId);
		if (parent == null) {
			throw new IllegalArgumentException("No Company found for this parent id");
		}

		child.setParentCompany(parent);
		parent.getChildCompanies().add(child);

		return child;
	}

	@Override
	public String getEntityName() {
		return "Company";
	}

	@Override
	public CompanyRepository getEntityRepository() {
		return companyRepository;
	}

	@Override
	public CompanyValidator getValidator() {
		return companyValidator;
	}
}
