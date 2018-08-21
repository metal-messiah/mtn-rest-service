package com.mtn.validators;

import com.mtn.model.domain.Banner;
import com.mtn.model.view.BannerView;
import com.mtn.repository.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BannerValidator extends EntityValidator<Banner, BannerView> {

	@Autowired
	public BannerValidator(BannerRepository entityRepository) {
		super(entityRepository);
	}
}
