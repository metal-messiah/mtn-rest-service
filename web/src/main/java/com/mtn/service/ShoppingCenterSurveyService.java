package com.mtn.service;

import com.mtn.exception.VersionConflictException;
import com.mtn.model.domain.ShoppingCenterAccess;
import com.mtn.model.domain.ShoppingCenterSurvey;
import com.mtn.model.domain.ShoppingCenterTenant;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.view.ShoppingCenterSurveyView;
import com.mtn.repository.ShoppingCenterSurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mtn.repository.specification.ShoppingCenterSurveySpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 5/6/2017.
 */
@Service
public class ShoppingCenterSurveyService extends ValidatingDataService<ShoppingCenterSurvey> {

    @Autowired
    private ShoppingCenterAccessService accessService;
    @Autowired
    private ShoppingCenterTenantService tenantService;
    @Autowired
    private ShoppingCenterSurveyRepository surveyRepository;
    @Autowired
    private SecurityService securityService;

    @Transactional
    public ShoppingCenterSurvey addOne(ShoppingCenterSurvey request) {
        validateForInsert(request);

        UserProfile currentUser = securityService.getCurrentUser();
        request.setCreatedBy(currentUser);
        request.setUpdatedBy(currentUser);

        return surveyRepository.save(request);
    }

    @Transactional
    public ShoppingCenterAccess addOneAccessToSurvey(Integer surveyId, ShoppingCenterAccess request) {
        ShoppingCenterSurvey existing = findOneUsingSpecs(surveyId);
        validateNotNull(existing);

        request.setSurvey(existing);
        existing.setUpdatedBy(securityService.getCurrentUser());

        return accessService.addOne(request);
    }

    @Transactional
    public ShoppingCenterTenant addOneTenantToSurvey(Integer surveyId, ShoppingCenterTenant request) {
        ShoppingCenterSurvey existing = findOneUsingSpecs(surveyId);
        validateNotNull(existing);

        request.setSurvey(existing);
        existing.setUpdatedBy(securityService.getCurrentUser());

        return tenantService.addOne(request);
    }

    @Transactional
    public void deleteOne(Integer id) {
        ShoppingCenterSurvey existing = findOneUsingSpecs(id);
        if (existing == null) {
            throw new IllegalArgumentException("No ShoppingCenterSurvey found with this id");
        }

        existing.setDeletedBy(securityService.getCurrentUser());
    }

    public List<ShoppingCenterSurvey> findAllByShoppingCenterId(Integer shoppingCenterId) {
        return surveyRepository.findAllByShoppingCenterId(shoppingCenterId);
    }

    public List<ShoppingCenterSurvey> findAllByProjectId(Integer id) {
        return surveyRepository.findAllByInteractionsProjectIdAndDeletedDateIsNull(id);
    }

    public List<ShoppingCenterSurvey> findAllByShoppingCenterIdUsingSpecs(Integer shoppingCenterId) {
        return surveyRepository.findAll(
                where(shoppingCenterIdEquals(shoppingCenterId))
                        .and(isNotDeleted())
        );
    }

    public ShoppingCenterSurvey findOne(Integer id) {
        return surveyRepository.findOne(id);
    }

    public ShoppingCenterSurvey findOneUsingSpecs(Integer id) {
        return surveyRepository.findOne(
                where(idEquals(id))
                        .and(isNotDeleted())
        );
    }

    @Transactional
    public ShoppingCenterSurvey updateOne(Integer id, ShoppingCenterSurvey request) {
        validateNotNull(request);
        validateForUpdate(request);

        ShoppingCenterSurvey existing = findOneUsingSpecs(id);
        if (existing == null) {
            throw new IllegalArgumentException("No ShoppingCenterSurvey found with this id");
        }
        if (!request.getVersion().equals(existing.getVersion())) {
            throw new VersionConflictException(new ShoppingCenterSurveyView(existing));
        }

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
        existing.setUpdatedBy(securityService.getCurrentUser());

        return existing;
    }

    @Override
    public String getEntityName() {
        return "ShoppingCenterSurvey";
    }

    @Override
    public void validateForInsert(ShoppingCenterSurvey object) {
        super.validateForInsert(object);

        if (object.getShoppingCenter() == null) {
            throw new IllegalStateException("ShoppingCenterSurvey was not mapped to ShoppingCenter before saving!");
        }

        object.getAccesses().forEach(accessService::validateForInsert);
        object.getTenants().forEach(tenantService::validateForInsert);
    }

    @Override
    public void validateBusinessRules(ShoppingCenterSurvey object) {
        //No special rules at this time
    }

    @Override
    public void validateUnique(ShoppingCenterSurvey object) {
        //No unique contraints to enforce
    }
}
