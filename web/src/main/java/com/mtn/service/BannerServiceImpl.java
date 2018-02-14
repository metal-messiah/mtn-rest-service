package com.mtn.service;

import com.mtn.model.domain.Banner;
import com.mtn.model.domain.Company;
import com.mtn.repository.BannerRepository;
import com.mtn.validators.BannerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.mtn.repository.specification.BannerSpecifications.idEquals;
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
	public void handleAssociationsOnDeletion(Banner existing) {

	}

	@Override
	public Page<Banner> findAllWhereBannerNameLike(String name, Pageable page) {
		name = String.format("%%%s%%", name.toLowerCase());
		return getEntityRepository().findAllByBannerNameLikeAndDeletedDateIsNull(name, page);
	}

	@Override
	public Banner findOneByBannerName(String bannerName) {
		return getEntityRepository().findOneByBannerName(bannerName);
	}

	@Override
	public Page<Banner> findAllUsingSpecs(Pageable page) {
		return getEntityRepository().findAll(
				where(isNotDeleted()),
				page
		);
	}

	@Override
	public Banner findOneUsingSpecs(Integer id) {
		return getEntityRepository().findOne(
				where(idEquals(id))
						.and(isNotDeleted())
		);
	}

	@Override
	public Banner getUpdatedEntity(Banner existing, Banner request) {
		existing.setBannerName(request.getBannerName());
		existing.setHistorical(request.getHistorical());
		existing.setDefaultStoreFit(request.getDefaultStoreFit());
		existing.setDefaultSalesArea(request.getDefaultSalesArea());

		return existing;
	}

	@Override
	public String getEntityName() {
		return "Banner";
	}

	@Override
	public BannerRepository getEntityRepository() {
		return bannerRepository;
	}

	@Override
	public BannerValidator getValidator() {
		return bannerValidator;
	}
}
