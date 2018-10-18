package com.mtn.service;

import com.mtn.constant.StoreType;
import com.mtn.model.domain.*;
import com.mtn.model.utils.SiteUtil;
import com.mtn.model.view.StoreView;
import com.mtn.repository.StoreRepository;
import com.mtn.repository.specification.StoreSpecifications;
import com.mtn.validators.StoreValidator;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.data.jpa.domain.Specifications.where;

@Service
public class StoreService extends EntityService<Store, StoreView> {

	@Autowired
	public StoreService(SecurityService securityService,
						StoreRepository repository,
						StoreValidator validator) {
		super(securityService, repository, validator, Store::new);
	}

	public List<Store> findAllOfTypesInBounds(Float north, Float south, Float east, Float west, List<StoreType> storeTypes) {
		UserProfile currentUser = this.securityService.getCurrentUser();
		Geometry geometry = (currentUser.getRestrictionBoundary() != null) ? currentUser.getRestrictionBoundary().getBoundary() : null;
		return ((StoreRepository) this.repository).findAllInGeometry(geometry, north, south, east, west, storeTypes);
	}

	public List<Store> findAllInGeoJson(String geoJson, boolean active, boolean future, boolean historical) {
		List<StoreType> storeTypes = this.getStoreTypes(active, future, historical);
		UserProfile currentUser = this.securityService.getCurrentUser();
		Geometry geometry = (currentUser.getRestrictionBoundary() != null) ? currentUser.getRestrictionBoundary().getBoundary() : null;
		return ((StoreRepository) this.repository).findAllInGeoJson(geoJson, geometry, storeTypes);
	}

	public List<Store> findAllInRadius(Float latitude, Float longitude, Float radiusMeters,
									   boolean active, boolean future, boolean historical) {
		List<StoreType> storeTypes = this.getStoreTypes(active, future, historical);
		UserProfile currentUser = this.securityService.getCurrentUser();
		Geometry geometry = (currentUser.getRestrictionBoundary() != null) ? currentUser.getRestrictionBoundary().getBoundary() : null;
		return ((StoreRepository) this.repository).findAllInRadius(latitude, longitude, radiusMeters, geometry, storeTypes);
	}

	private List<StoreType> getStoreTypes(boolean active, boolean future, boolean historical) {
		List<StoreType> storeTypes = new ArrayList<>();
		if (active) {
			storeTypes.add(StoreType.ACTIVE);
		}
		if (future) {
			storeTypes.add(StoreType.FUTURE);
		}
		if (historical) {
			storeTypes.add(StoreType.HISTORICAL);
		}
		return storeTypes;
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

	public List<Store> findAllByIdsUsingSpecs(List<Integer> storeIds) {
		Specifications<Store> spec = where(StoreSpecifications.isNotDeleted());
		spec = spec.and(StoreSpecifications.idIn(storeIds));
		return this.repository.findAll(spec);
	}

	@Transactional
	public Store createNewStoreForSite(Site site) {
		StoreView request = new StoreView();
		request.setStoreType(StoreType.ACTIVE);
		return this.createStoreForSiteFromRequest(request, site, true);
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
