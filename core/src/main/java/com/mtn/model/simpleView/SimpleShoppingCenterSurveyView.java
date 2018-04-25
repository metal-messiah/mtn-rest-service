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
	private LocalDateTime surveyDate;
	private String centerType;
	private String note;

	public SimpleShoppingCenterSurveyView(ShoppingCenterSurvey survey) {
		this.id = survey.getId();
		this.surveyDate = survey.getSurveyDate();
		this.centerType = survey.getCenterType();
		this.note = survey.getNote();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getSurveyDate() {
		return surveyDate;
	}

	public void setSurveyDate(LocalDateTime surveyDate) {
		this.surveyDate = surveyDate;
	}

	public String getCenterType() {
		return centerType;
	}

	public void setCenterType(String centerType) {
		this.centerType = centerType;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}


}
