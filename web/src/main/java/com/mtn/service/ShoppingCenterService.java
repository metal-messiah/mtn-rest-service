package com.mtn.service;

import com.mtn.model.domain.*;
import com.mtn.model.view.ShoppingCenterView;
import com.mtn.repository.ShoppingCenterRepository;
import com.mtn.repository.specification.ShoppingCenterSpecifications;
import com.mtn.validators.ShoppingCenterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import static org.springframework.data.jpa.domain.Specifications.where;

@Service
public class ShoppingCenterService extends EntityService<ShoppingCenter, ShoppingCenterView> {

    @Autowired
    public ShoppingCenterService(SecurityService securityService,
                                 ShoppingCenterRepository repository,
                                 ShoppingCenterValidator validator) {
        super(securityService, repository, validator, ShoppingCenter::new);
    }

    public Page<ShoppingCenter> findAllByNameUsingSpecs(String name, Pageable page) {
        return this.repository.findAll(
                Specifications.where(ShoppingCenterSpecifications.nameContains(name))
                        .and(ShoppingCenterSpecifications.isNotDeleted())
                , page
        );
    }

    public Page<ShoppingCenter> findAllByOwnerUsingSpecs(String owner, Pageable page) {
        return this.repository.findAll(
                Specifications.where(ShoppingCenterSpecifications.ownerContains(owner))
                        .and(ShoppingCenterSpecifications.isNotDeleted())
                , page
        );
    }

    public Page<ShoppingCenter> findAllByNameOrOwnerUsingSpecs(String q, Pageable page) {
        return this.repository.findAll(
                Specifications.where(
                        where(ShoppingCenterSpecifications.nameContains(q))
                                .or(ShoppingCenterSpecifications.ownerContains(q)))
                        .and(ShoppingCenterSpecifications.isNotDeleted())
                , page
        );
    }

    @Transactional
    public ShoppingCenter createNew() {
        UserProfile currentUser = securityService.getCurrentUser();

        ShoppingCenter newEntity = new ShoppingCenter();
        newEntity.setCreatedBy(currentUser);
        newEntity.setUpdatedBy(currentUser);

        return newEntity;
    }

    @Override
    protected void setEntityAttributesFromRequest(ShoppingCenter shoppingCenter, ShoppingCenterView request) {
        shoppingCenter.setName(StringUtils.isEmpty(request.getName()) ? null : request.getName());
        shoppingCenter.setOwner(StringUtils.isEmpty(request.getOwner()) ? null : request.getOwner());
        shoppingCenter.setCenterType(request.getCenterType());
    }

    @Override
    public void handleAssociationsOnDeletion(ShoppingCenter existing) {
        existing.getSites().forEach(site -> site.setShoppingCenter(null));
    }
}
