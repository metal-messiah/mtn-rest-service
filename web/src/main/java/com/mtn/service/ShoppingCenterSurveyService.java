package com.mtn.service;

import com.mtn.model.domain.ShoppingCenter;
import com.mtn.model.domain.ShoppingCenterSurvey;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.utils.ShoppingCenterUtil;
import com.mtn.model.view.ShoppingCenterSurveyView;
import com.mtn.repository.ShoppingCenterSurveyRepository;
import com.mtn.repository.specification.ShoppingCenterSurveySpecifications;
import com.mtn.validators.ShoppingCenterSurveyValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.Specifications.where;

@Service
public class ShoppingCenterSurveyService extends EntityServiceImpl<ShoppingCenterSurvey, ShoppingCenterSurveyView> {

    @Autowired
    public ShoppingCenterSurveyService(EntityServiceDependencies services,
                                       ShoppingCenterSurveyRepository repository,
                                       ShoppingCenterSurveyValidator validator) {
        super(services, repository, validator);
    }

    public List<ShoppingCenterSurvey> findAllByShoppingCenterIdUsingSpecs(Integer shoppingCenterId) {
        return this.repository.findAll(
                where(ShoppingCenterSurveySpecifications.shoppingCenterIdEquals(shoppingCenterId))
                        .and(ShoppingCenterSurveySpecifications.isNotDeleted())
        );
    }

    @Transactional
    public ShoppingCenterSurvey getCloneOfLatestForShoppingCenter(ShoppingCenter shoppingCenter, LocalDateTime dateTime) {
        ShoppingCenterSurvey survey = ShoppingCenterUtil
                .getLatestSurveyAsOfDateTime(shoppingCenter, LocalDateTime.now())
                .map(ShoppingCenterSurvey::new).orElseGet(ShoppingCenterSurvey::new);
        survey.setSurveyDate(dateTime);
        survey.setShoppingCenter(shoppingCenter);
        return survey;
    }

    @Override
    protected ShoppingCenterSurvey createNewEntity() {
        return new ShoppingCenterSurvey();
    }

    @Override
    protected void setEntityAttributesFromRequest(ShoppingCenterSurvey survey, ShoppingCenterSurveyView request) {
        survey.setSurveyDate(request.getSurveyDate());
        survey.setNote(request.getNote());
        survey.setFlowHasOneWayAisles(request.getFlowHasOneWayAisles());
        survey.setFlowRating(request.getFlowRating());
        survey.setTenantOccupiedCount(request.getTenantOccupiedCount());
        survey.setTenantVacantCount(request.getTenantVacantCount());
        survey.setSqFtPercentOccupied(request.getSqFtPercentOccupied());
    }

    @Override
    public void handleAssociationsOnDeletion(ShoppingCenterSurvey survey) {
        UserProfile currentUser = this.securityService.getCurrentUser();
        survey.setShoppingCenter(null);
        survey.getTenants().forEach(tenant -> {
            tenant.setDeletedBy(currentUser);
            tenant.setDeletedDate(LocalDateTime.now());
        });
        survey.getAccesses().forEach(access -> {
            access.setDeletedBy(currentUser);
            access.setDeletedDate(LocalDateTime.now());
        });
    }
}
