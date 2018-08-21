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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.springframework.data.jpa.domain.Specifications.where;

@Service
public class StoreService extends EntityServiceImpl<Store, StoreView> {

	@Autowired
	public StoreService(EntityServiceDependencies services,
						StoreRepository repository,
						StoreValidator validator) {
		super(services, repository, validator);
	}

	public Page<Store> findAllOfTypesInBounds(Float north, Float south, Float east, Float west, List<StoreType> storeTypes, Pageable page) {
		Integer assigneeId = securityService.getCurrentUser().getId();
		Specifications<Store> spec = Specifications.where(StoreSpecifications.withinBoundingBoxOrAssignedTo(north, south, east, west, assigneeId));

		if (storeTypes != null && storeTypes.size() > 0) {
			spec = spec.and(StoreSpecifications.ofTypes(storeTypes));
		}
		spec = spec.and(StoreSpecifications.isNotDeleted());

		return this.repository.findAll(spec, page);
	}

	public List<Store> findAllInGeoJson(String geoJson) {
		return ((StoreRepository) this.repository).findAllInGeoJson(geoJson);
	}

	public Page<Store> findAllAssignedTo(Integer assigneeId, List<StoreType> storeTypes, Pageable page) {
		Specifications<Store> spec = Specifications.where(StoreSpecifications.assignedTo(assigneeId));

		if (storeTypes != null && storeTypes.size() > 0) {
			spec = spec.and(StoreSpecifications.ofTypes(storeTypes));
		}
		spec = spec.and(StoreSpecifications.isNotDeleted());

		return this.repository.findAll(spec, page);
	}

	public List<Store> findAllByProjectId(Integer projectId) {
		return this.repository.findAll(Specifications.where(StoreSpecifications.projectIdEquals(projectId))
				.and(StoreSpecifications.isNotDeleted()));
	}

	public List<Store> findAllBySiteIdUsingSpecs(Integer siteId) {
		return this.repository.findAll(
				where(StoreSpecifications.siteIdEquals(siteId))
						.and(StoreSpecifications.isNotDeleted())
		);
	}

	@Transactional
	public Store createNewStoreForSite(Site site) {
		Store store = this.createNewEntity();
		store.setSite(site);
		store.setStoreType(StoreType.ACTIVE);
		return store;
	}

	@Transactional
	public Store createStoreForSiteFromRequest(StoreView request, Site site, boolean overrideActiveStore) {
		this.validator.validateForInsert(request);

		UserProfile currentUser = this.securityService.getCurrentUser();

		Store store = this.createNewEntityFromRequest(request);
		store.setCreatedBy(currentUser);
		store.setUpdatedBy(currentUser);

		// If the new store is ACTIVE, we have some special handling to do
		if (store.getStoreType() == StoreType.ACTIVE) {
			// Then, check for another existing ACTIVE store
			SiteUtil.getActiveStore(site)
					.ifPresent(existingActiveStore -> {
						// If site already has ACTIVE store
						if (!overrideActiveStore) {
							// Throw an error (only one ACTIVE per site)
							throw new IllegalArgumentException(String.format("A Site may only have one Active Store at a time. Store ID %d is currently set as this Site's Active Store.", existingActiveStore.getId()));
						} else {
							// Change old ACTIVE store to HISTORICAL
							existingActiveStore.setStoreType(StoreType.HISTORICAL);
							existingActiveStore.setUpdatedBy(securityService.getCurrentUser());
						}
					});
		}

		store.setSite(site);

		return store;
	}

	@Transactional
	public Store updateOneBanner(Integer storeId, Banner banner) {
		Store store = findOne(storeId);
		store.setBanner(banner);
		return store;
	}

	@Override
	protected Store createNewEntity() {
		return new Store();
	}

	@Override
	protected void setEntityAttributesFromRequest(Store store, StoreView request) {
		//If the store is changing type to active, we have some special handling to do
		if (request.getStoreType() == StoreType.ACTIVE && request.getStoreType() != store.getStoreType()) {
			//Find any existing ACTIVE store for the site
			Site site = store.getSite();
			SiteUtil.getActiveStore(site).ifPresent(existingActiveStore -> {
				existingActiveStore.setStoreType(StoreType.HISTORICAL);
				existingActiveStore.setUpdatedBy(securityService.getCurrentUser());
			});
		}

		store.setStoreName(request.getStoreName());
		store.setStoreNumber(request.getStoreNumber());
		store.setStoreType(request.getStoreType());
		store.setDateOpened(request.getDateOpened());
		store.setDateClosed(request.getDateClosed());
		store.setFloating((request.getFloating()));
	}

	@Override
	public void handleAssociationsOnDeletion(Store existing) {
		// Site is required
		existing.setBanner(null);
		// No need to disassociate casings, models, surveys, volumes, statuses
	}
}
