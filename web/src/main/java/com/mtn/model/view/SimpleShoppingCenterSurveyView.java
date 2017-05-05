package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ShoppingCenterSurvey;

/**
 * Created by Allen on 5/4/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleShoppingCenterSurveyView {

    protected Integer id;
    private Boolean hasAngledSpaces = false;
    private Boolean hasParkingHog = false;
    private Boolean hasSpeedBumps = false;

    public SimpleShoppingCenterSurveyView() {
    }

    public SimpleShoppingCenterSurveyView(ShoppingCenterSurvey survey) {
        this.id = survey.getId();
        this.hasAngledSpaces = survey.getHasAngledSpaces();
        this.hasParkingHog = survey.getHasParkingHog();
        this.hasSpeedBumps = survey.getHasParkingHog();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getHasAngledSpaces() {
        return hasAngledSpaces;
    }

    public void setHasAngledSpaces(Boolean hasAngledSpaces) {
        this.hasAngledSpaces = hasAngledSpaces;
    }

    public Boolean getHasParkingHog() {
        return hasParkingHog;
    }

    public void setHasParkingHog(Boolean hasParkingHog) {
        this.hasParkingHog = hasParkingHog;
    }

    public Boolean getHasSpeedBumps() {
        return hasSpeedBumps;
    }

    public void setHasSpeedBumps(Boolean hasSpeedBumps) {
        this.hasSpeedBumps = hasSpeedBumps;
    }
}
