package com.mtn.validators;

import com.mtn.model.domain.ShoppingCenterSurvey;
import com.mtn.service.ShoppingCenterSurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCenterSurveyValidator extends ValidatingDataService<ShoppingCenterSurvey> {

	@Autowired
	private ShoppingCenterSurveyService shoppingCenterSurveyService;
	@Autowired
	private ShoppingCenterAccessValidator shoppingCenterAccessValidator;
	@Autowired
	private ShoppingCenterTenantValidator shoppingCenterTenantValidator;

	@Override
	public ShoppingCenterSurveyService getEntityService() {
		return shoppingCenterSurveyService;
	}

	@Override
	public void validateForInsert(ShoppingCenterSurvey object) {
		super.validateForInsert(object);

		if (object.getShoppingCenter() == null) {
			throw new IllegalStateException("ShoppingCenterSurvey was not mapped to ShoppingCenter before saving!");
		}

		object.getAccesses().forEach(shoppingCenterAccessValidator::validateForInsert);
		object.getTenants().forEach(shoppingCenterTenantValidator::validateForInsert);
	}

}
