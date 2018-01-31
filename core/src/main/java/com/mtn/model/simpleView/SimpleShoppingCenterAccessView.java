package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ShoppingCenterAccess;

/**
 * Created by Allen on 5/4/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleShoppingCenterAccessView {

    private Integer id;
    private Boolean hasLeftIn;
    private Boolean hasLeftOut;
    private Boolean hasTrafficLight;
    private Boolean hasOneWayRoad;
    private Boolean hasRightIn;
    private Boolean hasRightOut;
    private Integer legacyCasingId;

    public SimpleShoppingCenterAccessView(ShoppingCenterAccess access) {
        this.id = access.getId();
        this.hasLeftIn = access.getHasLeftIn();
        this.hasLeftOut = access.getHasLeftOut();
        this.hasTrafficLight = access.getHasTrafficLight();
        this.hasOneWayRoad = access.getHasOneWayRoad();
        this.hasRightIn = access.getHasRightIn();
        this.hasRightOut = access.getHasRightOut();
        this.legacyCasingId = access.getLegacyCasingId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
