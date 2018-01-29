package com.mtn.validators;

import com.mtn.model.domain.ShoppingCenter;
import com.mtn.service.ShoppingCenterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCenterValidator extends ValidatingDataService<ShoppingCenter> {

	@Autowired
	private ShoppingCenterService shoppingCenterService;

	@Override
	public ShoppingCenterService getEntityService() {
		return shoppingCenterService;
	}


	@Override
	public void validateBusinessRules(ShoppingCenter object) {
		if (StringUtils.isBlank(object.getName())) {
			throw new IllegalArgumentException("ShoppingCenter name must be provided");
		}
	}

}
