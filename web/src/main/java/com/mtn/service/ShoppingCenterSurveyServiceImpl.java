package com.mtn.service;

import com.mtn.model.domain.ShoppingCenterAccess;
import com.mtn.model.domain.ShoppingCenterSurvey;
import com.mtn.model.domain.ShoppingCenterTenant;
import com.mtn.repository.ShoppingCenterSurveyRepository;
import com.mtn.validators.ShoppingCenterSurveyValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private ShoppingCenterSurveyRepository shoppingCenterSurveyRepository;
    @Autowired
    private ShoppingCenterSurveyValidator shoppingCenterSurveyValidator;

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
    public ShoppingCenterTenant addOneTenantToSurvey(Integer surveyId, ShoppingCenterTenant request) {
        ShoppingCenterSurvey existing = findOneUsingSpecs(surveyId);
        getValidator().validateNotNull(existing);

        request.setSurvey(existing);
        existing.setUpdatedBy(securityService.getCurrentUser());

        return tenantService.addOne(request);
    }

    @Override
	public List<ShoppingCenterSurvey> findAllByShoppingCenterId(Integer shoppingCenterId) {
        return getEntityRepository().findAllByShoppingCenterId(shoppingCenterId);
    }

    @Override
	public List<ShoppingCenterSurvey> findAllByProjectId(Integer id) {
        return getEntityRepository().findAllByInteractionsProjectIdAndDeletedDateIsNull(id);
    }

    @Override
	public List<ShoppingCenterSurvey> findAllByShoppingCenterIdUsingSpecs(Integer shoppingCenterId) {
        return getEntityRepository().findAll(
                where(shoppingCenterIdEquals(shoppingCenterId))
                        .and(isNotDeleted())
        );
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
    public ShoppingCenterSurvey getUpdatedEntity(ShoppingCenterSurvey existing, ShoppingCenterSurvey request) {
        existing.setType(request.getType());
        existing.setNote(request.getNote());
        existing.setFlowHasLandscaping(request.getFlowHasLandscaping());
        existing.setFlowHasSpeedBumps(request.getFlowHasSpeedBumps());
        existing.setFlowHasStopSigns(request.getFlowHasStopSigns());
        existing.setFlowHasOneWayAisles(request.getFlowHasOneWayAisles());
        existing.setParkingHasAngledSpaces(request.getParkingHasAngledSpaces());
        existing.setParkingHasParkingHog(request.getParkingHasParkingHog());
        existing.setParkingIsPoorlyLit(request.getParkingIsPoorlyLit());
        existing.setParkingSpaceCount(request.getParkingSpaceCount());
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
        // TODO - Handel Accesses, Interactions, Tenants
    }

    @Override
    public void handleAssociationsOnCreation(ShoppingCenterSurvey request) {
        // TODO - Handel Accesses, Interactions, Tenants
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
