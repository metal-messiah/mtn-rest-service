package com.mtn.model.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 5/4/2017.
 */
@Entity
@Table
public class ShoppingCenterSurvey extends AuditingEntity implements Identifiable {

    private Integer id;
    private ShoppingCenter shoppingCenter;
    private LocalDateTime surveyDate;
    private String centerType;
    private String note;
    private Boolean flowHasLandscaping = false;
    private Boolean flowHasSpeedBumps = false;
    private Boolean flowHasStopSigns = false;
    private Boolean flowHasOneWayAisles = false;
    private Boolean parkingHasAngledSpaces = false;
    private Boolean parkingHasParkingHog = false;
    private Boolean parkingIsPoorlyLit = false;
    private Integer parkingSpaceCount;
    private Integer tenantOccupiedCount;
    private Integer tenantVacantCount;
    private Double sqFtPercentOccupied;
    private Integer legacyCasingId;

    private List<ShoppingCenterAccess> accesses = new ArrayList<>();
    private List<Interaction> interactions = new ArrayList<>();
    private List<ShoppingCenterTenant> tenants = new ArrayList<>();

    @Id
    @GeneratedValue
    @Column(name = "shopping_center_survey_id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "shopping_center_id")
    public ShoppingCenter getShoppingCenter() {
        return shoppingCenter;
    }

    public void setShoppingCenter(ShoppingCenter shoppingCenter) {
        this.shoppingCenter = shoppingCenter;
    }

    public Boolean getParkingHasAngledSpaces() {
        return parkingHasAngledSpaces;
    }

    public void setParkingHasAngledSpaces(Boolean parkingHasAngledSpaces) {
        this.parkingHasAngledSpaces = parkingHasAngledSpaces;
    }

    public Boolean getParkingHasParkingHog() {
        return parkingHasParkingHog;
    }

    public void setParkingHasParkingHog(Boolean parkingHasParkingHog) {
        this.parkingHasParkingHog = parkingHasParkingHog;
    }

    public Boolean getFlowHasSpeedBumps() {
        return flowHasSpeedBumps;
    }

    public void setFlowHasSpeedBumps(Boolean flowHasSpeedBumps) {
        this.flowHasSpeedBumps = flowHasSpeedBumps;
    }

    @OneToMany(mappedBy = "survey", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public List<ShoppingCenterAccess> getAccesses() {
        return accesses;
    }

    public void setAccesses(List<ShoppingCenterAccess> accesses) {
        this.accesses = accesses;
    }

    @OneToMany(mappedBy = "survey", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public List<ShoppingCenterTenant> getTenants() {
        return tenants;
    }

    public void setTenants(List<ShoppingCenterTenant> tenants) {
        this.tenants = tenants;
    }

    public Boolean getFlowHasLandscaping() {
        return flowHasLandscaping;
    }

    public void setFlowHasLandscaping(Boolean flowHasLandscaping) {
        this.flowHasLandscaping = flowHasLandscaping;
    }

    public Boolean getFlowHasStopSigns() {
        return flowHasStopSigns;
    }

    public void setFlowHasStopSigns(Boolean flowHasStopSigns) {
        this.flowHasStopSigns = flowHasStopSigns;
    }

    public Boolean getFlowHasOneWayAisles() {
        return flowHasOneWayAisles;
    }

    public void setFlowHasOneWayAisles(Boolean flowHasOneWayAisles) {
        this.flowHasOneWayAisles = flowHasOneWayAisles;
    }

    public Boolean getParkingIsPoorlyLit() {
        return parkingIsPoorlyLit;
    }

    public void setParkingIsPoorlyLit(Boolean parkingIsPoorlyLit) {
        this.parkingIsPoorlyLit = parkingIsPoorlyLit;
    }

    public Integer getParkingSpaceCount() {
        return parkingSpaceCount;
    }

    public void setParkingSpaceCount(Integer parkingSpaceCount) {
        this.parkingSpaceCount = parkingSpaceCount;
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

    public String getCenterType() {
        return centerType;
    }

    public void setCenterType(String centerType) {
        this.centerType = centerType;
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

    @OneToMany(mappedBy = "shoppingCenterSurvey")
    public List<Interaction> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<Interaction> interactions) {
        this.interactions = interactions;
    }

    @Column(name = "shopping_center_survey_date")
    public LocalDateTime getSurveyDate() {
        return surveyDate;
    }

    public void setSurveyDate(LocalDateTime surveyDate) {
        this.surveyDate = surveyDate;
    }
}
