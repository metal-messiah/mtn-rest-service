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
//		if (object.getLocation() == null) {
//			throw new IllegalArgumentException("Site location must be provided");
//		} else
//		if (object.getShoppingCenter() == null) {
//			throw new IllegalStateException("Site ShoppingCenter should have been set by now");
//		} else
		if (object.getType() == null) {
			throw new IllegalArgumentException("Site type must be provided");
		}
	}

}
