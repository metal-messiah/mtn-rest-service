package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.StoreSurvey;

import java.io.Serializable;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleStoreSurveyView extends SimpleAuditingEntityView {

    private LocalDateTime surveyDate;
    private String fit;
    private Integer areaSales;
    private Integer areaTotal;
    private String note;

    private Integer legacyCasingId;

    public SimpleStoreSurveyView() {
    }

    public SimpleStoreSurveyView(StoreSurvey storeSurvey) {
        super(storeSurvey);
        this.surveyDate = storeSurvey.getSurveyDate();
        this.fit = storeSurvey.getFit();
        this.areaSales = storeSurvey.getAreaSales();
        this.areaTotal = storeSurvey.getAreaTotal();
        this.note = storeSurvey.getNote();
        this.legacyCasingId = storeSurvey.getLegacyCasingId();
    }

    public LocalDateTime getSurveyDate() {
        return surveyDate;
    }

    public void setSurveyDate(LocalDateTime surveyDate) {
        this.surveyDate = surveyDate;
    }

    public Integer getAreaTotal() {
        return areaTotal;
    }

    public void setAreaTotal(Integer areaTotal) {
        this.areaTotal = areaTotal;
    }

    public String getFit() {
        return fit;
    }

    public void setFit(String fit) {
        this.fit = fit;
    }

    public Integer getAreaSales() {
        return areaSales;
    }

    public void setAreaSales(Integer areaSales) {
        this.areaSales = areaSales;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getLegacyCasingId() {
        return legacyCasingId;
    }

    public void setLegacyCasingId(Integer legacyCasingId) {
        this.legacyCasingId = legacyCasingId;
    }

}
