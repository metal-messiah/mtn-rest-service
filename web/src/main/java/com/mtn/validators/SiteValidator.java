package com.mtn.validators;

import com.mtn.model.domain.Site;
import com.mtn.service.SiteService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SiteValidator extends ValidatingDataService<Site> {

	@Autowired
	private SiteService siteService;

	@Override
	public SiteService getEntityService() {
		return siteService;
	}

	@Override
	public void validateBusinessRules(Site object) {
		if (object.getLatitude() == null || object.getLongitude() == null) {
			throw new IllegalArgumentException("Site must have Latitude and Longitude");
		}
	}

}
