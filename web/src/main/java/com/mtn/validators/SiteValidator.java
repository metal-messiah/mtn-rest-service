package com.mtn.validators;

import com.mtn.model.domain.Site;
import com.mtn.model.view.SiteView;
import com.mtn.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SiteValidator extends EntityValidator<Site, SiteView> {

	@Autowired
	public SiteValidator(SiteRepository repository) {
		super(repository);
	}

	@Override
	protected void validateUpdateInsertBusinessRules(SiteView request) {
		if (request.getLatitude() == null || request.getLongitude() == null) {
			throw new IllegalArgumentException("Site must have Latitude and Longitude");
		}
	}

}
