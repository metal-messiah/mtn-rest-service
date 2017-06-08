package com.mtn.model.view;

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

    protected Integer id;
    protected StoreFitType fit;
    protected StoreFormatType format;
    protected Integer areaSales;
    protected Double areaSalesPercentOfTotal;
    protected Integer areaTotal;
    protected Boolean areaIsEstimate;

    public SimpleStoreSurveyView() {
    }

    public SimpleStoreSurveyView(StoreSurvey storeSurvey) {
        this.id = storeSurvey.getId();
        this.fit = storeSurvey.getFit();
        this.format = storeSurvey.getFormat();
        this.areaSales = storeSurvey.getAreaSales();
        this.areaSalesPercentOfTotal = storeSurvey.getAreaSalesPercentOfTotal();
        this.areaTotal = storeSurvey.getAreaTotal();
        this.areaIsEstimate = storeSurvey.getAreaIsEstimate();
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

    public Double getAreaSalesPercentOfTotal() {
        return areaSalesPercentOfTotal;
    }

    public void setAreaSalesPercentOfTotal(Double areaSalesPercentOfTotal) {
        this.areaSalesPercentOfTotal = areaSalesPercentOfTotal;
    }

    public Integer getAreaTotal() {
        return areaTotal;
    }

    public void setAreaTotal(Integer areaTotal) {
        this.areaTotal = areaTotal;
    }

    public Boolean getAreaIsEstimate() {
        return areaIsEstimate;
    }

    public void setAreaIsEstimate(Boolean areaIsEstimate) {
        this.areaIsEstimate = areaIsEstimate;
    }
}
