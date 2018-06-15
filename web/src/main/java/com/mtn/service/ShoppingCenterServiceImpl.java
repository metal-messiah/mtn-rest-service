package com.mtn.service;

import com.mtn.model.domain.*;
import com.mtn.repository.ShoppingCenterRepository;
import com.mtn.validators.ShoppingCenterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mtn.repository.specification.ShoppingCenterSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 4/23/2017.
 */
@Service
public class ShoppingCenterServiceImpl extends EntityServiceImpl<ShoppingCenter> implements ShoppingCenterService {

    @Autowired
    private ShoppingCenterRepository shoppingCenterRepository;
    @Autowired
    private SiteService siteService;
    @Autowired
    private ShoppingCenterSurveyService surveyService;
    @Autowired
    private ShoppingCenterCasingService casingService;
    @Autowired
    private ShoppingCenterValidator shoppingCenterValidator;

    @Override
    @Transactional
    public ShoppingCenterCasing addOneCasingToShoppingCenter(Integer shoppingCenterId, ShoppingCenterCasing request) {
        ShoppingCenter existing = findOneUsingSpecs(shoppingCenterId);
        getValidator().validateNotNull(existing);

        request.setShoppingCenter(existing);
        existing.setUpdatedBy(securityService.getCurrentUser());

        return casingService.addOne(request);
    }

    @Override
    @Transactional
    public Site addOneSiteToShoppingCenter(Integer shoppingCenterId, Site request) {
        ShoppingCenter existing = findOneUsingSpecs(shoppingCenterId);
        getValidator().validateNotNull(existing);

        request.setShoppingCenter(existing);
        existing.setUpdatedBy(securityService.getCurrentUser());

        return siteService.addOne(request);
    }

    @Override
    @Transactional
    public ShoppingCenterSurvey addOneSurveyToShoppingCenter(Integer shoppingCenterId, ShoppingCenterSurvey request) {
        ShoppingCenter existing = findOneUsingSpecs(shoppingCenterId);
        getValidator().validateNotNull(existing);

        request.setShoppingCenter(existing);
        existing.setUpdatedBy(securityService.getCurrentUser());

        return surveyService.addOne(request);
    }

    @Override
    public Page<ShoppingCenter> findAllUsingSpecs(Pageable page) {
        return getEntityRepository().findAll(
                where(isNotDeleted())
                , page
        );
    }

    @Override
    public List<ShoppingCenter> findAllByProjectId(Integer projectId) {
        return getEntityRepository().findAllByCasingsProjectsIdAndDeletedDateIsNull(projectId);
    }

    @Override
    public Page<ShoppingCenter> findAllByNameUsingSpecs(String name, Pageable page) {
        return getEntityRepository().findAll(
                where(nameContains(name))
                        .and(isNotDeleted())
                , page
        );
    }

    @Override
    public Page<ShoppingCenter> findAllByOwnerUsingSpecs(String owner, Pageable page) {
        return getEntityRepository().findAll(
                where(ownerContains(owner))
                        .and(isNotDeleted())
                , page
        );
    }

    @Override
    public Page<ShoppingCenter> findAllByNameOrOwnerUsingSpecs(String q, Pageable page) {
        return getEntityRepository().findAll(
                where(
                        where(nameContains(q))
                                .or(ownerContains(q)))
                        .and(isNotDeleted())
                , page
        );
    }

    @Override
    public ShoppingCenter findOneUsingSpecs(Integer id) {
        return getEntityRepository().findOne(
                where(idEquals(id))
                        .and(isNotDeleted())
        );
    }

    @Override
    public ShoppingCenter updateEntityAttributes(ShoppingCenter existing, ShoppingCenter request) {
        existing.setName(request.getName());
        existing.setOwner(request.getOwner());

        return existing;
    }

    @Override
    public String getEntityName() {
        return "ShoppingCenter";
    }

    @Override
    public void handleAssociationsOnDeletion(ShoppingCenter existing) {
        // TODO - handle casings, sites, surveys
    }

    @Override
    public void handleAssociationsOnCreation(ShoppingCenter request) {
        // TODO - handle casings, sites, surveys
    }

    @Override
    public ShoppingCenterRepository getEntityRepository() {
        return shoppingCenterRepository;
    }

    @Override
    public ShoppingCenterValidator getValidator() {
        return shoppingCenterValidator;
    }


}
