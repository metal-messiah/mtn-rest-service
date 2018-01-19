package com.mtn.service;

import com.mtn.exception.VersionConflictException;
import com.mtn.model.domain.ShoppingCenterAccess;
import com.mtn.model.simpleView.SimpleShoppingCenterAccessView;
import com.mtn.repository.ShoppingCenterAccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mtn.repository.specification.ShoppingCenterAccessSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 5/6/2017.
 */
@Service
public class ShoppingCenterAccessService extends ValidatingDataService<ShoppingCenterAccess> {

    @Autowired
    private ShoppingCenterAccessRepository accessRepository;
    @Autowired
    private SecurityService securityService;

    @Transactional
    public ShoppingCenterAccess addOne(ShoppingCenterAccess request) {
        validateForInsert(request);
        return accessRepository.save(request);
    }

    @Transactional
    public void deleteOne(Integer id) {
        accessRepository.delete(id);
    }

    public List<ShoppingCenterAccess> findAllBySurveyId(Integer id) {
        return accessRepository.findAllBySurveyId(id);
    }

    public List<ShoppingCenterAccess> findAllBySurveyIdUsingSpecs(Integer id) {
        return accessRepository.findAll(
                where(shoppingCenterSurveyIdEquals(id))
                        .and(isNotDeleted())
        );
    }

    public ShoppingCenterAccess findOne(Integer id) {
        return accessRepository.findOne(id);
    }

    public ShoppingCenterAccess findOneUsingSpecs(Integer id) {
        return accessRepository.findOne(
                where(idEquals(id))
                        .and(isNotDeleted())
        );
    }

    @Transactional
    public ShoppingCenterAccess updateOne(Integer id, ShoppingCenterAccess request) {
        validateNotNull(request);
        validateForUpdate(request);

        ShoppingCenterAccess existing = findOne(id);
        if (existing == null) {
            throw new IllegalArgumentException("No ShoppingCenterAccess found with this id");
        }
        if (!request.getVersion().equals(existing.getVersion())) {
            throw new VersionConflictException(new SimpleShoppingCenterAccessView(existing));
        }

        existing.setHasLeftIn(request.getHasLeftIn());
        existing.setHasLeftOut(request.getHasLeftOut());
        existing.setHasTrafficLight(request.getHasTrafficLight());
        existing.setHasOneWayRoad(request.getHasOneWayRoad());
        existing.setHasRightIn(request.getHasRightIn());
        existing.setHasRightOut(request.getHasRightOut());
        existing.setUpdatedBy(securityService.getCurrentUser());

        return existing;
    }

    @Override
    public String getEntityName() {
        return "ShoppingCenterAccess";
    }

    @Override
    public void validateForInsert(ShoppingCenterAccess object) {
        super.validateForInsert(object);

        if (object.getSurvey() == null) {
            throw new IllegalStateException("ShoppingCenterAccess was not mapped to ShoppingCenterSurvey before saving!");
        }
    }

    @Override
    public void validateBusinessRules(ShoppingCenterAccess object) {
        //No special rules to enforce yet
    }

    @Override
    public void validateUnique(ShoppingCenterAccess object) {
        //No unique contraints to enforce
    }
}
