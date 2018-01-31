package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.StoreFitType;
import com.mtn.constant.StoreFormatType;
import com.mtn.model.domain.StoreSurvey;

import java.io.Serializable;

/**
 * Created by Allen on 6/7/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleStoreSurveyView implements Serializable {

    private Integer id;
    private StoreFitType fit;
    private StoreFormatType format;
    private Integer areaSales;
    private String note;

    private Integer legacyCasingId;

    public SimpleStoreSurveyView(StoreSurvey storeSurvey) {
        this.id = storeSurvey.getId();
        this.fit = storeSurvey.getFit();
        this.format = storeSurvey.getFormat();
        this.areaSales = storeSurvey.getAreaSales();

        this.setNote(storeSurvey.getNote());
        this.setLegacyCasingId(storeSurvey.getLegacyCasingId());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public StoreFitType getFit() {
        return fit;
    }

    public void setFit(StoreFitType fit) {
        this.fit = fit;
    }

    public StoreFormatType getFormat() {
        return format;
    }

    public void setFormat(StoreFormatType format) {
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
