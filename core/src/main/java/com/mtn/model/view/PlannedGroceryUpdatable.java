package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ShoppingCenter;
import com.mtn.model.domain.Site;
import com.mtn.model.domain.Store;
import com.mtn.model.simpleView.SimpleStoreStatusView;
import com.mtn.model.utils.StoreUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlannedGroceryUpdatable {

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

    public PlannedGroceryUpdatable() {
    }

    public PlannedGroceryUpdatable(Store store) {
        this.setStoreData(store);
    }

    public PlannedGroceryUpdatable(Site site) {
        this.setSiteData(site);
    }

    public PlannedGroceryUpdatable(ShoppingCenter shoppingCenter) {
        this.setShoppingCenterData(shoppingCenter);
    }

    public Integer getShoppingCenterId() {
        return shoppingCenterId;
    }

    public String getShoppingCenterName() {
        return shoppingCenterName;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public String getAddress() {
        return address;
    }

    public String getQuad() {
        return quad;
    }

    public String getIntersectionStreetPrimary() {
        return intersectionStreetPrimary;
    }

    public String getIntersectionStreetSecondary() {
        return intersectionStreetSecondary;
    }

    public String getCity() {
        return city;
    }

    public String getCounty() {
        return county;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public LocalDateTime getDateOpened() {
        return dateOpened;
    }

    public List<SimpleStoreStatusView> getStoreStatuses() {
        return storeStatuses;
    }

    public Integer getAreaTotal() {
        return areaTotal;
    }

    private void setStoreData(Store store) {
        this.storeId = store.getId();
        this.storeName = store.getStoreName();
        this.dateOpened = store.getDateOpened();
        this.storeStatuses = store.getStatuses().stream().map(SimpleStoreStatusView::new).collect(Collectors.toList());

        StoreUtil.getLatestSurveyAsOfDateTime(store, LocalDateTime.now()).ifPresent(storeSurvey -> {
            this.areaTotal = storeSurvey.getAreaTotal();
        });
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
