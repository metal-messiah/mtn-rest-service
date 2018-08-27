package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.ModelType;
import com.mtn.model.domain.StoreModel;
import com.mtn.model.simpleView.SimpleProjectView;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreModelView extends AuditingEntityView {

    private String mapkey;
    private Double curve;
    private Double pwta;
    private Double power;
    private Double fitAdjustedPower;
    private ModelType modelType;
    private LocalDateTime modelDate;
    private Integer legacyCasingId;
    private SimpleProjectView project;

    public StoreModelView() {
    }

    public StoreModelView(StoreModel model) {
        super(model);

        this.mapkey = model.getMapkey();
        this.curve = model.getCurve();
        this.pwta = model.getPwta();
        this.power = model.getPower();
        this.fitAdjustedPower = model.getFitAdjustedPower();
        this.modelType = model.getModelType();
        this.modelDate = model.getModelDate();
        this.legacyCasingId = model.getLegacyCasingId();
        if (model.getProject() != null) {
            this.project = new SimpleProjectView(model.getProject());
        }
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

    public Integer getLegacyCasingId() {
        return legacyCasingId;
    }

    public void setLegacyCasingId(Integer legacyCasingId) {
        this.legacyCasingId = legacyCasingId;
    }

    public SimpleProjectView getProject() {
        return project;
    }

    public void setProject(SimpleProjectView project) {
        this.project = project;
    }
}
