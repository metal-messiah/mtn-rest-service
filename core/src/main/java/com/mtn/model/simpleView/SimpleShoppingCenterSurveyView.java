package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ShoppingCenterSurvey;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleShoppingCenterSurveyView extends SimpleAuditingEntityView {

	private LocalDateTime surveyDate;
	private String note;

	public SimpleShoppingCenterSurveyView() {
	}

	public SimpleShoppingCenterSurveyView(ShoppingCenterSurvey survey) {
		super(survey);
		this.surveyDate = survey.getSurveyDate();
		this.note = survey.getNote();
	}

	public LocalDateTime getSurveyDate() {
		return surveyDate;
	}

	public void setSurveyDate(LocalDateTime surveyDate) {
		this.surveyDate = surveyDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}


}
