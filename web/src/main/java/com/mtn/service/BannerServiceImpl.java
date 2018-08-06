package com.mtn.service;

import com.mtn.model.domain.Banner;
import com.mtn.model.domain.Company;
import com.mtn.repository.BannerRepository;
import com.mtn.repository.specification.BannerSpecifications;
import com.mtn.validators.BannerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import static com.mtn.repository.specification.BannerSpecifications.bannerNameLike;
import static com.mtn.repository.specification.BannerSpecifications.isNotDeleted;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Tyler on 2/14/2018.
 */
@Service
public class BannerServiceImpl extends EntityServiceImpl<Banner> implements BannerService {

	@Autowired
	private BannerRepository bannerRepository;
	@Autowired
	private BannerValidator bannerValidator;
	@Autowired
	private CompanyService companyService;

	@Override
	public void handleAssociationsOnCreation(Banner request) {
		if (request.getCompany() != null && request.getCompany().getId() != null) {
			Company company = companyService.findOne(request.getCompany().getId());
			if (company == null) {
				throw new IllegalArgumentException("No Company found with this company id");
			}

			request.setCompany(company);
			company.getBanners().add(request);
		}
	}

	@Override
	public Page<Banner> findAllByQueryUsingSpecs(Pageable page, String query) {
		Specifications<Banner> spec = where(isNotDeleted());

		if (query != null) {
			spec = spec.and(bannerNameLike("%" + query + "%"));
		}
		return bannerRepository.findAll(spec, page);
	}

	@Override
	public Banner findOneByBannerName(String bannerName) {
		return bannerRepository.findOne(Specifications.where(BannerSpecifications.bannerNameEquals(bannerName)));
	}

	@Override
	public Banner updateEntityAttributes(Banner existing, Banner request) {
		existing.setBannerName(request.getBannerName());
		existing.setHistorical(request.getHistorical());
		existing.setDefaultStoreFit(request.getDefaultStoreFit());
		existing.setDefaultSalesArea(request.getDefaultSalesArea());
		existing.setLogoFileName(request.getLogoFileName());

		return existing;
	}

	@Override
	public String getEntityName() {
		return "Banner";
	}

	@Override
	public BannerValidator getValidator() {
		return bannerValidator;
	}
}
