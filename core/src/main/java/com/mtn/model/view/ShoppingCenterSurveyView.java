package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ShoppingCenterSurvey;
import com.mtn.model.simpleView.SimpleInteractionView;
import com.mtn.model.simpleView.SimpleShoppingCenterAccessView;
import com.mtn.model.simpleView.SimpleShoppingCenterSurveyView;
import com.mtn.model.simpleView.SimpleShoppingCenterTenantView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 5/4/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCenterSurveyView extends AuditingEntityView {

    private Integer id;
    private Boolean flowHasLandscaping;
    private Boolean flowHasStopSigns;
    private Boolean flowHasOneWayAisles;
    private Boolean flowHasSpeedBumps;
    private Boolean parkingHasAngledSpaces;
    private Boolean parkingHasParkingHog;
    private Boolean parkingIsPoorlyLit;
    private Integer parkingSpaceCount;
    private Integer tenantOccupiedCount;
    private Integer tenantVacantCount;
    private Double sqFtPercentOccupied;
    private String type;
    private String note;
    private Integer legacyCasingId;

    private List<SimpleInteractionView> interactions = new ArrayList<>();
    private List<SimpleShoppingCenterAccessView> accesses = new ArrayList<>();
    private List<SimpleShoppingCenterTenantView> tenants = new ArrayList<>();

    public ShoppingCenterSurveyView(ShoppingCenterSurvey survey) {
        super(survey);

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

        this.accesses = survey.getAccesses().stream().filter(access -> access.getDeletedDate() == null).map(SimpleShoppingCenterAccessView::new).collect(Collectors.toList());
        this.interactions = survey.getInteractions().stream().filter(interaction -> interaction.getDeletedDate() == null).map(SimpleInteractionView::new).collect(Collectors.toList());
        this.tenants = survey.getTenants().stream().filter(tenant -> tenant.getDeletedDate() == null).map(SimpleShoppingCenterTenantView::new).collect(Collectors.toList());
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

    public List<SimpleInteractionView> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<SimpleInteractionView> interactions) {
        this.interactions = interactions;
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
