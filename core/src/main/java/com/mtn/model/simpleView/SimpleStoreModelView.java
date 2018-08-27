package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.ModelType;
import com.mtn.model.domain.StoreModel;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleStoreModelView extends SimpleAuditingEntityView {

	private Double fitAdjustedPower;
	private ModelType modelType;
	private LocalDateTime modelDate;

	public SimpleStoreModelView() {
	}

	public SimpleStoreModelView(StoreModel model) {
		super(model);
		this.fitAdjustedPower = model.getFitAdjustedPower();
		this.modelType = model.getModelType();
		this.modelDate = model.getModelDate();
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
}
