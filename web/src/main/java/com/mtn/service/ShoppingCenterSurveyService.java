package com.mtn.service;

import com.mtn.model.domain.ShoppingCenterAccess;
import com.mtn.model.domain.ShoppingCenterSurvey;
import com.mtn.model.domain.ShoppingCenterTenant;

import java.util.List;

public interface ShoppingCenterSurveyService extends EntityService<ShoppingCenterSurvey> {
	ShoppingCenterAccess addOneAccessToSurvey(Integer surveyId, ShoppingCenterAccess request);

	List<ShoppingCenterTenant> createNewTenantsForSurvey(Integer surveyId, List<ShoppingCenterTenant> request);

	List<ShoppingCenterSurvey> findAllByShoppingCenterId(Integer shoppingCenterId);

	ShoppingCenterSurvey findLatestShoppingCenterSurveyForStore(Integer storeId);

	List<ShoppingCenterSurvey> findAllByShoppingCenterIdUsingSpecs(Integer shoppingCenterId);
}
