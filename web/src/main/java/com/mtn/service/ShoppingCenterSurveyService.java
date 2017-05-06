package com.mtn.service;

import com.mtn.model.domain.ShoppingCenterAccess;
import com.mtn.model.domain.ShoppingCenterSurvey;
import com.mtn.model.domain.ShoppingCenterTenant;
import com.mtn.model.domain.UserProfile;
import com.mtn.repository.ShoppingCenterSurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mtn.repository.specification.ShoppingCenterSurveySpecifications.idEquals;
import static com.mtn.repository.specification.ShoppingCenterSurveySpecifications.isNotDeleted;
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
    private UserProfileService userProfileService;
    @Autowired
    private ShoppingCenterSurveyRepository surveyRepository;

    @Transactional
    public ShoppingCenterSurvey addOne(ShoppingCenterSurvey request) {
        validateForInsert(request);

        UserProfile systemAdministrator = userProfileService.findSystemAdministrator();
        request.setCreatedBy(systemAdministrator);
        request.setUpdatedBy(systemAdministrator);

        return surveyRepository.save(request);
    }

    @Transactional
    public ShoppingCenterAccess addOneAccessToSurvey(Integer surveyId, ShoppingCenterAccess request) {
        ShoppingCenterSurvey existing = findOneUsingSpecs(surveyId);
        validateNotNull(existing);

        request.setSurvey(existing);

        return accessService.addOne(request);
    }

    @Transactional
    public ShoppingCenterTenant addOneTenantToSurvey(Integer surveyId, ShoppingCenterTenant request) {
        ShoppingCenterSurvey existing = findOneUsingSpecs(surveyId);
        validateNotNull(existing);

        request.setSurvey(existing);

        return tenantService.addOne(request);
    }

    @Transactional
    public void deleteOne(Integer id) {
        ShoppingCenterSurvey existing = findOneUsingSpecs(id);
        if (existing == null) {
            throw new IllegalArgumentException("No ShoppingCenterSurvey found with this id");
        }

        existing.setDeletedBy(userProfileService.findSystemAdministrator());
    }

    public List<ShoppingCenterSurvey> findAllByShoppingCenterId(Integer shoppingCenterId) {
        return surveyRepository.findAllByShoppingCenterId(shoppingCenterId);
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

        existing.setHasAngledSpaces(request.getHasAngledSpaces());
        existing.setHasParkingHog(request.getHasParkingHog());
        existing.setHasSpeedBumps(request.getHasSpeedBumps());
        existing.setUpdatedBy(userProfileService.findSystemAdministrator());

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
    public void validateDoesNotExist(ShoppingCenterSurvey object) {
        //No unique contraints to enforce
    }
}
