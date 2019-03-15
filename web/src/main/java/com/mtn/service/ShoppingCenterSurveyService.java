package com.mtn.service;

import com.mtn.model.domain.ShoppingCenter;
import com.mtn.model.domain.ShoppingCenterSurvey;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.utils.ShoppingCenterUtil;
import com.mtn.model.view.ShoppingCenterSurveyView;
import com.mtn.repository.ShoppingCenterSurveyRepository;
import com.mtn.validators.ShoppingCenterSurveyValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ShoppingCenterSurveyService extends EntityService<ShoppingCenterSurvey, ShoppingCenterSurveyView> {

    @Autowired
    public ShoppingCenterSurveyService(SecurityService securityService,
                                       ShoppingCenterSurveyRepository repository,
                                       ShoppingCenterSurveyValidator validator) {
        super(securityService, repository, validator, ShoppingCenterSurvey::new);
    }

    @Transactional
    public ShoppingCenterSurvey getCloneOfLatestForShoppingCenter(ShoppingCenter shoppingCenter, LocalDateTime dateTime) {
        UserProfile currentUser = securityService.getCurrentUser();

        ShoppingCenterSurvey survey = ShoppingCenterUtil
                .getLatestSurveyAsOfDateTime(shoppingCenter, LocalDateTime.now())
                .map(ShoppingCenterSurvey::new).orElseGet(ShoppingCenterSurvey::new);
        survey.setSurveyDate(dateTime);
        survey.setShoppingCenter(shoppingCenter);
        survey.setCreatedBy(currentUser);
        survey.setUpdatedBy(currentUser);

        survey.getAccesses().forEach(access -> {
            access.setCreatedBy(currentUser);
            access.setUpdatedBy(currentUser);
        });

        survey.getTenants().forEach(tenant -> {
            tenant.setCreatedBy(currentUser);
            tenant.setUpdatedBy(currentUser);
        });

        return survey;
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
        // Do Nothing
    }
}
