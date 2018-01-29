package com.mtn.validators;

import com.mtn.model.domain.ShoppingCenterTenant;
import com.mtn.service.ShoppingCenterTenantService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCenterTenantValidator extends ValidatingDataService<ShoppingCenterTenant> {

	@Autowired
	private ShoppingCenterTenantService shoppingCenterTenantService;

	@Override
	public ShoppingCenterTenantService getEntityService() {
		return shoppingCenterTenantService;
	}

	@Override
	public void validateForInsert(ShoppingCenterTenant object) {
		super.validateForInsert(object);

		// Check that Tenant belongs to a Survey
		if (object.getSurvey() == null) {
			throw new IllegalStateException("ShoppingCenterTenant was not mapped to ShoppingCenterSurvey before saving!");
		}
	}

	@Override
	public void validateBusinessRules(ShoppingCenterTenant object) {
		if (StringUtils.isBlank(object.getName())) {
			throw new IllegalArgumentException("ShoppingCenterTenant name must not be null");
		}
	}

}
