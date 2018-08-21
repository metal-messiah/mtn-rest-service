package com.mtn.service;

import com.mtn.model.domain.Banner;
import com.mtn.model.view.BannerView;
import com.mtn.repository.BannerRepository;
import com.mtn.repository.specification.BannerSpecifications;
import com.mtn.validators.BannerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import static org.springframework.data.jpa.domain.Specifications.where;

@Service
public class BannerService extends EntityServiceImpl<Banner, BannerView> {

	@Autowired
	public BannerService(EntityServiceDependencies services,
						 BannerRepository bannerRepository,
						 BannerValidator bannerValidator) {
		super(services, bannerRepository, bannerValidator);
	}

	public Page<Banner> findAllByQueryUsingSpecs(Pageable page, String query) {
		Specifications<Banner> spec = where(BannerSpecifications.isNotDeleted());

		if (query != null) {
			spec = spec.and(BannerSpecifications.bannerNameLike("%" + query + "%"));
		}
		return this.repository.findAll(spec, page);
	}

	@Override
	public Banner createNewEntity() {
		return new Banner();
	}

	@Override
	protected void setEntityAttributesFromRequest(Banner banner, BannerView request) {
		banner.setBannerName(request.getBannerName());
		banner.setHistorical(request.getHistorical());
		banner.setDefaultStoreFit(request.getDefaultStoreFit());
		banner.setDefaultSalesArea(request.getDefaultSalesArea());
		banner.setLogoFileName(request.getLogoFileName());
	}

	@Override
	public void handleAssociationsOnDeletion(Banner existing) {
		existing.getStores().forEach(store -> store.setBanner(null));
	}

}
