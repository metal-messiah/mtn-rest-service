package com.mtn.service;

import com.mtn.constant.StoreType;
import com.mtn.model.domain.*;
import com.mtn.model.utils.ShoppingCenterUtil;
import com.mtn.model.utils.StoreUtil;
import com.mtn.repository.StoreRepository;
import com.mtn.repository.specification.AuditingEntitySpecifications;
import com.mtn.repository.specification.StoreSpecifications;
import com.mtn.validators.StoreValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.mtn.repository.specification.StoreSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

@Service
public class StoreServiceImpl extends EntityServiceImpl<Store> implements StoreService {

	@Autowired
	private StoreRepository storeRepository;
	@Autowired
	private StoreSurveyService surveyService;
	@Autowired
	private BannerService bannerService;
	@Autowired
	private StoreCasingService casingService;
	@Autowired
	private StoreModelService modelService;
	@Autowired
	private StoreVolumeService storeVolumeService;
	@Autowired
	private StoreValidator storeValidator;
	@Autowired
	private StoreStatusService storeStatusService;
	@Autowired
	private StoreSurveyService storeSurveyService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private SiteService siteService;
	@Autowired
	private ShoppingCenterSurveyService shoppingCenterSurveyService;
	@Autowired
	private ShoppingCenterCasingService shoppingCenterCasingService;

	@Override
	public Page<Store> findAllOfTypesInBounds(Float north, Float south, Float east, Float west, List<StoreType> storeTypes, Pageable page) {
		Integer assigneeId = securityService.getCurrentUser().getId();
		Specifications<Store> spec = Specifications.where(StoreSpecifications.withinBoundingBoxOrAssignedTo(north, south, east, west, assigneeId));

		if (storeTypes != null && storeTypes.size() > 0) {
			spec = spec.and(ofTypes(storeTypes));
		}
		spec = spec.and(AuditingEntitySpecifications.isNotDeleted());

		return storeRepository.findAll(spec, page);
	}

	@Override
	public List<Store> findAllInGeoJson(String geoJson) {
		return storeRepository.findAllInGeoJson(geoJson);
	}

	@Override
	public Page<Store> findAllAssignedTo(Integer assigneeId, List<StoreType> storeTypes, Pageable page) {
		Specifications<Store> spec = Specifications.where(StoreSpecifications.assignedTo(assigneeId));

		if (storeTypes != null && storeTypes.size() > 0) {
			spec = spec.and(StoreSpecifications.ofTypes(storeTypes));
		}
		spec = spec.and(AuditingEntitySpecifications.isNotDeleted());

		return storeRepository.findAll(spec, page);
	}

	@Override
	@Transactional
	public StoreCasing addOneCasingToStore(Integer storeId, StoreCasing requestCasing) {
		Store store = findOne(storeId);

		if (store.getSite() == null) {
			throw new IllegalStateException(String.format("Store with ID %s does not have a site!", store.getId()));
		} else {
			// If no shopping center, create one
			if (store.getSite().getShoppingCenter() == null) {
				throw new IllegalStateException(String.format("Site with ID %s does not belong to a shopping center", store.getSite().getId()));
			}
		}

		if (requestCasing.getId() != null) {
			throw new IllegalArgumentException("Casing must be new - use PUT /api/store-casing to save changes to existing casing");
		}

		if (requestCasing.getProjects() != null) {
			requestCasing.setProjects(requestCasing.getProjects().stream()
					.map(project -> projectService.findOne(project.getId()))
					.collect(Collectors.toList()));
		}

		if (requestCasing.getStoreStatus() != null) {
			throw new IllegalArgumentException("Do not include store status. Will be provided by web service");
		} else {
			// Use the latest
			StoreStatus storeStatus = StoreUtil.getLatestStatusAsOfDateTime(store, LocalDateTime.now())
					.orElseGet(() -> {
						StoreStatus newStatus = new StoreStatus();
						newStatus.setStatusStartDate(requestCasing.getCasingDate());
						newStatus.setStatus("Open");
						newStatus.setStore(store);
						return storeStatusService.addOne(newStatus);
					});
			requestCasing.setStoreStatus(storeStatus.getStatus());
		}

		if (requestCasing.getStoreSurvey() != null) {
			throw new IllegalArgumentException("Do not include store survey. Will be provided by web service");
		} else {
			// If the store has a latest non-deleted survey, clone it, otherwise create a brand new one
			StoreSurvey newSurvey = StoreUtil.getLatestSurveyAsOfDateTime(store, LocalDateTime.now())
					.map(StoreSurvey::new).orElseGet(StoreSurvey::new);
			newSurvey.setSurveyDate(requestCasing.getCasingDate());
			newSurvey.setStore(store);
			storeSurveyService.addOne(newSurvey);

			requestCasing.setStoreSurvey(newSurvey);
		}

		if (requestCasing.getShoppingCenterCasing() != null) {
			throw new IllegalArgumentException("Do not include shopping center casing. Will be provided by web service");
		} else {
			// Create a new Shopping center Casing
			final ShoppingCenter shoppingCenter = store.getSite().getShoppingCenter();

			Optional<ShoppingCenterSurvey> latestSurvey = ShoppingCenterUtil.getLatestSurveyAsOfDateTime(shoppingCenter, LocalDateTime.now());
			// if the shopping center has latest non-deleted survey, clone it, otherwise create a brand new one
			ShoppingCenterSurvey newShoppingCenterSurvey = latestSurvey.map(ShoppingCenterSurvey::new).orElseGet(ShoppingCenterSurvey::new);
			newShoppingCenterSurvey.setSurveyDate(requestCasing.getCasingDate());
			newShoppingCenterSurvey.setShoppingCenter(shoppingCenter);
			shoppingCenterSurveyService.addOne(newShoppingCenterSurvey);

			ShoppingCenterCasing shoppingCenterCasing = new ShoppingCenterCasing();
			shoppingCenterCasing.setCasingDate(requestCasing.getCasingDate());
			shoppingCenterCasing.setShoppingCenter(shoppingCenter);
			shoppingCenterCasing.setShoppingCenterSurvey(newShoppingCenterSurvey);
			shoppingCenterCasing.setProjects(requestCasing.getProjects());

			requestCasing.setShoppingCenterCasing(shoppingCenterCasingService.addOne(shoppingCenterCasing));
		}
		requestCasing.setStore(store);
		store.setUpdatedBy(securityService.getCurrentUser());

		return casingService.addOne(requestCasing);
	}

