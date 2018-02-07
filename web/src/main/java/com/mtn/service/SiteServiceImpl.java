package com.mtn.service;

import com.mtn.constant.StoreType;
import com.mtn.model.domain.Site;
import com.mtn.model.domain.Store;
import com.mtn.repository.SiteRepository;
import com.mtn.validators.SiteValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mtn.repository.specification.SiteSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 4/24/2017.
 */
@Service
public class SiteServiceImpl extends EntityServiceImpl<Site> implements SiteService {

    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private StoreService storeService;
    @Autowired
    private SiteValidator siteValidator;

    @Override
    @Transactional
    public Store addOneStoreToSite(Integer siteId, Store request, boolean overrideActiveStore) {
        Site existing = findOneUsingSpecs(siteId);
        getValidator().validateNotNull(existing);
        request.setSite(existing);

        //If the new store is ACTIVE, we have some special handling to do
        if (request.getType() == StoreType.ACTIVE) {
            //First, ensure the request is valid for insert
            storeService.getValidator().validateForInsert(request);

            //Then, check for another existing ACTIVE store
            Store existingActiveStore = existing.findActiveStore();

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

        existing.setUpdatedBy(securityService.getCurrentUser());

        return storeService.addOne(request);
    }

    @Override
    public List<Site> findAllByShoppingCenterIdUsingSpecs(Integer shoppingCenterId) {
        return getEntityRepository().findAll(
                where(shoppingCenterIdEquals(shoppingCenterId))
                        .and(isNotDeleted())
        );
    }

    @Override
    public Page<Site> findAllUsingSpecs(Pageable page) {
        return getEntityRepository().findAll(where(isNotDeleted()), page);
    }

    @Override
    public Site findOneUsingSpecs(Integer id) {
        return getEntityRepository().findOne(
                where(idEquals(id))
                        .and(isNotDeleted())
        );
    }

    @Override
    public Site getUpdatedEntity(Site existing, Site request) {
        existing.setLocation(request.getLocation());
        existing.setType(request.getType());
        existing.setIntersectionType(request.getIntersectionType());
        existing.setAddress1(request.getAddress1());
        existing.setAddress2(request.getAddress2());
        existing.setCity(request.getCity());
        existing.setState(request.getState());
        existing.setPostalCode(request.getPostalCode());
        existing.setCounty(request.getCounty());
        existing.setCountry(request.getCountry());
        existing.setFootprintSqft(request.getFootprintSqft());
        existing.setIntersectionStreetPrimary(request.getIntersectionStreetPrimary());
        existing.setIntersectionStreetSecondary(request.getIntersectionStreetSecondary());
        existing.setIntersectionQuad(request.getIntersectionQuad());
        existing.setPositionInCenter(request.getPositionInCenter());

        return existing;
    }

    @Override
    public String getEntityName() {
        return "Site";
    }

    @Override
    public void handleAssociationsOnDeletion(Site existing) {
        // TODO - Handle Stores
    }

    @Override
    public void handleAssociationsOnCreation(Site request) {
        // TODO - Handle Stores
    }

    @Override
    public SiteRepository getEntityRepository() {
        return siteRepository;
    }

    @Override
    public SiteValidator getValidator() {
        return siteValidator;
    }


}