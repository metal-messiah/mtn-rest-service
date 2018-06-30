package com.mtn.service;

import com.mtn.constant.StoreType;
import com.mtn.model.domain.*;
import com.mtn.repository.StoreRepository;
import com.mtn.validators.StoreValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.mtn.repository.specification.StoreSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 4/26/2017.
 */
@Service
public class StoreServiceImpl extends EntityServiceImpl<Store> implements StoreService {

	@Autowired
	private StoreRepository storeRepository;
	@Autowired
	private StoreSurveyService surveyService;
	@Autowired
	private BannerService bannerService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private StoreCasingService casingService;
	@Autowired
	private StoreModelService modelService;
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
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
	private ShoppingCenterSurveyService shoppingCenterSurveyService;
	@Autowired
	private ShoppingCenterCasingService shoppingCenterCasingService;
	@Autowired
	private ShoppingCenterAccessService shoppingCenterAccessService;
	@Autowired
	private ShoppingCenterTenantService shoppingCenterTenantService;

	private static final String QUERY_STORES_WHERE_PARENT_COMPANY_ID_IN_LIST = "" +
			"SELECT s.* " +
			"FROM store s " +
			"RIGHT JOIN banner c ON c.id = s.banner_id " +
			"WHERE c.id IN :bannerIds " +
			"AND s.deleted_date IS NULL";

	@Override
	public Page<Store> findAllOfTypesInBounds(Float north, Float south, Float east, Float west, List<StoreType> storeTypes, Pageable page) {
		Specifications<Store> spec = where(isNotDeleted()).and(withinBoundingBox(north, south, east, west));

//		StoreType requestedStoreType = StoreType.valueOf(storeType);
		if (storeTypes != null && storeTypes.size() > 0) {
			spec = spec.and(ofTypes(storeTypes));
		}

		return getEntityRepository().findAll(spec, page);
	}

	@Override
	@Transactional
	public StoreCasing addOneCasingToStore(Integer storeId, StoreCasing requestCasing,
										   boolean storeRemodeled, boolean shoppingCenterRedeveloped) {
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
			StoreStatus storeStatus;
			final List<StoreStatus> storeStatuses = store.getStatuses().stream()
					.filter(status -> status.getDeletedDate() == null && status.getStatusStartDate().isBefore(LocalDateTime.now()))
					.collect(Collectors.toList());
			if (storeStatuses != null && storeStatuses.size() > 0) {
				storeStatus = storeStatuses.stream().max(Comparator.comparing(StoreStatus::getStatusStartDate)).get();
			} else {
				storeStatus = new StoreStatus();
				storeStatus.setStatusStartDate(requestCasing.getCasingDate());
				storeStatus.setStatus("Open");
				storeStatus.setStore(store);
				storeStatus = storeStatusService.addOne(storeStatus);
			}
			requestCasing.setStoreStatus(storeStatus);
		}

		if (requestCasing.getStoreSurvey() != null) {
			throw new IllegalArgumentException("Do not include store survey. Will be provided by web service");
		} else {
			// If the store has surveys use the most recent
			StoreSurvey storeSurvey;
			final List<StoreSurvey> storeSurveys = store.getSurveys().stream()
					.filter(survey -> survey.getDeletedDate() == null)
					.collect(Collectors.toList());
			if (storeSurveys != null && storeSurveys.size() > 0) {
				storeSurvey = storeSurveys.stream().max(Comparator.comparing(StoreSurvey::getSurveyDate)).get();
				// If store was remodeled clone the survey
				if (storeRemodeled) {
					storeSurvey = new StoreSurvey(storeSurvey);
					storeSurvey.setSurveyDate(requestCasing.getCasingDate());
					storeSurvey.setStore(store);
					storeSurvey = storeSurveyService.addOne(storeSurvey);
				}
			} else {
				// If store has not surveys create a new one
				storeSurvey = new StoreSurvey();
				storeSurvey.setSurveyDate(requestCasing.getCasingDate());
				storeSurvey.setStore(store);
				storeSurvey = storeSurveyService.addOne(storeSurvey);
			}
			requestCasing.setStoreSurvey(storeSurvey);
			store.setCurrentStoreSurvey(storeSurvey);
		}