	@Override
	@Transactional
	public StoreModel addOneModelToStore(Integer storeId, StoreModel request) {
		Store existing = findOneUsingSpecs(storeId);

		request.setStore(existing);
		existing.setUpdatedBy(securityService.getCurrentUser());

		return modelService.addOne(request);
	}

	@Override
	@Transactional
	public StoreSurvey addOneSurveyToStore(Integer storeId, StoreSurvey request) {
		Store existing = findOneUsingSpecs(storeId);

		request.setStore(existing);
		existing.setUpdatedBy(securityService.getCurrentUser());

		return surveyService.addOne(request);
	}

	@Override
	@Transactional
	public StoreVolume addOneVolumeToStore(Integer storeId, StoreVolume volume) {
		Store existing = findOneUsingSpecs(storeId);

		volume.setStore(existing);
		existing.setUpdatedBy(securityService.getCurrentUser());

		return storeVolumeService.addOne(volume);
	}

	@Override
	public List<Store> findAllByProjectId(Integer projectId) {
		return storeRepository.findAll(Specifications.where(StoreSpecifications.projectIdEquals(projectId)).and(AuditingEntitySpecifications.isNotDeleted()));
	}

	@Override
	public List<Store> findAllByBannerId(Integer bannerId) {
		Banner banner = bannerService.findOne(bannerId);
		if (banner == null) {
			throw new IllegalArgumentException("No banner found with this id");
		}

		return banner.getStores()
				.stream()
				.filter(store -> store.getDeletedDate() == null)
				.collect(Collectors.toList());
	}

	@Override
	public List<Store> findAllBySiteIdUsingSpecs(Integer siteId) {
		return storeRepository.findAll(
				where(siteIdEquals(siteId))
						.and(isNotDeleted())
		);
	}

	@Override
	public Store updateEntityAttributes(Store existing, Store request) {

		//If the store is changing type to active, we have some special handling to do
		if (request.getStoreType() == StoreType.ACTIVE && request.getStoreType() != existing.getStoreType()) {
			//Find any existing ACTIVE store for the site
			Site site = existing.getSite();
			Store existingActiveStore = SiteService.findActiveStore(site);

			if (existingActiveStore != null) {
				existingActiveStore.setStoreType(StoreType.HISTORICAL);
				existingActiveStore.setUpdatedBy(securityService.getCurrentUser());
			}
		}

		existing.setStoreName(request.getStoreName());
		existing.setStoreNumber(request.getStoreNumber());
		existing.setStoreType(request.getStoreType());
		existing.setDateOpened(request.getDateOpened());
		existing.setDateClosed(request.getDateClosed());
		existing.setFloating((request.getFloating()));

		return existing;
	}

	@Override
	@Transactional
	public Store updateOneBanner(Integer storeId, Integer bannerId) {
		Store store = findOne(storeId);
		if (store == null) {
			throw new IllegalArgumentException("No Store found with this id");
		}

		Banner banner = bannerService.findOne(bannerId);
		if (banner == null) {
			throw new IllegalArgumentException("No Banner found with this id");
		}

		store.setBanner(banner);
		banner.getStores().add(store);

		return store;
	}

	@Override
	@Transactional
	public StoreStatus addOneStatusToStore(Integer storeId, StoreStatus storeStatus) {
		Store store = findOne(storeId);
		getValidator().validateNotNull(store);

		storeStatus.setStore(store);
		store.setUpdatedBy(securityService.getCurrentUser());

		return storeStatusService.addOne(storeStatus);
	}

	@Override
	@Transactional
	public void deleteStoreStatus(Integer storeId, Integer statusId) {
		storeStatusService.deleteOne(statusId);
	}

	@Override
	@Transactional
	public void deleteStoreVolume(Integer storeId, Integer volumeId) {
		storeVolumeService.deleteOne(volumeId);
	}

	@Override
	public String getEntityName() {
		return "Store";
	}

	@Override
	public StoreValidator getValidator() {
		return storeValidator;
	}

}
