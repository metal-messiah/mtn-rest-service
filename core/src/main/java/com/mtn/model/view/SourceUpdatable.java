package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ShoppingCenter;
import com.mtn.model.domain.Site;
import com.mtn.model.domain.Store;
import com.mtn.model.simpleView.SimpleStoreSourceView;
import com.mtn.model.simpleView.SimpleStoreStatusView;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SourceUpdatable {

    private Integer shoppingCenterId;
    private String shoppingCenterName;

    private Integer siteId;
    private String address;
    private String quad;
    private String intersectionStreetPrimary;
    private String intersectionStreetSecondary;
    private String city;
    private String county;
    private String state;
    private String postalCode;
    private Float latitude;
    private Float longitude;

    private Integer storeId;
    private String storeName;
    private LocalDateTime dateOpened;
    private List<SimpleStoreStatusView> storeStatuses;
    private Integer areaTotal;

    private SimpleStoreSourceView storeSource;

    public SourceUpdatable() {
    }

    public SourceUpdatable(Store store) {
        this.setStoreData(store);
    }

    public SourceUpdatable(Site site) {
        this.setSiteData(site);
    }

    public SourceUpdatable(ShoppingCenter shoppingCenter) {
        this.setShoppingCenterData(shoppingCenter);
    }

    public Integer getShoppingCenterId() {
        return shoppingCenterId;
    }

    public void setShoppingCenterId(Integer shoppingCenterId) {
        this.shoppingCenterId = shoppingCenterId;
    }

    public String getShoppingCenterName() {
        return shoppingCenterName;
    }

    public void setShoppingCenterName(String shoppingCenterName) {
        this.shoppingCenterName = shoppingCenterName;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQuad() {
        return quad;
    }

    public void setQuad(String quad) {
        this.quad = quad;
    }

    public String getIntersectionStreetPrimary() {
        return intersectionStreetPrimary;
    }

    public void setIntersectionStreetPrimary(String intersectionStreetPrimary) {
        this.intersectionStreetPrimary = intersectionStreetPrimary;
    }

    public String getIntersectionStreetSecondary() {
        return intersectionStreetSecondary;
    }

    public void setIntersectionStreetSecondary(String intersectionStreetSecondary) {
        this.intersectionStreetSecondary = intersectionStreetSecondary;
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

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public LocalDateTime getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(LocalDateTime dateOpened) {
        this.dateOpened = dateOpened;
    }

    public List<SimpleStoreStatusView> getStoreStatuses() {
        return storeStatuses;
    }

    public void setStoreStatuses(List<SimpleStoreStatusView> storeStatuses) {
        this.storeStatuses = storeStatuses;
    }

    public Integer getAreaTotal() {
        return areaTotal;
    }

    public void setAreaTotal(Integer areaTotal) {
        this.areaTotal = areaTotal;
    }

    public SimpleStoreSourceView getStoreSource() {
        return storeSource;
    }

    public void setStoreSource(SimpleStoreSourceView storeSource) {
        this.storeSource = storeSource;
    }

    private void setStoreData(Store store) {
        this.storeId = store.getId();
        this.storeName = store.getStoreName();
        this.dateOpened = store.getDateOpened();
        this.storeStatuses = store.getStatuses().stream().map(SimpleStoreStatusView::new).collect(Collectors.toList());
        this.areaTotal = store.getAreaTotal();

        this.setSiteData(store.getSite());
    }

    private void setSiteData(Site site) {
        this.siteId = site.getId();
        this.address = site.getAddress1();
        this.quad = site.getQuad();
        this.intersectionStreetPrimary = site.getIntersectionStreetPrimary();
        this.intersectionStreetSecondary = site.getIntersectionStreetSecondary();
        this.city = site.getCity();
        this.county = site.getCounty();
        this.state = site.getState();
        this.postalCode = site.getPostalCode();
        this.latitude = site.getLatitude();
        this.longitude = site.getLongitude();
        this.setShoppingCenterData(site.getShoppingCenter());
    }

    private void setShoppingCenterData(ShoppingCenter shoppingCenter) {
        this.shoppingCenterId = shoppingCenter.getId();
        this.shoppingCenterName = shoppingCenter.getName();
    }
}
