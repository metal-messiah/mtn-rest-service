package com.mtn.service;

import com.mtn.model.domain.Company;
import com.mtn.model.view.BannerView;
import com.mtn.model.view.CompanyView;
import com.mtn.repository.CompanyRepository;
import com.mtn.repository.specification.CompanySpecifications;
import com.mtn.validators.CompanyValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class CompanyService extends EntityService<Company, CompanyView> {

	private final BannerService bannerService;

	@Autowired
	public CompanyService(SecurityService securityService,
						  CompanyRepository repository,
						  CompanyValidator validator,
						  BannerService bannerService) {
		super(securityService, repository, validator, Company::new);
		this.bannerService = bannerService;
	}

	public Set<Company> findAllChildCompaniesRecursive(Company company) {
		Set<Company> companies = new HashSet<>();
		companies.add(company);

		for (Company childCompany : company.getChildCompanies()) {
			companies.addAll(findAllChildCompaniesRecursive(childCompany));
		}

		return companies;
	}

	public Page<Company> findAllByCompanyNameLike(String name, Pageable page) {
		return this.repository.findAll(Specifications.where(
				CompanySpecifications.companyNameLike(name))
				.and(CompanySpecifications.isNotDeleted()), page);
	}

	public Company findOneByCompanyName(String name) {
		return this.repository.findOne(Specifications.where(CompanySpecifications.companyNameEquals(name)));
	}

	@Transactional
	public Company setParentCompany(Integer childCompanyId, Integer parentCompanyId) {
		Company child = findOne(childCompanyId);
		Company parent = findOne(parentCompanyId);
		child.setParentCompany(parent);
		return child;
	}

	@Override
	public void handleAssociationsOnDeletion(Company company) {
		company.getChildCompanies().forEach(child -> {
			child.setParentCompany(null);
			this.updateOne(new CompanyView(child));
		});
		company.getBanners().forEach(banner -> {
			banner.setCompany(null);
			this.bannerService.updateOne(new BannerView(banner));
		});
	}

	@Override
	protected void setEntityAttributesFromRequest(Company existing, CompanyView request) {
		existing.setCompanyName(request.getCompanyName());
		existing.setWebsiteUrl(request.getWebsiteUrl());

		if (request.getParentCompany() != null) {
			Company parent = this.findOneUsingSpecs(request.getParentCompany().getId());
			existing.setParentCompany(parent);
		} else {
			existing.setParentCompany(null);
		}
	}

}
