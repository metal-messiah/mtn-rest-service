package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ShoppingCenterSurvey;
import com.mtn.model.simpleView.SimpleShoppingCenterTenantView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 5/4/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCenterSurveyView extends AuditingEntityView {

    private LocalDateTime surveyDate;
    private String note;
    private Boolean flowHasOneWayAisles;
    private String flowRating;
    private Integer tenantOccupiedCount;
    private Integer tenantVacantCount;
    private Double sqFtPercentOccupied;
    private Integer legacyCasingId;

    private List<ShoppingCenterAccessView> accesses = new ArrayList<>();
    private List<SimpleShoppingCenterTenantView> tenants = new ArrayList<>();

    public ShoppingCenterSurveyView(ShoppingCenterSurvey survey) {
        super(survey);

        this.surveyDate = survey.getSurveyDate();
        this.note = survey.getNote();
        this.flowHasOneWayAisles = survey.getFlowHasOneWayAisles();
        this.flowRating = survey.getFlowRating();
        this.tenantOccupiedCount = survey.getTenantOccupiedCount();
        this.tenantVacantCount = survey.getTenantVacantCount();
        this.sqFtPercentOccupied = survey.getSqFtPercentOccupied();
        this.legacyCasingId = survey.getLegacyCasingId();

        if (survey.getAccesses() != null) {
            this.accesses = survey.getAccesses().stream()
                    .filter(access -> access.getDeletedDate() == null)
                    .map(ShoppingCenterAccessView::new)
                    .collect(Collectors.toList());
        }
        if (survey.getTenants() != null) {
            this.tenants = survey.getTenants().stream()
                    .filter(tenant -> tenant.getDeletedDate() == null)
                    .map(SimpleShoppingCenterTenantView::new)
                    .collect(Collectors.toList());
        }
    }

    public LocalDateTime getSurveyDate() {
        return surveyDate;
    }

    public String getNote() {
        return note;
    }

    public Boolean getFlowHasOneWayAisles() {
        return flowHasOneWayAisles;
    }

    public String getFlowRating() {
        return flowRating;
    }

    public Integer getTenantOccupiedCount() {
        return tenantOccupiedCount;
    }

    public Integer getTenantVacantCount() {
        return tenantVacantCount;
    }

    public Double getSqFtPercentOccupied() {
        return sqFtPercentOccupied;
    }

    public Integer getLegacyCasingId() {
        return legacyCasingId;
    }

    public List<ShoppingCenterAccessView> getAccesses() {
        return accesses;
    }

    public List<SimpleShoppingCenterTenantView> getTenants() {
        return tenants;
    }
}
