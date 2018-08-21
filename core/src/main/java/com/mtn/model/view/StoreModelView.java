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

    public Double getCurve() {
        return curve;
    }

    public Double getPwta() {
        return pwta;
    }

    public Double getPower() {
        return power;
    }

    public Double getFitAdjustedPower() {
        return fitAdjustedPower;
    }

    public ModelType getModelType() {
        return modelType;
    }

    public LocalDateTime getModelDate() {
        return modelDate;
    }

    public Integer getLegacyCasingId() {
        return legacyCasingId;
    }

    public SimpleProjectView getProject() {
        return project;
    }
}
