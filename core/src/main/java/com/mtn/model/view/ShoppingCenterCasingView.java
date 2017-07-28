package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ShoppingCenterCasing;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCenterCasingView extends SimpleShoppingCenterCasingView {

    private SimpleUserProfileView createdBy;
    private LocalDateTime createdDate;
    private SimpleUserProfileView updatedBy;
    private LocalDateTime updatedDate;

    public ShoppingCenterCasingView() {
        super();
    }

    public ShoppingCenterCasingView(ShoppingCenterCasing casing) {
        super(casing);

        this.createdBy = new SimpleUserProfileView(casing.getCreatedBy());
        this.createdDate = casing.getCreatedDate();
        this.updatedBy = new SimpleUserProfileView(casing.getUpdatedBy());
        this.updatedDate = casing.getUpdatedDate();
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
