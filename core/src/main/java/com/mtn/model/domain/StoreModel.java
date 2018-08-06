package com.mtn.model.domain;

import com.mtn.constant.ModelType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@AttributeOverride(name="id", column=@Column(name="store_model_id"))
public class StoreModel extends AuditingEntity {

    private Store store;
    private Project project;
    private String mapkey;
    private Double curve;
    private Double pwta;
    private Double power;
    private Double fitAdjustedPower;
    private ModelType modelType;
    private LocalDateTime modelDate;
    private Integer legacyCasingId;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "store_id")
    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "project_id")
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
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

    @Enumerated(EnumType.STRING)
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
}
