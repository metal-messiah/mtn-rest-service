package com.mtn.service;

import com.mtn.constant.StoreType;
import com.mtn.model.domain.*;
import com.mtn.repository.StoreRepository;
import com.mtn.validators.StoreValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    private static final String QUERY_STORES_WHERE_PARENT_COMPANY_ID_IN_LIST = "" +
            "SELECT s.* " +
            "FROM store s " +
            "RIGHT JOIN company c ON c.id = s.parent_company_id " +
            "WHERE c.id IN :companyIds " +
            "AND s.deleted_date IS NULL";

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
        return getEntityRepository().findAllByInteractionsProjectIdAndDeletedDateIsNull(id);
    }

    @Override
    public List<Store> findAllByParentCompanyId(Integer companyId) {
        Company company = companyService.findOne(companyId);
        if (company == null) {
            throw new IllegalArgumentException("No Company found with this id");
        }

        return company.getStores()
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

        Set<Integer> companyIds = companyService.findAllChildCompanyIdsRecursive(company);

        Map<String, Object> params = new HashMap<>();
        params.put("companyIds", companyIds);

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

        //If the store is changing status to active, we have some special handling to do
        if (request.getType() == StoreType.ACTIVE && request.getType() != existing.getType()) {
            //Find any existing ACTIVE store for the site
            Site site = existing.getSite();
            Store existingActiveStore = site.findActiveStore();

            if (existingActiveStore != null) {
                existingActiveStore.setType(StoreType.HISTORICAL);
                existingActiveStore.setUpdatedBy(securityService.getCurrentUser());
            }
        }

        existing.setName(request.getName());
        existing.setType(request.getType());
        existing.setOpenedDate(request.getOpenedDate());
        existing.setClosedDate(request.getClosedDate());

        return existing;
    }

    @Override
    @Transactional
    public Store updateOneParentCompany(Integer storeId, Integer companyId) {
        Store store = findOneUsingSpecs(storeId);
        if (store == null) {
            throw new IllegalArgumentException("No Store found with this id");
        }

        Company company = companyService.findOne(companyId);
        if (company == null) {
            throw new IllegalArgumentException("No Company found with this id");
        }

        store.setParentCompany(company);
        company.getStores().add(store);

        return store;
    }

    @Override
    public String getEntityName() {
        return "Store";
    }

    @Override
    public void handleAssociationsOnDeletion(Store existing) {
        // TODO - casings, interactions, models, surveys, volumes
    }

    @Override
    public void handleAssociationsOnCreation(Store request) {
        // TODO - casings, interactions, models, surveys, volumes
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
