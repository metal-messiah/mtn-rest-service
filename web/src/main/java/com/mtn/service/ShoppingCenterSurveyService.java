package com.mtn.service;

import com.mtn.model.domain.ShoppingCenterAccess;
import com.mtn.model.domain.ShoppingCenterSurvey;
import com.mtn.model.domain.ShoppingCenterTenant;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ShoppingCenterSurveyService extends EntityService<ShoppingCenterSurvey> {
	ShoppingCenterAccess addOneAccessToSurvey(Integer surveyId, ShoppingCenterAccess request);

	ShoppingCenterTenant addOneTenantToSurvey(Integer surveyId, ShoppingCenterTenant request);

	List<ShoppingCenterSurvey> findAllByShoppingCenterId(Integer shoppingCenterId);

	List<ShoppingCenterSurvey> findAllByProjectId(Integer id);

	List<ShoppingCenterSurvey> findAllByShoppingCenterIdUsingSpecs(Integer shoppingCenterId);
}
