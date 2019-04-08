package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreSourceData {

    private String shoppingCenterName;
    private String address;
    private String city;
    private String county;
    private String state;
    private String postalCode;
    private Float latitude;
    private Float longitude;

    private String dateOpened;
    private String storeStatus;
    private Integer areaTotal;
    private Boolean areaIsActual;

    public StoreSourceData() {
    }

    public String getShoppingCenterName() {
        return shoppingCenterName;
    }

    public void setShoppingCenterName(String shoppingCenterName) {
        this.shoppingCenterName = shoppingCenterName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(String dateOpened) {
        this.dateOpened = dateOpened;
    }

    public Boolean getAreaIsActual() {
        return areaIsActual;
    }

    public void setAreaIsActual(Boolean areaIsActual) {
        this.areaIsActual = areaIsActual;
    }

    public String getStoreStatus() {
        return storeStatus;
    }

    public void setStoreStatus(String storeStatus) {
        this.storeStatus = storeStatus;
    }

    public Integer getAreaTotal() {
        return areaTotal;
    }

    public void setAreaTotal(Integer areaTotal) {
        this.areaTotal = areaTotal;
    }

}
