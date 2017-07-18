package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ShoppingCenterTenant;

import java.time.LocalDateTime;

/**
 * Created by Allen on 5/4/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleShoppingCenterTenantView {

    protected Integer id;
    protected String name;
    protected Boolean isAnchor = false;
    protected Boolean isOutparcel = false;
    protected Integer sqft;
    protected Integer legacyCasingId;
    protected Integer version;
    protected SimpleUserProfileView createdBy;
    protected LocalDateTime createdDate;
    protected SimpleUserProfileView updatedBy;
    protected LocalDateTime updatedDate;

    public SimpleShoppingCenterTenantView() {
    }

    public SimpleShoppingCenterTenantView(ShoppingCenterTenant tenant) {
        this.id = tenant.getId();
        this.name = tenant.getName();
        this.isAnchor = tenant.getIsAnchor();
        this.isOutparcel = tenant.getIsOutparcel();
        this.sqft = tenant.getSqft();
        this.legacyCasingId = tenant.getLegacyCasingId();
        this.version = tenant.getVersion();

        this.createdBy = new SimpleUserProfileView(tenant.getCreatedBy());
        this.createdDate = tenant.getCreatedDate();
        this.updatedBy = new SimpleUserProfileView(tenant.getUpdatedBy());
        this.updatedDate = tenant.getUpdatedDate();
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
