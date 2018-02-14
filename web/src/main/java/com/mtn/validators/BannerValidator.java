package com.mtn.validators;

import com.mtn.model.domain.Banner;
import com.mtn.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BannerValidator extends ValidatingDataService<Banner> {

	@Autowired
	private BannerService bannerService;

	@Override
	public BannerService getEntityService() {
		return bannerService;
	}

}
