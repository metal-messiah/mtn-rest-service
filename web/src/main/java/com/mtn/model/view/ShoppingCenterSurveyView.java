package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ShoppingCenterSurvey;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 5/4/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCenterSurveyView extends SimpleShoppingCenterSurveyView {

    private SimpleUserProfileView createdBy;
    private LocalDateTime createdDate;
    private SimpleUserProfileView updatedBy;
    private LocalDateTime updatedDate;
    protected SimpleShoppingCenterView shoppingCenter;

    protected List<SimpleShoppingCenterAccessView> accesses = new ArrayList<>();
    protected List<SimpleShoppingCenterTenantView> tenants = new ArrayList<>();

    public ShoppingCenterSurveyView() {
    }

    public ShoppingCenterSurveyView(ShoppingCenterSurvey survey) {
        super(survey);

        this.createdBy = new SimpleUserProfileView(survey.getCreatedBy());
        this.createdDate = survey.getCreatedDate();
        this.updatedBy = new SimpleUserProfileView(survey.getUpdatedBy());
        this.updatedDate = survey.getUpdatedDate();

        if (survey.getShoppingCenter() != null) {
            this.shoppingCenter = new SimpleShoppingCenterView(survey.getShoppingCenter());
        }

        this.accesses = survey.getAccesses().stream().map(SimpleShoppingCenterAccessView::new).collect(Collectors.toList());
        this.tenants = survey.getTenants().stream().map(SimpleShoppingCenterTenantView::new).collect(Collectors.toList());
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

    public SimpleShoppingCenterView getShoppingCenter() {
        return shoppingCenter;
    }

    public void setShoppingCenter(SimpleShoppingCenterView shoppingCenter) {
        this.shoppingCenter = shoppingCenter;
    }

    public List<SimpleShoppingCenterAccessView> getAccesses() {
        return accesses;
    }

    public void setAccesses(List<SimpleShoppingCenterAccessView> accesses) {
        this.accesses = accesses;
    }

    public List<SimpleShoppingCenterTenantView> getTenants() {
        return tenants;
    }

    public void setTenants(List<SimpleShoppingCenterTenantView> tenants) {
        this.tenants = tenants;
    }
}
