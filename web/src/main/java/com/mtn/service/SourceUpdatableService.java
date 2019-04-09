package com.mtn.service;

import com.mtn.model.domain.ShoppingCenter;
import com.mtn.model.domain.Site;
import com.mtn.model.domain.Store;
import com.mtn.model.domain.StoreStatus;
import com.mtn.model.simpleView.SimpleStoreStatusView;
import com.mtn.model.view.SourceUpdatable;
import com.mtn.model.view.StoreStatusView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Optional;

@Service
public class SourceUpdatableService {

	private final ShoppingCenterService shoppingCenterService;
	private final SiteService siteService;
	private final StoreService storeService;
	private final StoreStatusService storeStatusService;

	@Autowired
	public SourceUpdatableService(ShoppingCenterService shoppingCenterService,
								  SiteService siteService,
								  StoreService storeService,
								  StoreStatusService storeStatusService) {
		this.shoppingCenterService = shoppingCenterService;
		this.siteService = siteService;
		this.storeService = storeService;
		this.storeStatusService = storeStatusService;
	}

	public SourceUpdatable getUpdatableByStoreId(Integer storeId) {
		Store store = storeService.findOne(storeId);
		return new SourceUpdatable(store);
	}

	public SourceUpdatable getUpdatableBySiteId(Integer siteId) {
		Site site = siteService.findOne(siteId);
		return new SourceUpdatable(site);
	}

	public SourceUpdatable getUpdatableByShoppingCenterId(Integer shoppingCenterId) {
		ShoppingCenter shoppingCenter = shoppingCenterService.findOne(shoppingCenterId);
		return new SourceUpdatable(shoppingCenter);
	}

	@Transactional
	public Store updateFromUpdatable(SourceUpdatable updatable) {
		Store store;
		if (updatable.getShoppingCenterId() == null) {
			ShoppingCenter sc = shoppingCenterService.createNew();
			Site site = siteService.createOne(sc, updatable.getLatitude(), updatable.getLongitude());
			store = this.storeService.createNewStoreForSite(site);
		} else if (updatable.getSiteId() == null) {
			ShoppingCenter sc = shoppingCenterService.findOne(updatable.getShoppingCenterId());
			Site site = this.createNewSiteInShoppingCenter(sc, updatable.getLatitude(), updatable.getLongitude());
			store = this.storeService.createNewStoreForSite(site);
		} else if (updatable.getStoreId() == null) {
			Site site = siteService.findOne(updatable.getSiteId());
			store = this.storeService.createNewStoreForSite(site);
		} else {
			store = storeService.findOne(updatable.getStoreId());
		}
		this.updateStoreFromUpdatable(updatable, store);
		this.updateSiteFromUpdatable(updatable, store.getSite());
		this.updateShoppingCenterFromUpdatable(updatable, store.getSite().getShoppingCenter());

		return store;
	}

	@Transactional
	protected Site createNewSiteInShoppingCenter(ShoppingCenter sc, Float latitude, Float longitude) {
		Site site = new Site();
		site.setShoppingCenter(sc);
		site.setLatitude(latitude);
		site.setLongitude(longitude);
		return siteService.createOne(sc, latitude, longitude);
	}

	private void updateShoppingCenterFromUpdatable(SourceUpdatable updatable, ShoppingCenter shoppingCenter) {
		shoppingCenter.setName(updatable.getShoppingCenterName());
		shoppingCenterService.updateOne(shoppingCenter);
	}

	private void updateSiteFromUpdatable(SourceUpdatable updatable, Site site) {
		site.setAddress1(updatable.getAddress());
		site.setQuad(updatable.getQuad());
		site.setIntersectionStreetPrimary(updatable.getIntersectionStreetPrimary());
		site.setIntersectionStreetSecondary(updatable.getIntersectionStreetSecondary());
		site.setCity(updatable.getCity());
		site.setCounty(updatable.getCounty());
		site.setState(updatable.getState());
		site.setPostalCode(updatable.getPostalCode());
		site.setLatitude(updatable.getLatitude());
		site.setLongitude(updatable.getLongitude());
		siteService.updateOne(site);
	}

	private void updateStoreFromUpdatable(SourceUpdatable updatable, Store store) {
		store.setStoreName(updatable.getStoreName());
		store.setDateOpened(updatable.getDateOpened());
		store.setAreaTotal(updatable.getAreaTotal());
		if (updatable.getStoreStatus() != null && updatable.getStoreStatusStartDate() != null) {
			Optional<StoreStatus> previousStatus = store.getStatuses().stream()
					.filter(st -> st.getStatusStartDate().isBefore(updatable.getStoreStatusStartDate()))
					.max(Comparator.comparing(StoreStatus::getStatusStartDate));
			// If no previous status is found, or the previous status is not the same, then add the new status
			if (!previousStatus.isPresent() ||!previousStatus.get().getStatus().equals(updatable.getStoreStatus()) ) {
				StoreStatusView newStoreStatusRequest = new StoreStatusView();
				newStoreStatusRequest.setStatus(updatable.getStoreStatus());
				newStoreStatusRequest.setStatusStartDate(updatable.getStoreStatusStartDate());
				storeStatusService.addOneToStore(newStoreStatusRequest, store);
			}
		}
		storeService.updateOne(store);
	}

}
