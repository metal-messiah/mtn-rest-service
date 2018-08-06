package com.mtn.service;

import com.mtn.model.domain.*;
import com.mtn.repository.ShoppingCenterRepository;
import com.mtn.validators.ShoppingCenterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
    private ShoppingCenterValidator shoppingCenterValidator;

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
    public Page<ShoppingCenter> findAllByNameUsingSpecs(String name, Pageable page) {
        return shoppingCenterRepository.findAll(
                Specifications.where(nameContains(name))
                        .and(isNotDeleted())
                , page
        );
    }

    @Override
    public Page<ShoppingCenter> findAllByOwnerUsingSpecs(String owner, Pageable page) {
        return shoppingCenterRepository.findAll(
                Specifications.where(ownerContains(owner))
                        .and(isNotDeleted())
                , page
        );
    }

    @Override
    public Page<ShoppingCenter> findAllByNameOrOwnerUsingSpecs(String q, Pageable page) {
        return shoppingCenterRepository.findAll(
                Specifications.where(
                        where(nameContains(q))
                                .or(ownerContains(q)))
                        .and(isNotDeleted())
                , page
        );
    }

    @Override
    public ShoppingCenter updateEntityAttributes(ShoppingCenter existing, ShoppingCenter request) {
        existing.setName(StringUtils.isEmpty(request.getName()) ? null : request.getName());
        existing.setOwner(StringUtils.isEmpty(request.getOwner()) ? null : request.getOwner());
        existing.setCenterType(request.getCenterType());

        return existing;
    }

    @Override
    public String getEntityName() {
        return "ShoppingCenter";
    }

    @Override
    public ShoppingCenterValidator getValidator() {
        return shoppingCenterValidator;
    }


}
