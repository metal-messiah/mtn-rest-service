package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.ModelType;
import com.mtn.model.domain.StoreModel;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleStoreModelView {

	private Integer id;
	private Double fitAdjustedPower;
	private ModelType modelType;
	private LocalDateTime modelDate;

	public SimpleStoreModelView(StoreModel model) {
		this.id = model.getId();
		this.fitAdjustedPower = model.getFitAdjustedPower();
		this.modelType = model.getModelType();
		this.modelDate = model.getModelDate();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
