package com.mtn.service;

import com.mtn.constant.StoreType;
import com.mtn.model.domain.Banner;
import com.mtn.model.domain.Site;
import com.mtn.model.domain.Store;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.utils.SiteUtil;
import com.mtn.model.view.StoreView;
import com.mtn.repository.StoreRepository;
import com.mtn.repository.specification.StoreSpecifications;
import com.mtn.validators.StoreValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.data.jpa.domain.Specifications.where;

@Service
public class StoreService extends EntityService<Store, StoreView> {

	@Autowired
	public StoreService(SecurityService securityService, StoreRepository repository, StoreValidator validator) {
		super(securityService, repository, validator, Store::new);
	}

	public List<Store> findAllBySiteIdUsingSpecs(Integer siteId) {
		return this.repository
				.findAll(where(StoreSpecifications.siteIdEquals(siteId)).and(StoreSpecifications.isNotDeleted()));
	}

	public List<Store> findAllByIdsUsingSpecs(List<Integer> storeIds) {
		Specifications<Store> spec = where(StoreSpecifications.isNotDeleted());
		spec = spec.and(StoreSpecifications.idIn(storeIds));
		return this.repository.findAll(spec);
	}

	public Store createNewStoreForSite(Site site) {
		StoreView request = new StoreView();
		request.setStoreType(StoreType.ACTIVE);
		return this.createStoreForSiteFromRequest(request, site, true);
	}

	public Store createStoreForSiteFromRequest(StoreView request, Site site, boolean overrideActiveStore) {
		this.validator.validateForInsert(request);

		UserProfile currentUser = this.securityService.getCurrentUser();

		Store store = this.createNewEntityFromRequest(request);
		store.setCreatedBy(currentUser);
		store.setUpdatedBy(currentUser);

		// If the new store is ACTIVE, we have some special handling to do
		if (store.getStoreType() == StoreType.ACTIVE) {
			// Then, check for another existing ACTIVE store
			SiteUtil.getActiveStore(site).ifPresent(existingActiveStore -> {
				// If site already has ACTIVE store
				if (!overrideActiveStore) {
					// Throw an error (only one ACTIVE per site)
					throw new IllegalArgumentException(String.format(
							"A Site may only have one Active Store at a time. Store ID %d is currently set as this Site's Active Store.",
							existingActiveStore.getId()));
				} else {
					// Change old ACTIVE store to HISTORICAL
					existingActiveStore.setStoreType(StoreType.HISTORICAL);
					existingActiveStore.setUpdatedBy(securityService.getCurrentUser());
				}
			});
		}

		store.setSite(site);
		return this.repository.save(store);
	}

	@Transactional
	public Store updateOneBanner(Integer storeId, Banner banner) {
		Store store = findOne(storeId);
		store.setBanner(banner);
		return store;
	}

	@Override
	protected void setEntityAttributesFromRequest(Store store, StoreView request) {
		store.setStoreName(request.getStoreName());
		store.setStoreNumber(request.getStoreNumber());
		store.setStoreType(request.getStoreType());
		store.setDateOpened(request.getDateOpened());
		store.setDateClosed(request.getDateClosed());
		store.setFit(request.getFit());
		store.setFormat(request.getFormat());
		store.setAreaSales(request.getAreaSales());
		store.setAreaSalesPercentOfTotal(request.getAreaSalesPercentOfTotal());
		store.setAreaTotal(request.getAreaTotal());
		store.setAreaIsEstimate(request.getAreaIsEstimate());
		store.setStoreIsOpen24(request.getStoreIsOpen24());
		store.setNaturalFoodsAreIntegrated(request.getNaturalFoodsAreIntegrated());
		store.setFloating((request.getFloating()));
	}

	@Override
	public void handleAssociationsOnDeletion(Store existing) {
		// Site is required
		existing.setBanner(null);
		// No need to disassociate casings, models, surveys, volumes, statuses
	}

	@Transactional
	public Store validateStore(Integer storeId) {
		Store store = this.findOne(storeId);
		store.setValidatedDate(LocalDateTime.now());
		store.setValidatedBy(this.securityService.getCurrentUser());
		return store;
	}

	@Transactional
	public Store invalidateStore(Integer storeId) {
		Store store = this.findOne(storeId);
		store.setValidatedDate(null);
		store.setValidatedBy(null);
		return store;
	}
}
