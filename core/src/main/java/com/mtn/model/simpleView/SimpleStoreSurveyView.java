package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.StoreSurvey;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleStoreSurveyView extends SimpleAuditingEntityView {

    private LocalDateTime surveyDate;
    private String note;

    public SimpleStoreSurveyView() {
    }

    public SimpleStoreSurveyView(StoreSurvey storeSurvey) {
        super(storeSurvey);
        this.surveyDate = storeSurvey.getSurveyDate();
        this.note = storeSurvey.getNote();
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
