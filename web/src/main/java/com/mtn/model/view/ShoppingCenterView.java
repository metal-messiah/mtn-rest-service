package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 4/23/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCenterView extends SimpleShoppingCenterView {

    private SimpleUserProfileView createdBy;
    private LocalDateTime createdDate;
    private SimpleUserProfileView updatedBy;
    private LocalDateTime updatedDate;

    private List<SimpleSiteView> sites = new ArrayList<>();

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

    public List<SimpleSiteView> getSites() {
        return sites;
    }

    public void setSites(List<SimpleSiteView> sites) {
        this.sites = sites;
    }
}
