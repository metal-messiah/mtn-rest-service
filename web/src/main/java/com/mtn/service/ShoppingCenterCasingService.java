package com.mtn.service;

import com.mtn.model.domain.ShoppingCenterCasing;

import java.util.List;

public interface ShoppingCenterCasingService extends EntityService<ShoppingCenterCasing> {

	List<ShoppingCenterCasing> findAllByShoppingCenterId(Integer shoppingCenterId);

	List<ShoppingCenterCasing> findAllByShoppingCenterIdUsingSpecs(Integer shoppingCenterId);
}
