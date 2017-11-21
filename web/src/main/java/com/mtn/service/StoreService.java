package com.mtn.service;

import com.mtn.constant.StoreType;
import com.mtn.exception.VersionConflictException;
import com.mtn.model.domain.*;
import com.mtn.model.view.StoreView;
import com.mtn.repository.StoreRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class StoreService extends ValidatingDataService<Store> {

    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private StoreSurveyService surveyService;
    @Autowired
    private SecurityService securityService;
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

    private static final String QUERY_STORES_WHERE_PARENT_COMPANY_ID_IN_LIST = "" +
            "SELECT s.* " +
            "FROM store s " +
            "RIGHT JOIN company c ON c.id = s.parent_company_id " +
            "WHERE c.id IN :companyIds " +
            "AND s.deleted_date IS NULL";

    @Transactional
    public Store addOne(Store request) {
        validateForInsert(request);

        UserProfile currentUser = securityService.getCurrentUser();
        request.setCreatedBy(currentUser);
        request.setUpdatedBy(currentUser);

        return storeRepository.save(request);
    }

    @Transactional
    public StoreCasing addOneCasingToStore(Integer storeId, StoreCasing request) {
        Store existing = findOneUsingSpecs(storeId);
        validateNotNull(existing);

        request.setStore(existing);
        existing.setUpdatedBy(securityService.getCurrentUser());

        return casingService.addOne(request);
    }

    @Transactional
    public StoreModel addOneModelToStore(Integer storeId, StoreModel request) {
        Store existing = findOneUsingSpecs(storeId);
        validateNotNull(existing);

        request.setStore(existing);
        existing.setUpdatedBy(securityService.getCurrentUser());

        return modelService.addOne(request);
    }

    @Transactional
    public StoreSurvey addOneSurveyToStore(Integer storeId, StoreSurvey request) {
        Store existing = findOneUsingSpecs(storeId);
        validateNotNull(existing);

        request.setStore(existing);
        existing.setUpdatedBy(securityService.getCurrentUser());

        return surveyService.addOne(request);
    }

    @Transactional
    public StoreVolume addOneVolumeToStore(Integer storeId, StoreVolume volume) {
        Store existing = findOneUsingSpecs(storeId);
        validateNotNull(existing);

        volume.setStore(existing);
        existing.setUpdatedBy(securityService.getCurrentUser());

        return volumeService.addOne(volume);
    }

    @Transactional
    public void deleteOne(Integer id) {
        Store existing = findOneUsingSpecs(id);
        if (existing == null) {
            throw new IllegalArgumentException("No Site found with this id");
        }

        existing.setDeletedBy(securityService.getCurrentUser());
    }

    public List<Store> findAllByProjectId(Integer id) {
        return storeRepository.findAllByInteractionsProjectIdAndDeletedDateIsNull(id);
    }

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

    public List<Store> findAllBySiteIdUsingSpecs(Integer siteId) {
        return storeRepository.findAll(
                where(siteIdEquals(siteId))
                        .and(isNotDeleted())
        );
    }

    public Store findOneUsingSpecs(Integer id) {
        return storeRepository.findOne(
                where(idEquals(id))
                        .and(isNotDeleted())
        );
    }

    @Transactional
    public Store updateOne(Integer id, Store request, boolean overrideActiveStore) {
        validateForUpdate(request);

        Store existing = findOneUsingSpecs(id);
        if (existing == null) {
            throw new IllegalArgumentException("No Store found with this id");
        }
        if (!request.getVersion().equals(existing.getVersion())) {
            throw new VersionConflictException(new StoreView(existing));
        }

        //If the store is changing status to active, we have some special handling to do
        if (request.getType() == StoreType.ACTIVE && request.getType() != existing.getType()) {
            //Find any existing ACTIVE store for the site
            Site site = existing.getSite();
            Store existingActiveStore = site.findActiveStore();

            //If one exists, and no override is provided, throw an error
            if (existingActiveStore != null && !overrideActiveStore) {
                throw new IllegalArgumentException(String.format("A Site may only have one Active Store at a time. Store ID %d is currently set as this Site's Active Store.", existingActiveStore.getId()));
            }
            //Else if one exists, set it to historical before proceeding
            else if (existingActiveStore != null) {
                existingActiveStore.setType(StoreType.HISTORICAL);
                existingActiveStore.setUpdatedBy(securityService.getCurrentUser());
            }
        }

        existing.setName(request.getName());
        existing.setType(request.getType());
        existing.setOpenedDate(request.getOpenedDate());
        existing.setClosedDate(request.getClosedDate());
        existing.setUpdatedBy(securityService.getCurrentUser());

        return existing;
    }

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
    public void validateBusinessRules(Store object) {
        if (object.getSite() == null) {
            throw new IllegalStateException("Store Site should have been set by now");
        } else if (object.getType() == null) {
            throw new IllegalArgumentException(String.format("Store type must be one of: %s", StringUtils.join(StoreType.values(), ", ")));
        }
    }

    @Override
    public void validateDoesNotExist(Store object) {
        //No unique constraints to enforce yet
    }
}
