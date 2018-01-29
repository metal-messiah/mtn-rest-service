package com.mtn.service;

import com.mtn.model.domain.ShoppingCenterTenant;

import java.util.List;

public interface ShoppingCenterTenantService extends EntityService<ShoppingCenterTenant> {
	List<ShoppingCenterTenant> findAllBySurveyId(Integer id);

	List<ShoppingCenterTenant> findAllBySurveyIdUsingSpecs(Integer id);
}
