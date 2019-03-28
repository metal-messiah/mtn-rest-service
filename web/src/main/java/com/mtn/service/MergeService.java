package com.mtn.service;

import com.mtn.model.domain.*;
import com.mtn.model.utils.StoreUtil;
import com.mtn.model.view.SiteMergeRequest;
import com.mtn.model.view.StoreMergeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Iterator;

@Service
public class MergeService {

	private final SecurityService securityService;

	private final SiteService siteService;
	private final ShoppingCenterService shoppingCenterService;
	private final StoreService storeService;

	@Autowired
	public MergeService(SecurityService securityService,
						SiteService siteService,
						ShoppingCenterService shoppingCenterService,
						StoreService storeService) {
		this.securityService = securityService;
		this.siteService = siteService;
		this.shoppingCenterService = shoppingCenterService;
		this.storeService = storeService;
	}

	@Transactional
	public Site mergeSites(SiteMergeRequest siteMergeRequest) {
		// Update the Shopping Center and Site Attributes
		ShoppingCenter updatedShoppingCenter = this.shoppingCenterService.updateOne(siteMergeRequest.getMergedSite().getShoppingCenter());
		Site updatedSite = this.siteService.updateOne(siteMergeRequest.getMergedSite());

		// For each site that isn't the updated one
		siteMergeRequest.getSiteIds().stream()
				.filter(siteId -> !siteId.equals(updatedSite.getId()))
				.forEach(siteId -> {
					Site site = this.siteService.findOne(siteId);

					// Migrate Shopping center surveys and casings
					ShoppingCenter sc = site.getShoppingCenter();
					Iterator<ShoppingCenterCasing> scCasingIterator = sc.getCasings().iterator();
					while(scCasingIterator.hasNext()) {
						ShoppingCenterCasing scCasing = scCasingIterator.next();
						scCasingIterator.remove();
						updatedShoppingCenter.addCasing(scCasing);
					}
					Iterator<ShoppingCenterSurvey> scSurveyIterator = sc.getSurveys().iterator();
					while(scSurveyIterator.hasNext()) {
						ShoppingCenterSurvey scSurvey = scSurveyIterator.next();
						scSurveyIterator.remove();
						updatedShoppingCenter.addSurvey(scSurvey);
					}
					// TODO Migrate Shopping Center Sources as well
					sc.setDeletedDate(LocalDateTime.now());
					sc.setDeletedBy(this.securityService.getCurrentUser());
					this.shoppingCenterService.updateOne(sc);

					// Migrate Stores
					Iterator<Store> storeIterator = site.getStores().iterator();
					while(storeIterator.hasNext()) {
						Store store = storeIterator.next();
						storeIterator.remove();
						updatedSite.addStore(store);
					}

					site.setDeletedDate(LocalDateTime.now());
					site.setDeletedBy(this.securityService.getCurrentUser());
					this.siteService.updateOne(site);
				});

		return updatedSite;
	}

	@Transactional
	public Store mergeStores(StoreMergeRequest storeMergeRequest) {
		Store updatedStore = this.storeService.updateOne(storeMergeRequest.getMergedStore());

		storeMergeRequest.getStoreIds().stream()
				.filter(storeId -> !storeId.equals(updatedStore.getId()))
				.forEach(storeId -> {
					Store store = this.storeService.findOne(storeId);

					StoreUtil.transferChildren(store.getCasings(), updatedStore.getCasings(), updatedStore);
					StoreUtil.transferChildren(store.getModels(), updatedStore.getModels(), updatedStore);
					StoreUtil.transferChildren(store.getSources(), updatedStore.getSources(), updatedStore);
					StoreUtil.transferChildren(store.getStatuses(), updatedStore.getStatuses(), updatedStore);
					StoreUtil.transferChildren(store.getSurveys(), updatedStore.getSurveys(), updatedStore);
					StoreUtil.transferChildren(store.getVolumes(), updatedStore.getVolumes(), updatedStore);

					store.setDeletedBy(this.securityService.getCurrentUser());
					store.setDeletedDate(LocalDateTime.now());
					this.storeService.updateOne(store);
				});

		Iterator<StoreStatus> statusIterator = updatedStore.getStatuses().stream().iterator();
		while (statusIterator.hasNext()) {
			StoreStatus curr = statusIterator.next();
			statusIterator.forEachRemaining(rem -> {
				// If the status isn't already deleted
				if (rem.getDeletedDate() == null) {
					if (curr.getStatus().equals(rem.getStatus()) && curr.getStatusStartDate().toLocalDate().equals(rem.getStatusStartDate().toLocalDate())) {
						rem.setDeletedBy(this.securityService.getCurrentUser());
						rem.setDeletedDate(LocalDateTime.now());
					}
				}
			});
		}

		return updatedStore;
	}

}
