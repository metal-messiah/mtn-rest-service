package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Site;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 4/25/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SiteView extends SimpleSiteView {

    private SimpleShoppingCenterView shoppingCenter;
    private SimpleUserProfileView createdBy;
    private LocalDateTime createdDate;
    private SimpleUserProfileView updatedBy;
    private LocalDateTime updatedDate;

    private List<SimpleStoreView> stores = new ArrayList<>();

    public SiteView() {
    }

    public SiteView(Site site) {
        super(site);

        this.shoppingCenter = new SimpleShoppingCenterView(site.getShoppingCenter());
        this.createdBy = new SimpleUserProfileView(site.getCreatedBy());
        this.createdDate = site.getCreatedDate();
        this.updatedBy = new SimpleUserProfileView(site.getUpdatedBy());
        this.updatedDate = site.getUpdatedDate();

        this.stores = site.getStores().stream().map(SimpleStoreView::new).collect(Collectors.toList());
    }

    public SimpleShoppingCenterView getShoppingCenter() {
        return shoppingCenter;
    }

    public void setShoppingCenter(SimpleShoppingCenterView shoppingCenter) {
        this.shoppingCenter = shoppingCenter;
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

    public List<SimpleStoreView> getStores() {
        return stores;
    }

    public void setStores(List<SimpleStoreView> stores) {
        this.stores = stores;
    }
}