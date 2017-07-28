package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ShoppingCenterAccess;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCenterAccessView extends SimpleShoppingCenterAccessView {

    private SimpleUserProfileView createdBy;
    private LocalDateTime createdDate;
    private SimpleUserProfileView updatedBy;
    private LocalDateTime updatedDate;

    public ShoppingCenterAccessView() {
        super();
    }

    public ShoppingCenterAccessView(ShoppingCenterAccess access) {
        super(access);

        this.createdBy = new SimpleUserProfileView(access.getCreatedBy());
        this.createdDate = access.getCreatedDate();
        this.updatedBy = new SimpleUserProfileView(access.getUpdatedBy());
        this.updatedDate = access.getUpdatedDate();
    }

    @Override
    public SimpleUserProfileView getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(SimpleUserProfileView createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    @Override
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public SimpleUserProfileView getUpdatedBy() {
        return updatedBy;
    }

    @Override
    public void setUpdatedBy(SimpleUserProfileView updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    @Override
    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
}
