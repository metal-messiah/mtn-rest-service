package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.StoreSurvey;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Allen on 6/7/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleStoreSurveyView implements Serializable {

    private Integer id;
    private LocalDateTime surveyDate;
    private String fit;
    private String format;
    private Integer areaSales;
    private Integer areaTotal;
    private String note;

    private Integer legacyCasingId;

    public SimpleStoreSurveyView(StoreSurvey storeSurvey) {
        this.id = storeSurvey.getId();
        this.surveyDate = storeSurvey.getSurveyDate();
        this.fit = storeSurvey.getFit();
        this.format = storeSurvey.getFormat();
        this.areaSales = storeSurvey.getAreaSales();
        this.areaTotal = storeSurvey.getAreaTotal();
        this.note = storeSurvey.getNote();
        this.legacyCasingId = storeSurvey.getLegacyCasingId();
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

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
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
