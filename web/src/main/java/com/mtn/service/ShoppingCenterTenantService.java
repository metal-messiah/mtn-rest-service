package com.mtn.service;

import com.mtn.exception.VersionConflictException;
import com.mtn.model.domain.ShoppingCenterTenant;
import com.mtn.model.simpleView.SimpleShoppingCenterTenantView;
import com.mtn.repository.ShoppingCenterTenantRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mtn.repository.specification.ShoppingCenterTenantSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 5/6/2017.
 */
@Service
public class ShoppingCenterTenantService extends ValidatingDataService<ShoppingCenterTenant> {

    @Autowired
    private ShoppingCenterTenantRepository tenantRepository;
    @Autowired
    private SecurityService securityService;

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

    public List<ShoppingCenterTenant> findAllBySurveyIdUsingSpecs(Integer id) {
        return tenantRepository.findAll(
                where(shoppingCenterSurveyIdEquals(id))
                        .and(isNotDeleted())
        );
    }

    public ShoppingCenterTenant findOne(Integer id) {
        return tenantRepository.findOne(id);
    }

    public ShoppingCenterTenant findOneUsingSpecs(Integer id) {
        return tenantRepository.findOne(
                where(idEquals(id))
                        .and(isNotDeleted())
        );
    }

    @Transactional
    public ShoppingCenterTenant updateOne(Integer id, ShoppingCenterTenant request) {
        validateNotNull(request);
        validateForUpdate(request);

        ShoppingCenterTenant existing = findOne(id);
        if (existing == null) {
            throw new IllegalArgumentException("No ShoppingCenterTenant found with this id");
        }
        if (!request.getVersion().equals(existing.getVersion())) {
            throw new VersionConflictException(new SimpleShoppingCenterTenantView(existing));
        }

        existing.setName(request.getName());
        existing.setIsAnchor(request.getIsAnchor());
        existing.setIsOutparcel(request.getIsOutparcel());
        existing.setSqft(request.getSqft());
        existing.setUpdatedBy(securityService.getCurrentUser());

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
        }
    }

    @Override
    public void validateDoesNotExist(ShoppingCenterTenant object) {
        //No unique contraints to enforce
    }
}