		if (requestCasing.getShoppingCenterCasing() != null) {
			throw new IllegalArgumentException("Do not include shopping center casing. Will be provided by web service");
		} else {
			// Create a new Shopping center Casing
			final ShoppingCenter shoppingCenter = store.getSite().getShoppingCenter();

			// if the shopping center has surveys use the most recent
			ShoppingCenterSurvey shoppingCenterSurvey;
			final List<ShoppingCenterSurvey> shoppingCenterSurveys = shoppingCenter.getSurveys().stream()
					.filter(survey -> survey.getDeletedDate() == null)
					.collect(Collectors.toList());
			if (shoppingCenterSurveys != null && shoppingCenterSurveys.size() > 0) {
				shoppingCenterSurvey = shoppingCenterSurveys.stream()
						.max(Comparator.comparing(ShoppingCenterSurvey::getSurveyDate)).get();
				if (shoppingCenterRedeveloped) {
					shoppingCenterSurvey = new ShoppingCenterSurvey(shoppingCenterSurvey);
					shoppingCenterSurvey.setSurveyDate(requestCasing.getCasingDate());
					shoppingCenterSurvey.setShoppingCenter(shoppingCenter);
					shoppingCenterSurvey = shoppingCenterSurveyService.addOne(shoppingCenterSurvey);
				}
			} else {
				shoppingCenterSurvey = new ShoppingCenterSurvey();
				shoppingCenterSurvey.setSurveyDate(requestCasing.getCasingDate());
				shoppingCenterSurvey.setShoppingCenter(shoppingCenter);
				shoppingCenterSurvey = shoppingCenterSurveyService.addOne(shoppingCenterSurvey);
			}

			ShoppingCenterCasing shoppingCenterCasing = new ShoppingCenterCasing();
			shoppingCenterCasing.setCasingDate(requestCasing.getCasingDate());
			shoppingCenterCasing.setShoppingCenter(shoppingCenter);
			shoppingCenterCasing.setShoppingCenterSurvey(shoppingCenterSurvey);
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
	public List<Store> findAllByProjectId(Integer id) {
		return getEntityRepository().findAllByCasingsProjectsIdAndDeletedDateIsNull(id);
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
	public List<Store> findAllByParentCompanyIdRecursive(Integer companyId) {
		Company company = companyService.findOne(companyId);
		if (company == null) {
			throw new IllegalArgumentException("No Company found with this id");
		}

		Set<Company> companies = companyService.findAllChildCompaniesRecursive(company);

		Set<Integer> bannerIds = new HashSet<>();
		for (Company childCompany : companies) {
			List<Banner> banners = childCompany.getBanners();
			for (Banner banner : banners) {
				bannerIds.add(banner.getId());
			}
		}

		Map<String, Object> params = new HashMap<>();
		params.put("bannerIds", bannerIds);

		return jdbcTemplate.queryForList(QUERY_STORES_WHERE_PARENT_COMPANY_ID_IN_LIST, params, Store.class);
	}

	@Override
	public List<Store> findAllBySiteIdUsingSpecs(Integer siteId) {
		return getEntityRepository().findAll(
				where(siteIdEquals(siteId))
						.and(isNotDeleted())
		);
	}

	@Override
	public Page<Store> findAllUsingSpecs(Pageable page) {
		return getEntityRepository().findAll(where(isNotDeleted()), page);
	}

	@Override
	public Store findOneUsingSpecs(Integer id) {
		return getEntityRepository().findOne(
				where(idEquals(id))
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
	public Store setCurrentStoreStatus(Integer storeId, Integer storeStatusId) {
		Store store = findOne(storeId);
		if (store == null) {
			throw new IllegalArgumentException("No store found with this id");
		}

		StoreStatus status = storeStatusService.findOne(storeStatusId);
		if (status == null) {
			throw new IllegalArgumentException("No store status with this id");
		}

		store.setCurrentStatus(status);

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
	public void handleAssociationsOnDeletion(Store existing) {
		// TODO - casings, models, surveys, volumes, statuses
	}

	@Override
	public void handleAssociationsOnCreation(Store request) {
		// TODO - casings, models, surveys, volumes, statuses
	}

	@Override
	public StoreRepository getEntityRepository() {
		return storeRepository;
	}

	@Override
	public StoreValidator getValidator() {
		return storeValidator;
	}


}
