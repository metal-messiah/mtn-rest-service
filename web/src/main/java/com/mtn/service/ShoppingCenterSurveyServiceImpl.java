package com.mtn.service;

import com.mtn.model.domain.ShoppingCenterAccess;
import com.mtn.model.domain.ShoppingCenterSurvey;
import com.mtn.model.domain.ShoppingCenterTenant;
import com.mtn.model.domain.UserProfile;
import com.mtn.repository.ShoppingCenterSurveyRepository;
import com.mtn.validators.ShoppingCenterAccessValidator;
import com.mtn.validators.ShoppingCenterSurveyValidator;
import com.mtn.validators.ShoppingCenterTenantValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

import static com.mtn.repository.specification.ShoppingCenterSurveySpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 5/6/2017.
 */
@Service
public class ShoppingCenterSurveyServiceImpl extends EntityServiceImpl<ShoppingCenterSurvey> implements ShoppingCenterSurveyService {

    @Autowired
    private ShoppingCenterAccessService accessService;
    @Autowired
    private ShoppingCenterTenantService tenantService;
    @Autowired
    private ShoppingCenterTenantService shoppingCenterTenantService;
    @Autowired
    private ShoppingCenterSurveyRepository shoppingCenterSurveyRepository;
    @Autowired
    private ShoppingCenterSurveyValidator shoppingCenterSurveyValidator;
    @Autowired
    private ShoppingCenterAccessValidator shoppingCenterAccessValidator;
    @Autowired
    private ShoppingCenterTenantValidator shoppingCenterTenantValidator;

    @Override
	@Transactional
    public ShoppingCenterAccess addOneAccessToSurvey(Integer surveyId, ShoppingCenterAccess request) {
        ShoppingCenterSurvey existing = findOneUsingSpecs(surveyId);
        getValidator().validateNotNull(existing);

        request.setSurvey(existing);
        existing.setUpdatedBy(securityService.getCurrentUser());

        return accessService.addOne(request);
    }

    @Override
	@Transactional
    public List<ShoppingCenterTenant> createNewTenantsForSurvey(Integer surveyId, List<ShoppingCenterTenant> requestTenants) {
        ShoppingCenterSurvey existing = findOneUsingSpecs(surveyId);
        getValidator().validateNotNull(existing);

        UserProfile currentUser = securityService.getCurrentUser();
        for (ShoppingCenterTenant tenant : requestTenants) {
            tenant.setCreatedBy(currentUser);
            tenant.setUpdatedBy(currentUser);
            tenant.setSurvey(existing);
            this.shoppingCenterTenantService.getValidator().validateForInsert(tenant);
        }
        existing.getTenants().addAll(requestTenants);
        existing.setUpdatedBy(securityService.getCurrentUser());

        return requestTenants;
    }

    @Override
	public List<ShoppingCenterSurvey> findAllByShoppingCenterId(Integer shoppingCenterId) {
        return getEntityRepository().findAllByShoppingCenterId(shoppingCenterId);
    }

    @Override
	public List<ShoppingCenterSurvey> findAllByShoppingCenterIdUsingSpecs(Integer shoppingCenterId) {
        return getEntityRepository().findAll(
                where(shoppingCenterIdEquals(shoppingCenterId))
                        .and(isNotDeleted())
        );
    }

    @Override
    public ShoppingCenterSurvey findLatestShoppingCenterSurveyForStore(Integer storeId) {
        List<ShoppingCenterSurvey> surveys = this.findAllByShoppingCenterIdUsingSpecs(storeId);
        if (surveys.size() > 0) {
            return surveys.stream().max(Comparator.comparing(ShoppingCenterSurvey::getSurveyDate)).get();
        } else {
            return null;
        }
    }

    @Override
    public Page<ShoppingCenterSurvey> findAllUsingSpecs(Pageable page) {
        return getEntityRepository().findAll(
                where(isNotDeleted()), page
        );
    }

    @Override
    public ShoppingCenterSurvey findOneUsingSpecs(Integer id) {
        return getEntityRepository().findOne(
                where(idEquals(id))
                        .and(isNotDeleted())
        );
    }

    @Override
    public ShoppingCenterSurvey updateEntityAttributes(ShoppingCenterSurvey existing, ShoppingCenterSurvey request) {
        existing.setNote(request.getNote());
        existing.setFlowHasOneWayAisles(request.getFlowHasOneWayAisles());
        existing.setFlowRating(request.getFlowRating());
        existing.setTenantOccupiedCount(request.getTenantOccupiedCount());
        existing.setTenantVacantCount(request.getTenantVacantCount());
        existing.setSqFtPercentOccupied(request.getSqFtPercentOccupied());

        return existing;
    }

    @Override
    public String getEntityName() {
        return "ShoppingCenterSurvey";
    }

    @Override
    public void handleAssociationsOnDeletion(ShoppingCenterSurvey existing) {
        // TODO - Handle Accesses, Tenants, Casings
    }

    @Override
    public void handleAssociationsOnCreation(ShoppingCenterSurvey request) {
        UserProfile currentUser = securityService.getCurrentUser();
        request.getAccesses().forEach(access -> {
            shoppingCenterAccessValidator.validateForInsert(access);
            access.setCreatedBy(currentUser);
            access.setUpdatedBy(currentUser);
        });
        request.getTenants().forEach(tenant -> {
            shoppingCenterTenantValidator.validateForInsert(tenant);
            tenant.setCreatedBy(currentUser);
            tenant.setUpdatedBy(currentUser);
        });
    }

    @Override
    public ShoppingCenterSurveyRepository getEntityRepository() {
        return shoppingCenterSurveyRepository;
    }

    @Override
    public ShoppingCenterSurveyValidator getValidator() {
        return shoppingCenterSurveyValidator;
    }


}
