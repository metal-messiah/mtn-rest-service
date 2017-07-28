package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.ModelType;
import com.mtn.model.domain.StoreModel;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleStoreModelView {

    private Integer id;
    private String mapkey;
    private Double curve;
    private Double pwta;
    private Double power;
    private Double fitAdjustedPower;
    private ModelType modelType;
    private LocalDateTime modelDate;
    private Integer version;
    private Integer legacyCasingId;
    private SimpleUserProfileView createdBy;
    private LocalDateTime createdDate;
    private SimpleUserProfileView updatedBy;
    private LocalDateTime updatedDate;

    public SimpleStoreModelView() {
    }

    public SimpleStoreModelView(StoreModel model) {
        this.id = model.getId();
        this.mapkey = model.getMapkey();
        this.curve = model.getCurve();
        this.pwta = model.getPwta();
        this.power = model.getPower();
        this.fitAdjustedPower = model.getFitAdjustedPower();
        this.modelType = model.getModelType();
        this.modelDate = model.getModelDate();
        this.version = model.getVersion();
        this.legacyCasingId = model.getLegacyCasingId();
        this.createdBy = new SimpleUserProfileView(model.getCreatedBy());
        this.createdDate = model.getCreatedDate();
        this.updatedBy = new SimpleUserProfileView(model.getUpdatedBy());
        this.updatedDate = model.getUpdatedDate();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMapkey() {
        return mapkey;
    }

    public void setMapkey(String mapkey) {
        this.mapkey = mapkey;
    }

    public Double getCurve() {
        return curve;
    }

    public void setCurve(Double curve) {
        this.curve = curve;
    }

    public Double getPwta() {
        return pwta;
    }

    public void setPwta(Double pwta) {
        this.pwta = pwta;
    }

    public Double getPower() {
        return power;
    }

    public void setPower(Double power) {
        this.power = power;
    }

    public Double getFitAdjustedPower() {
        return fitAdjustedPower;
    }

    public void setFitAdjustedPower(Double fitAdjustedPower) {
        this.fitAdjustedPower = fitAdjustedPower;
    }

    public ModelType getModelType() {
        return modelType;
    }

    public void setModelType(ModelType modelType) {
        this.modelType = modelType;
    }

    public LocalDateTime getModelDate() {
        return modelDate;
    }

    public void setModelDate(LocalDateTime modelDate) {
        this.modelDate = modelDate;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getLegacyCasingId() {
        return legacyCasingId;
    }

    public void setLegacyCasingId(Integer legacyCasingId) {
        this.legacyCasingId = legacyCasingId;
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
