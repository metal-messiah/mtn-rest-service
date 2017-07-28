package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ShoppingCenterTenant;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCenterTenantView extends SimpleShoppingCenterTenantView {

    private SimpleUserProfileView createdBy;
    private LocalDateTime createdDate;
    private SimpleUserProfileView updatedBy;
    private LocalDateTime updatedDate;

    public ShoppingCenterTenantView() {
        super();
    }

    public ShoppingCenterTenantView(ShoppingCenterTenant tenant) {
        super(tenant);

        this.createdBy = new SimpleUserProfileView(tenant.getCreatedBy());
        this.createdDate = tenant.getCreatedDate();
        this.updatedBy = new SimpleUserProfileView(tenant.getUpdatedBy());
        this.updatedDate = tenant.getUpdatedDate();
    }

    public SimpleUserProfileView getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(SimpleUserProfileView createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public SimpleUserProfileView getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(SimpleUserProfileView updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
}
