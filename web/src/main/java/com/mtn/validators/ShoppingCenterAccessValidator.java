package com.mtn.validators;

import com.mtn.model.domain.ShoppingCenterAccess;
import com.mtn.service.ShoppingCenterAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCenterAccessValidator extends ValidatingDataService<ShoppingCenterAccess> {

	@Autowired
	private ShoppingCenterAccessService shoppingCenterAccessService;

	@Override
	public ShoppingCenterAccessService getEntityService() {
		return shoppingCenterAccessService;
	}

	@Override
	public void validateForInsert(ShoppingCenterAccess object) {
		super.validateForInsert(object);

		// Check that Access belongs to a Survey
		if (object.getSurvey() == null) {
			throw new IllegalStateException("ShoppingCenterAccess was not mapped to ShoppingCenterSurvey before saving!");
		}
	}

}
