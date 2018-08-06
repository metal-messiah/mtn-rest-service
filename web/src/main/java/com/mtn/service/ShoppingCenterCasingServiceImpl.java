package com.mtn.service;

import com.mtn.model.domain.ShoppingCenterCasing;
import com.mtn.repository.ShoppingCenterCasingRepository;
import com.mtn.repository.specification.ShoppingCenterCasingSpecifications;
import com.mtn.validators.ShoppingCenterCasingValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
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
    public List<ShoppingCenterCasing> findAllByShoppingCenterId(Integer shoppingCenterId) {
        return shoppingCenterCasingRepository.findAll(
                Specifications.where(ShoppingCenterCasingSpecifications.shoppingCenterIdEquals(shoppingCenterId)));
    }

    @Override
    public List<ShoppingCenterCasing> findAllByShoppingCenterIdUsingSpecs(Integer shoppingCenterId) {
        return shoppingCenterCasingRepository.findAll(
                where(shoppingCenterIdEquals(shoppingCenterId))
                        .and(isNotDeleted())
        );
    }

    @Override
    public ShoppingCenterCasing updateEntityAttributes(ShoppingCenterCasing existing, ShoppingCenterCasing request) {
        existing.setCasingDate(request.getCasingDate());
        existing.setNote(request.getNote());
        existing.setRatingParkingLot(request.getRatingParkingLot());
        existing.setRatingBuildings(request.getRatingBuildings());
        existing.setRatingLighting(request.getRatingLighting());
        existing.setRatingSynergy(request.getRatingSynergy());

        return existing;
    }

    @Override
    public String getEntityName() {
        return "ShoppingCenterCasing";
    }

    @Override
    public ShoppingCenterCasingValidator getValidator() {
        return shoppingCenterCasingValidator;
    }

}
