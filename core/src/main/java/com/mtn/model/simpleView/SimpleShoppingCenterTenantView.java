package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ShoppingCenterTenant;

import java.time.LocalDateTime;

/**
 * Created by Allen on 5/4/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleShoppingCenterTenantView {

    private Integer id;
    private String name;
    private Boolean isAnchor;
    private Boolean isOutparcel;
    private Integer sqft;
    private Integer legacyCasingId;

    public SimpleShoppingCenterTenantView(ShoppingCenterTenant tenant) {
        this.id = tenant.getId();
        this.name = tenant.getName();
        this.isAnchor = tenant.getIsAnchor();
        this.isOutparcel = tenant.getIsOutparcel();
        this.sqft = tenant.getSqft();
        this.legacyCasingId = tenant.getLegacyCasingId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsAnchor() {
        return isAnchor;
    }

    public void setIsAnchor(Boolean anchor) {
        isAnchor = anchor;
    }

    public Boolean getIsOutparcel() {
        return isOutparcel;
    }

    public void setIsOutparcel(Boolean outparcel) {
        isOutparcel = outparcel;
    }

    public Integer getSqft() {
        return sqft;
    }

    public void setSqft(Integer sqft) {
        this.sqft = sqft;
    }

    public Integer getLegacyCasingId() {
        return legacyCasingId;
    }

    public void setLegacyCasingId(Integer legacyCasingId) {
        this.legacyCasingId = legacyCasingId;
    }

}