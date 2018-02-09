package com.mtn.model.domain;

import javax.persistence.*;

/**
 * Created by Allen on 5/4/2017.
 */
@Entity
@Table
public class ShoppingCenterAccess extends AuditingEntity implements Identifiable {

    private Integer id;
    private ShoppingCenterSurvey survey;
    private Boolean hasLeftIn = false;
    private Boolean hasLeftOut = false;
    private Boolean hasTrafficLight = false;
    private Boolean hasOneWayRoad = false;
    private Boolean hasRightIn = false;
    private Boolean hasRightOut = false;
    private Integer legacyCasingId;

    @Id
    @GeneratedValue
    @Column(name = "shopping_center_access_id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "shopping_center_survey_id")
    public ShoppingCenterSurvey getSurvey() {
        return survey;
    }

    public void setSurvey(ShoppingCenterSurvey survey) {
        this.survey = survey;
    }

    public Boolean getHasLeftIn() {
        return hasLeftIn;
    }

    public void setHasLeftIn(Boolean hasLeftIn) {
        this.hasLeftIn = hasLeftIn;
    }

    public Boolean getHasLeftOut() {
        return hasLeftOut;
    }

    public void setHasLeftOut(Boolean hasLeftOut) {
        this.hasLeftOut = hasLeftOut;
    }

    public Boolean getHasTrafficLight() {
        return hasTrafficLight;
    }

    public void setHasTrafficLight(Boolean hasTrafficLight) {
        this.hasTrafficLight = hasTrafficLight;
    }

    public Boolean getHasOneWayRoad() {
        return hasOneWayRoad;
    }

    public void setHasOneWayRoad(Boolean hasOneWayRoad) {
        this.hasOneWayRoad = hasOneWayRoad;
    }

    public Boolean getHasRightIn() {
        return hasRightIn;
    }

    public void setHasRightIn(Boolean hasRightIn) {
        this.hasRightIn = hasRightIn;
    }

    public Boolean getHasRightOut() {
        return hasRightOut;
    }

    public void setHasRightOut(Boolean hasRightOut) {
        this.hasRightOut = hasRightOut;
    }

    public Integer getLegacyCasingId() {
        return legacyCasingId;
    }

    public void setLegacyCasingId(Integer legacyCasingId) {
        this.legacyCasingId = legacyCasingId;
    }

}
