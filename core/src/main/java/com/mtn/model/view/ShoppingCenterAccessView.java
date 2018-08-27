package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.AccessType;
import com.mtn.model.domain.ShoppingCenterAccess;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCenterAccessView extends AuditingEntityView {

    private AccessType accessType;
    private Boolean hasLeftIn;
    private Boolean hasLeftOut;
    private Boolean hasTrafficLight;
    private Boolean oneWayRoad;
    private Boolean hasRightIn;
    private Boolean hasRightOut;
    private Integer legacyCasingId;

    public ShoppingCenterAccessView() {
    }

    public ShoppingCenterAccessView(ShoppingCenterAccess access) {
        super(access);

        this.accessType = access.getAccessType();
        this.hasLeftIn = access.getHasLeftIn();
        this.hasLeftOut = access.getHasLeftOut();
        this.hasTrafficLight = access.getHasTrafficLight();
        this.oneWayRoad = access.getOneWayRoad();
        this.hasRightIn = access.getHasRightIn();
        this.hasRightOut = access.getHasRightOut();
        this.legacyCasingId = access.getLegacyCasingId();
    }

    public AccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
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

    public Boolean getOneWayRoad() {
        return oneWayRoad;
    }

    public void setOneWayRoad(Boolean oneWayRoad) {
        this.oneWayRoad = oneWayRoad;
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
