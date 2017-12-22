package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ShoppingCenterSurvey;

import java.time.LocalDateTime;

/**
 * Created by Allen on 5/4/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleShoppingCenterSurveyView {

    private Integer id;
    private Boolean flowHasLandscaping = false;
    private Boolean flowHasStopSigns = false;
    private Boolean flowHasOneWayAisles = false;
    private Boolean flowHasSpeedBumps = false;
    private Boolean parkingHasAngledSpaces = false;
    private Boolean parkingHasParkingHog = false;
    private Boolean parkingIsPoorlyLit = false;
    private Integer parkingSpaceCount;
    private Integer tenantOccupiedCount;
    private Integer tenantVacantCount;
    private Double sqFtPercentOccupied;
    private String type;
    private String note;
    private Integer legacyCasingId;
    private Integer version;
    private SimpleUserProfileView createdBy;
    private LocalDateTime createdDate;
    private SimpleUserProfileView updatedBy;
    private LocalDateTime updatedDate;


    public SimpleShoppingCenterSurveyView() {
    }

    public SimpleShoppingCenterSurveyView(ShoppingCenterSurvey survey) {
        this.id = survey.getId();
        this.flowHasLandscaping = survey.getFlowHasLandscaping();
        this.flowHasStopSigns = survey.getFlowHasStopSigns();
        this.flowHasOneWayAisles = survey.getFlowHasOneWayAisles();
        this.flowHasSpeedBumps = survey.getFlowHasSpeedBumps();
        this.parkingHasAngledSpaces = survey.getParkingHasAngledSpaces();
        this.parkingHasParkingHog = survey.getParkingHasParkingHog();
        this.parkingIsPoorlyLit = survey.getParkingIsPoorlyLit();
        this.parkingSpaceCount = survey.getParkingSpaceCount();
        this.tenantOccupiedCount = survey.getTenantOccupiedCount();
        this.tenantVacantCount = survey.getTenantVacantCount();
        this.sqFtPercentOccupied = survey.getSqFtPercentOccupied();
        this.type = survey.getType();
        this.note = survey.getNote();
        this.legacyCasingId = survey.getLegacyCasingId();
        this.version = survey.getVersion();
        this.createdBy = new SimpleUserProfileView(survey.getCreatedBy());
        this.createdDate = survey.getCreatedDate();
        this.updatedBy = new SimpleUserProfileView(survey.getUpdatedBy());
        this.updatedDate = survey.getUpdatedDate();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Boolean getFlowHasSpeedBumps() {
        return flowHasSpeedBumps;
    }

    public void setFlowHasSpeedBumps(Boolean flowHasSpeedBumps) {
        this.flowHasSpeedBumps = flowHasSpeedBumps;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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
