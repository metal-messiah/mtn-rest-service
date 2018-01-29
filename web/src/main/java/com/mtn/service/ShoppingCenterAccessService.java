package com.mtn.service;

import com.mtn.model.domain.ShoppingCenterAccess;

import java.util.List;

public interface ShoppingCenterAccessService extends EntityService<ShoppingCenterAccess> {
	List<ShoppingCenterAccess> findAllBySurveyIdUsingSpecs(Integer id);
}
