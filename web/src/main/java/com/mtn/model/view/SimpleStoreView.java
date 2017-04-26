package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.StoreFitType;
import com.mtn.constant.StoreFormatType;
import com.mtn.model.domain.Store;

import java.time.LocalDateTime;

/**
 * Created by Allen on 4/26/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleStoreView {

    protected Integer id;
    protected String name;
    protected StoreFitType fit;
    protected StoreFormatType format;
    protected Boolean isActive = false;
    protected Integer areaSales;
    protected Double areaSalesPercentOfTotal;
    protected Integer areaTotal;
    protected Boolean areaIsEstimate = true;
    protected LocalDateTime openedDate;
    protected LocalDateTime closedDate;
    protected Integer version;

    public SimpleStoreView() {
    }

    public SimpleStoreView(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.fit = store.getFit();
        this.format = store.getFormat();
        this.isActive = store.getActive();
        this.areaSales = store.getAreaSales();
        this.areaSalesPercentOfTotal = store.getAreaSalesPercentOfTotal();
        this.areaTotal = store.getAreaTotal();
        this.areaIsEstimate = store.getAreaIsEstimate();
        this.openedDate = store.getOpenedDate();
        this.closedDate = store.getClosedDate();
        this.version = store.getVersion();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
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

    public LocalDateTime getOpenedDate() {
        return openedDate;
    }

    public void setOpenedDate(LocalDateTime openedDate) {
        this.openedDate = openedDate;
    }

    public LocalDateTime getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(LocalDateTime closedDate) {
        this.closedDate = closedDate;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
