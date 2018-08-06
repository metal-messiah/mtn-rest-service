package com.mtn.service;

import com.mtn.model.domain.ShoppingCenter;
import com.mtn.model.domain.Site;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShoppingCenterService extends EntityService<ShoppingCenter> {
	Site addOneSiteToShoppingCenter(Integer shoppingCenterId, Site request);

	Page<ShoppingCenter> findAllByNameUsingSpecs(String name, Pageable page);

	Page<ShoppingCenter> findAllByOwnerUsingSpecs(String owner, Pageable page);

	Page<ShoppingCenter> findAllByNameOrOwnerUsingSpecs(String q, Pageable page);
}
