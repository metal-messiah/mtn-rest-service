package com.mtn.service;

import com.mtn.model.domain.ShoppingCenter;
import com.mtn.model.domain.ShoppingCenterCasing;
import com.mtn.model.domain.ShoppingCenterSurvey;
import com.mtn.model.domain.Site;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShoppingCenterService extends EntityService<ShoppingCenter> {
	ShoppingCenterCasing addOneCasingToShoppingCenter(Integer shoppingCenterId, ShoppingCenterCasing request);

	Site addOneSiteToShoppingCenter(Integer shoppingCenterId, Site request);

	ShoppingCenterSurvey addOneSurveyToShoppingCenter(Integer shoppingCenterId, ShoppingCenterSurvey request);

	List<ShoppingCenter> findAllByProjectId(Integer id);

	Page<ShoppingCenter> findAllByNameUsingSpecs(String name, Pageable page);

	Page<ShoppingCenter> findAllByOwnerUsingSpecs(String owner, Pageable page);

	Page<ShoppingCenter> findAllByNameOrOwnerUsingSpecs(String q, Pageable page);
}
