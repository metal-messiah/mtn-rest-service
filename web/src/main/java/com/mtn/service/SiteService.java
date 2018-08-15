package com.mtn.service;

import com.mtn.constant.StoreType;
import com.mtn.model.domain.Site;
import com.mtn.model.domain.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SiteService extends EntityService<Site> {

	Store addOneStoreToSite(Integer siteId, Store request, boolean overrideActiveStore);

	List<Site> findAllByShoppingCenterIdUsingSpecs(Integer shoppingCenterId);

	List<Site> findAllInGeoJson(String geoJson);

	List<Site> findAllInBoundsUsingSpecs(Float north, Float south, Float east, Float west);

	List<Site> findAllInProjectBoundary(Integer projectId);

	Page<Site> findAllInBoundsWithoutStoresUsingSpecs(Float north, Float south, Float east, Float west, boolean noStores, Pageable page);

	List<Site> assignSitesToUser(Integer[] siteIds, Integer userId);

	Page<Site> findAllDuplicatesUsingSpecs(Pageable page);

	static Store findActiveStore(Site site) {
		return site.getStores().stream()
				.filter(store -> store.getStoreType() == StoreType.ACTIVE).findFirst().orElse(null);
	}

}
