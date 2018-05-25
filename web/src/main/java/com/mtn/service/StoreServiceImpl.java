package com.mtn.service;

import com.mtn.constant.StoreType;
import com.mtn.model.domain.*;
import com.mtn.repository.StoreRepository;
import com.mtn.validators.StoreStatusValidator;
import com.mtn.validators.StoreValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private StoreVolumeService volumeService;
    @Autowired
    private StoreValidator storeValidator;
    @Autowired
    private StoreStatusService storeStatusService;
    @Autowired
    private StoreStatusValidator storeStatusValidator;

    private static final String QUERY_STORES_WHERE_PARENT_COMPANY_ID_IN_LIST = "" +
            "SELECT s.* " +
            "FROM store s " +
            "RIGHT JOIN banner c ON c.id = s.banner_id " +
            "WHERE c.id IN :bannerIds " +
            "AND s.deleted_date IS NULL";

    @Override
    public Page<Store> findAllOfTypeInBounds(Float north, Float south, Float east, Float west, String storeType, Pageable page) {
        Specification<Store> spec = where(isNotDeleted()).and(withinBoundingBox(north, south, east, west));

        if (storeType != null) {
            spec = ((Specifications<Store>) spec).and(ofType(storeType));
        }

        return getEntityRepository().findAll(spec, page);
    }

    @Override
    @Transactional
    public StoreCasing addOneCasingToStore(Integer storeId, StoreCasing request) {
        Store existing = findOneUsingSpecs(storeId);
        getValidator().validateNotNull(existing);

        request.setStore(existing);
        existing.setUpdatedBy(securityService.getCurrentUser());

        return casingService.addOne(request);
    }

    @Override
    @Transactional
    public StoreModel addOneModelToStore(Integer storeId, StoreModel request) {
        Store existing = findOneUsingSpecs(storeId);
        getValidator().validateNotNull(existing);

        request.setStore(existing);
        existing.setUpdatedBy(securityService.getCurrentUser());

        return modelService.addOne(request);
    }

    @Override
    @Transactional
    public StoreSurvey addOneSurveyToStore(Integer storeId, StoreSurvey request) {
        Store existing = findOneUsingSpecs(storeId);
        getValidator().validateNotNull(existing);

        request.setStore(existing);
        existing.setUpdatedBy(securityService.getCurrentUser());

        return surveyService.addOne(request);
    }

    @Override
    @Transactional
    public StoreVolume addOneVolumeToStore(Integer storeId, StoreVolume volume) {
        Store existing = findOneUsingSpecs(storeId);
        getValidator().validateNotNull(existing);

        volume.setStore(existing);
        existing.setUpdatedBy(securityService.getCurrentUser());

        return volumeService.addOne(volume);
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
    public Store getUpdatedEntity(Store existing, Store request) {

        //If the store is changing type to active, we have some special handling to do
        if (request.getStoreType() == StoreType.ACTIVE && request.getStoreType() != existing.getStoreType()) {
            //Find any existing ACTIVE store for the site
            Site site = existing.getSite();
            Store existingActiveStore = site.findActiveStore();

            if (existingActiveStore != null) {
                existingActiveStore.setStoreType(StoreType.HISTORICAL);
                existingActiveStore.setUpdatedBy(securityService.getCurrentUser());
            }
        }

        existing.setStoreName(request.getStoreName());
        existing.setStoreType(request.getStoreType());
        existing.setDateOpened(request.getDateOpened());
        existing.setDateClosed(request.getDateClosed());

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
    public StoreStatus createNewStoreStatus(Integer storeId, StoreStatus storeStatus) {
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
