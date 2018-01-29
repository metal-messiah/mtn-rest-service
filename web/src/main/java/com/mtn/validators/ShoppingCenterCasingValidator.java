package com.mtn.validators;

import com.mtn.model.domain.ShoppingCenterCasing;
import com.mtn.service.ShoppingCenterCasingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCenterCasingValidator extends ValidatingDataService<ShoppingCenterCasing> {

	@Autowired
	private ShoppingCenterCasingService shoppingCenterCasingService;

	@Override
	public ShoppingCenterCasingService getEntityService() {
		return shoppingCenterCasingService;
	}

	@Override
	public void validateForInsert(ShoppingCenterCasing object) {
		super.validateForInsert(object);

		// Verify Casing belongs to shopping center
		if (object.getShoppingCenter() == null) {
			throw new IllegalStateException("ShoppingCenterCasing was not mapped to ShoppingCenter before saving!");
		}
	}

}
