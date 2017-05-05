package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ShoppingCenter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<SimpleShoppingCenterSurveyView> surveys = new ArrayList<>();

    public ShoppingCenterView() {
    }

    public ShoppingCenterView(ShoppingCenter shoppingCenter) {
        super(shoppingCenter);

        this.createdBy = new SimpleUserProfileView(shoppingCenter.getCreatedBy());
        this.createdDate = shoppingCenter.getCreatedDate();
        this.updatedBy = new SimpleUserProfileView(shoppingCenter.getUpdatedBy());
        this.updatedDate = shoppingCenter.getUpdatedDate();

        this.sites = shoppingCenter.getSites().stream().map(SimpleSiteView::new).collect(Collectors.toList());
        this.surveys = shoppingCenter.getSurveys().stream().map(SimpleShoppingCenterSurveyView::new).collect(Collectors.toList());
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

    public List<SimpleSiteView> getSites() {
        return sites;
    }

    public void setSites(List<SimpleSiteView> sites) {
        this.sites = sites;
    }

    public List<SimpleShoppingCenterSurveyView> getSurveys() {
        return surveys;
    }

    public void setSurveys(List<SimpleShoppingCenterSurveyView> surveys) {
        this.surveys = surveys;
    }
}
