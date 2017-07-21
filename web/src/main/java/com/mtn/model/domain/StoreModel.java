package com.mtn.model.domain;

import com.mtn.constant.ModelType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
public class StoreModel extends AuditingEntity implements Identifiable {

    private Integer id;
    private Store store;
    private String mapkey;
    private Double curve;
    private Double pwta;
    private Double power;
    private Double fitAdjustedPower;
    private ModelType modelType;
    private LocalDateTime modelDate;
    private Integer version;
    private Integer legacyCasingId;

    @PrePersist
    public void prePersist() {
        version = 1;
    }

    @PreUpdate
    public void preUpdate() {
        version++;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_store_model_id")
    @SequenceGenerator(name = "seq_store_model_id", sequenceName = "seq_store_model_id", allocationSize = 1)
    @Column(name = "store_model_id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "store_id")
    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
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
}
