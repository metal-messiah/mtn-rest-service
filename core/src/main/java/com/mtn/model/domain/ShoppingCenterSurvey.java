package com.mtn.model.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table
@AttributeOverride(name="id", column=@Column(name="shopping_center_survey_id"))
public class ShoppingCenterSurvey extends AuditingEntity {

    private LocalDateTime surveyDate;
    private String note;
    private Boolean flowHasOneWayAisles = false;
    private String flowRating;
    private Integer tenantOccupiedCount;
    private Integer tenantVacantCount;
    private Double sqFtPercentOccupied;
    private Integer legacyCasingId;

    private ShoppingCenter shoppingCenter;
    private List<ShoppingCenterCasing> shoppingCenterCasings = new ArrayList<>();

    private List<ShoppingCenterAccess> accesses = new ArrayList<>();
    private List<ShoppingCenterTenant> tenants = new ArrayList<>();

    public ShoppingCenterSurvey() {
    }

    public ShoppingCenterSurvey(ShoppingCenterSurvey shoppingCenterSurvey) {
        this.surveyDate = shoppingCenterSurvey.surveyDate;
        this.note = shoppingCenterSurvey.note;
        this.flowHasOneWayAisles = shoppingCenterSurvey.flowHasOneWayAisles;
        this.flowRating = shoppingCenterSurvey.flowRating;
        this.tenantOccupiedCount = shoppingCenterSurvey.tenantOccupiedCount;
        this.tenantVacantCount = shoppingCenterSurvey.tenantVacantCount;
        this.sqFtPercentOccupied = shoppingCenterSurvey.sqFtPercentOccupied;

        this.accesses = shoppingCenterSurvey.getAccesses().stream()
                .filter(access -> access.getDeletedDate() == null) // exclude deleted ones from copy
                .map(ShoppingCenterAccess::new) // Create new object
                .collect(Collectors.toList());
        this.accesses.forEach(access -> access.setSurvey(this));
        this.tenants = shoppingCenterSurvey.getTenants().stream()
                .filter(tenant -> tenant.getDeletedDate() == null)
                .map(ShoppingCenterTenant::new)
                .collect(Collectors.toList());
        this.tenants.forEach(tenant -> tenant.setSurvey(this));
    }

    @ManyToOne
    @JoinColumn(name = "shopping_center_id")
    public ShoppingCenter getShoppingCenter() {
        return shoppingCenter;
    }

    public void setShoppingCenter(ShoppingCenter shoppingCenter) {
        this.shoppingCenter = shoppingCenter;
    }

    @OneToMany(mappedBy = "survey", cascade = {CascadeType.PERSIST})
    public List<ShoppingCenterAccess> getAccesses() {
        return accesses;
    }

    public void setAccesses(List<ShoppingCenterAccess> accesses) {
        this.accesses = accesses;
    }

    public void addAccess(ShoppingCenterAccess access) {
        this.getAccesses().add(access);
        access.setSurvey(this);
    }

    @OneToMany(mappedBy = "survey", cascade = {CascadeType.PERSIST})
    public List<ShoppingCenterTenant> getTenants() {
        return tenants;
    }

    public void setTenants(List<ShoppingCenterTenant> tenants) {
        this.tenants = tenants;
    }

    public void addTenant(ShoppingCenterTenant tenant) {
        this.getTenants().add(tenant);
        tenant.setSurvey(this);
    }

    public Boolean getFlowHasOneWayAisles() {
        return flowHasOneWayAisles;
    }

    public void setFlowHasOneWayAisles(Boolean flowHasOneWayAisles) {
        this.flowHasOneWayAisles = flowHasOneWayAisles;
    }

    public String getFlowRating() {
        return flowRating;
    }

    public void setFlowRating(String flowRating) {
        this.flowRating = flowRating;
    }

    public Integer getTenantOccupiedCount() {
        return tenantOccupiedCount;
    }

    public void setTenantOccupiedCount(Integer tenantOccupiedCount) {
        this.tenantOccupiedCount = tenantOccupiedCount;
    }

    public Integer getTenantVacantCount() {
        return tenantVacantCount;
    }

    public void setTenantVacantCount(Integer tenantVacantCount) {
        this.tenantVacantCount = tenantVacantCount;
    }

    public Double getSqFtPercentOccupied() {
        return sqFtPercentOccupied;
    }

    public void setSqFtPercentOccupied(Double sqFtPercentOccupied) {
        this.sqFtPercentOccupied = sqFtPercentOccupied;
    }

    @Column(name = "shopping_center_survey_note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getLegacyCasingId() {
        return legacyCasingId;
    }

    public void setLegacyCasingId(Integer legacyCasingId) {
        this.legacyCasingId = legacyCasingId;
    }

    @Column(name = "shopping_center_survey_date")
    public LocalDateTime getSurveyDate() {
        return surveyDate;
    }

    public void setSurveyDate(LocalDateTime surveyDate) {
        this.surveyDate = surveyDate;
    }

    @OneToMany(mappedBy = "shoppingCenterSurvey")
    public List<ShoppingCenterCasing> getShoppingCenterCasings() {
        return shoppingCenterCasings;
    }

    public void setShoppingCenterCasings(List<ShoppingCenterCasing> shoppingCenterCasings) {
        this.shoppingCenterCasings = shoppingCenterCasings;
    }
}
