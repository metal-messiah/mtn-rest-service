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

    public Boolean getHasLeftIn() {
        return hasLeftIn;
    }

    public Boolean getHasLeftOut() {
        return hasLeftOut;
    }

    public Boolean getHasTrafficLight() {
        return hasTrafficLight;
    }

    public Boolean getOneWayRoad() {
        return oneWayRoad;
    }

    public Boolean getHasRightIn() {
        return hasRightIn;
    }

    public Boolean getHasRightOut() {
        return hasRightOut;
    }

    public Integer getLegacyCasingId() {
        return legacyCasingId;
    }

}
