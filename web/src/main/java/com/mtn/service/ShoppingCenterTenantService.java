package com.mtn.service;

import com.mtn.model.domain.ShoppingCenterTenant;
import com.mtn.repository.ShoppingCenterTenantRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Allen on 5/6/2017.
 */
@Service
public class ShoppingCenterTenantService extends ValidatingDataService<ShoppingCenterTenant> {

    @Autowired
    private ShoppingCenterTenantRepository tenantRepository;

    @Transactional
    public ShoppingCenterTenant addOne(ShoppingCenterTenant request) {
        validateForInsert(request);
        return tenantRepository.save(request);
    }

    @Transactional
    public void deleteOne(Integer id) {
        tenantRepository.delete(id);
    }

    public List<ShoppingCenterTenant> findAllBySurveyId(Integer id) {
        return tenantRepository.findAllBySurveyId(id);
    }

    public ShoppingCenterTenant findOne(Integer id) {
        return tenantRepository.findOne(id);
    }

    @Transactional
    public ShoppingCenterTenant updateOne(Integer id, ShoppingCenterTenant request) {
        validateNotNull(request);
        validateForUpdate(request);

        ShoppingCenterTenant existing = findOne(id);
        if (existing == null) {
            throw new IllegalArgumentException("No ShoppingCenterTenant found with this id");
        }

        existing.setName(request.getName());
        existing.setType(request.getType());
        existing.setIsOutparcel(request.getIsOutparcel());
        existing.setSqft(request.getSqft());

        return existing;
    }

    @Override
    public String getEntityName() {
        return "ShoppingCenterTenant";
    }

    @Override
    public void validateForInsert(ShoppingCenterTenant object) {
        super.validateForInsert(object);

        if (object.getSurvey() == null) {
            throw new IllegalStateException("ShoppingCenterTenant was not mapped to ShoppingCenterSurvey before saving!");
        }
    }

    @Override
    public void validateBusinessRules(ShoppingCenterTenant object) {
        if (StringUtils.isBlank(object.getName())) {
            throw new IllegalArgumentException("ShoppingCenterTenant name must not be null");
        } else if (object.getType() == null) {
            throw new IllegalArgumentException("ShoppingCenterTenant type must not be null");
        }
    }

    @Override
    public void validateDoesNotExist(ShoppingCenterTenant object) {
        //No unique contraints to enforce
    }
}
