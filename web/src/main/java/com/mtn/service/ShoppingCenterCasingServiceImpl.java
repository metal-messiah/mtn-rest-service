package com.mtn.service;

import com.mtn.model.domain.ShoppingCenterCasing;
import com.mtn.repository.ShoppingCenterCasingRepository;
import com.mtn.validators.ShoppingCenterCasingValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mtn.repository.specification.ShoppingCenterCasingSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 5/6/2017.
 */
@Service
public class ShoppingCenterCasingServiceImpl extends EntityServiceImpl<ShoppingCenterCasing> implements ShoppingCenterCasingService {

    @Autowired
    private ShoppingCenterCasingRepository shoppingCenterCasingRepository;
    @Autowired
    private ShoppingCenterCasingValidator shoppingCenterCasingValidator;


    @Override
    public List<ShoppingCenterCasing> findAllByProjectId(Integer id) {
        return getEntityRepository().findAllByProjectsIdAndDeletedDateIsNull(id);
    }

    @Override
    public List<ShoppingCenterCasing> findAllByShoppingCenterId(Integer shoppingCenterId) {
        return getEntityRepository().findAllByShoppingCenterId(shoppingCenterId);
    }

    @Override
    public List<ShoppingCenterCasing> findAllByShoppingCenterIdUsingSpecs(Integer shoppingCenterId) {
        return getEntityRepository().findAll(
                where(shoppingCenterIdEquals(shoppingCenterId))
                        .and(isNotDeleted())
        );
    }

    @Override
    public Page<ShoppingCenterCasing> findAllUsingSpecs(Pageable page) {
        return getEntityRepository().findAll(
                where(isNotDeleted()), page
        );
    }

    @Override
    public ShoppingCenterCasing findOneUsingSpecs(Integer id) {
        return getEntityRepository().findOne(
                where(idEquals(id))
                        .and(isNotDeleted())
        );
    }

    @Override
    public ShoppingCenterCasing getUpdatedEntity(ShoppingCenterCasing existing, ShoppingCenterCasing request) {
        // TODO update existing from request

        return existing;
    }

    @Override
    public String getEntityName() {
        return "ShoppingCenterCasing";
    }

    @Override
    public void handleAssociationsOnDeletion(ShoppingCenterCasing existing) {
        // TODO - manage survey
    }

    @Override
    public void handleAssociationsOnCreation(ShoppingCenterCasing request) {
        // TODO - manage survey
    }

    @Override
    public ShoppingCenterCasingRepository getEntityRepository() {
        return shoppingCenterCasingRepository;
    }

    @Override
    public ShoppingCenterCasingValidator getValidator() {
        return shoppingCenterCasingValidator;
    }

}
