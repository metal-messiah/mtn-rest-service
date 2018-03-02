package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.StoreType;
import com.mtn.model.domain.Site;
import com.mtn.model.domain.Store;
import com.vividsolutions.jts.geom.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 4/24/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleSiteView {

    private Integer id;
    private Point location;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String postalCode;
    private String intersectionStreetPrimary;
    private String intersectionStreetSecondary;
    private String quad;
    private SimpleStoreView activeStore;
    private Boolean hasPlannedStore;

    public SimpleSiteView(Site site) {
        this.id = site.getId();
        this.location = site.getLocation();
        this.address1 = site.getAddress1();
        this.address2 = site.getAddress2();
        this.city = site.getCity();
        this.state = site.getState();
        this.postalCode = site.getPostalCode();
        this.intersectionStreetPrimary = site.getIntersectionStreetPrimary();
        this.intersectionStreetSecondary = site.getIntersectionStreetSecondary();
        this.quad = site.getQuad();
        site.getStores().stream()
                .filter(s -> s.getStoreType().equals(StoreType.ACTIVE))
                .findFirst().ifPresent(store -> this.activeStore = new SimpleStoreView(store));
        for (Store store : site.getStores()) {
            if (store.getStoreType().equals(StoreType.FUTURE)) {
                this.hasPlannedStore = true;
                break;
            }
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getQuad() {
        return quad;
    }

    public void setQuad(String quad) {
        this.quad = quad;
    }

    public SimpleStoreView getActiveStore() {
        return activeStore;
    }

    public void setActiveStore(SimpleStoreView activeStore) {
        this.activeStore = activeStore;
    }

    public Boolean getHasPlannedStore() {
        return hasPlannedStore;
    }

    public void setHasPlannedStore(Boolean hasPlannedStore) {
        this.hasPlannedStore = hasPlannedStore;
    }
}
