package com.mtn.service;

import com.mtn.model.domain.ShoppingCenter;
import com.mtn.model.domain.ShoppingCenterCasing;
import com.mtn.model.domain.ShoppingCenterSurvey;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.view.ShoppingCenterCasingView;
import com.mtn.repository.ShoppingCenterCasingRepository;
import com.mtn.validators.ShoppingCenterCasingValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ShoppingCenterCasingService extends EntityService<ShoppingCenterCasing, ShoppingCenterCasingView> {

    private final ShoppingCenterSurveyService shoppingCenterSurveyService;

    @Autowired
    public ShoppingCenterCasingService(SecurityService securityService,
                                       ShoppingCenterCasingRepository repository,
                                       ShoppingCenterCasingValidator validator, ShoppingCenterSurveyService shoppingCenterSurveyService) {
        super(securityService, repository, validator, ShoppingCenterCasing::new);
        this.shoppingCenterSurveyService = shoppingCenterSurveyService;
    }

    @Transactional
    public ShoppingCenterCasing createNewForShoppingCenter(ShoppingCenter shoppingCenter, LocalDateTime dateTime) {
        UserProfile currentUser = securityService.getCurrentUser();

        ShoppingCenterCasing shoppingCenterCasing = new ShoppingCenterCasing();
        shoppingCenterCasing.setCasingDate(dateTime);
        shoppingCenterCasing.setShoppingCenter(shoppingCenter);
        shoppingCenterCasing.setCreatedBy(currentUser);
        shoppingCenterCasing.setUpdatedBy(currentUser);

        ShoppingCenterSurvey scSurvey = shoppingCenterSurveyService.getCloneOfLatestForShoppingCenter(shoppingCenter, dateTime);
        shoppingCenterCasing.setShoppingCenterSurvey(scSurvey);

        return shoppingCenterCasing;
    }

    @Override
    protected void setEntityAttributesFromRequest(ShoppingCenterCasing casing, ShoppingCenterCasingView request) {
        casing.setCasingDate(request.getCasingDate());
        casing.setNote(request.getNote());
        casing.setRatingBuildings(request.getRatingBuildings());
        casing.setRatingLighting(request.getRatingLighting());
        casing.setRatingSynergy(request.getRatingSynergy());
        casing.setRatingTenantBuildings(request.getRatingTenantBuildings());
    }

    @Override
    public void handleAssociationsOnDeletion(ShoppingCenterCasing casing) {
        // Do Nothing
    }
}
