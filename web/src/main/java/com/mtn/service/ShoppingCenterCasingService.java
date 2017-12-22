package com.mtn.service;

import com.mtn.exception.VersionConflictException;
import com.mtn.model.domain.ShoppingCenterCasing;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.simpleView.SimpleShoppingCenterCasingView;
import com.mtn.repository.ShoppingCenterCasingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mtn.repository.specification.ShoppingCenterCasingSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 5/6/2017.
 */
@Service
public class ShoppingCenterCasingService extends ValidatingDataService<ShoppingCenterCasing> {

    @Autowired
    private ShoppingCenterCasingRepository casingRepository;
    @Autowired
    private SecurityService securityService;

    @Transactional
    public ShoppingCenterCasing addOne(ShoppingCenterCasing request) {
        validateForInsert(request);

        UserProfile currentUser = securityService.getCurrentUser();
        request.setCreatedBy(currentUser);
        request.setUpdatedBy(currentUser);

        return casingRepository.save(request);
    }

    @Transactional
    public void deleteOne(Integer id) {
        ShoppingCenterCasing existing = findOneUsingSpecs(id);
        if (existing == null) {
            throw new IllegalArgumentException("No ShoppingCenterCasing found with this id");
        }

        existing.setDeletedBy(securityService.getCurrentUser());
    }

    public List<ShoppingCenterCasing> findAllByProjectId(Integer id) {
        return casingRepository.findAllByInteractionsProjectIdAndDeletedDateIsNull(id);
    }

    public List<ShoppingCenterCasing> findAllByShoppingCenterId(Integer shoppingCenterId) {
        return casingRepository.findAllByShoppingCenterId(shoppingCenterId);
    }

    public List<ShoppingCenterCasing> findAllByShoppingCenterIdUsingSpecs(Integer shoppingCenterId) {
        return casingRepository.findAll(
                where(shoppingCenterIdEquals(shoppingCenterId))
                        .and(isNotDeleted())
        );
    }

    public ShoppingCenterCasing findOne(Integer id) {
        return casingRepository.findOne(id);
    }

    public ShoppingCenterCasing findOneUsingSpecs(Integer id) {
        return casingRepository.findOne(
                where(idEquals(id))
                        .and(isNotDeleted())
        );
    }

    @Transactional
    public ShoppingCenterCasing updateOne(Integer id, ShoppingCenterCasing request) {
        validateNotNull(request);
        validateForUpdate(request);

        ShoppingCenterCasing existing = findOneUsingSpecs(id);
        if (existing == null) {
            throw new IllegalArgumentException("No ShoppingCenterCasing found with this id");
        }
        if (!request.getVersion().equals(existing.getVersion())) {
            throw new VersionConflictException(new SimpleShoppingCenterCasingView(existing));
        }


        existing.setUpdatedBy(securityService.getCurrentUser());

        return existing;
    }

    @Override
    public String getEntityName() {
        return "ShoppingCenterCasing";
    }

    @Override
    public void validateForInsert(ShoppingCenterCasing object) {
        super.validateForInsert(object);

        if (object.getShoppingCenter() == null) {
            throw new IllegalStateException("ShoppingCenterCasing was not mapped to ShoppingCenter before saving!");
        }
    }

    @Override
    public void validateBusinessRules(ShoppingCenterCasing object) {
        //No special rules at this time
    }

    @Override
    public void validateDoesNotExist(ShoppingCenterCasing object) {
        //No unique contraints to enforce
    }
}
