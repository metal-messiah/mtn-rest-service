package com.mtn.service;

import com.mtn.model.domain.Site;
import com.mtn.model.domain.Store;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SiteService extends EntityService<Site> {
	@Transactional
	Store addOneStoreToSite(Integer siteId, Store request, boolean overrideActiveStore);

	List<Site> findAllByShoppingCenterIdUsingSpecs(Integer shoppingCenterId);
}
