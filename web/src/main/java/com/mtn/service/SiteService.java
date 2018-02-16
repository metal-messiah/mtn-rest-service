package com.mtn.service;

import com.mtn.model.domain.Site;
import com.mtn.model.domain.Store;

import java.util.List;

public interface SiteService extends EntityService<Site> {

	Store addOneStoreToSite(Integer siteId, Store request, boolean overrideActiveStore);

	List<Site> findAllByShoppingCenterIdUsingSpecs(Integer shoppingCenterId);
}
