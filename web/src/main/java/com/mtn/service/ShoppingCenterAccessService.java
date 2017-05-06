package com.mtn.service;

import com.mtn.model.domain.ShoppingCenterAccess;
import com.mtn.repository.ShoppingCenterAccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Allen on 5/6/2017.
 */
@Service
public class ShoppingCenterAccessService extends ValidatingDataService<ShoppingCenterAccess> {

    @Autowired
    private ShoppingCenterAccessRepository accessRepository;

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

    public ShoppingCenterAccess findOne(Integer id) {
        return accessRepository.findOne(id);
    }

    @Transactional
    public ShoppingCenterAccess updateOne(Integer id, ShoppingCenterAccess request) {
        validateNotNull(request);
        validateForUpdate(request);

        ShoppingCenterAccess existing = findOne(id);
        if (existing == null) {
            throw new IllegalArgumentException("No ShoppingCenterTenant found with this id");
        }

        existing.setHasLeftIn(request.getHasLeftIn());
        existing.setHasLeftOut(request.getHasLeftOut());
        existing.setHasTrafficLight(request.getHasTrafficLight());
        existing.setHasOneWayRoad(request.getHasOneWayRoad());
        existing.setHasRightIn(request.getHasRightIn());
        existing.setHasRightOut(request.getHasRightOut());

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
    public void validateDoesNotExist(ShoppingCenterAccess object) {
        //No unique contraints to enforce
    }
}
