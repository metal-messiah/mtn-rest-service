package com.mtn.model.view.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.search.ShoppingCenterSearchResult;
import com.mtn.model.domain.search.StoreSearchResult;
import com.mtn.model.view.geojson.GeoJsonView;

/**
 * Created by Allen on 4/26/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SiteSearchResultView {

    private Integer id;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String postalCode;
    private String county;
    private String country;
    private GeoJsonView location;
    private String intersectionStreetPrimary;
    private String intersectionStreetSecondary;

    public SiteSearchResultView() {
    }

    public SiteSearchResultView(ShoppingCenterSearchResult searchResult) {
        this.id = searchResult.getSiteId();
        this.address1 = searchResult.getAddress1();
        this.address2 = searchResult.getAddress2();
        this.city = searchResult.getCity();
        this.state = searchResult.getState();
        this.postalCode = searchResult.getPostalCode();
        this.county = searchResult.getCounty();
        this.country = searchResult.getCountry();
        this.intersectionStreetPrimary = searchResult.getIntersectionStreetPrimary();
        this.intersectionStreetSecondary = searchResult.getIntersectionStreetSecondary();

        if (searchResult.getLocation() != null) {
            this.location = new GeoJsonView(searchResult.getLocation());
        }
    }

    public SiteSearchResultView(StoreSearchResult searchResult) {
        this.id = searchResult.getSiteId();
        this.address1 = searchResult.getAddress1();
        this.address2 = searchResult.getAddress2();
        this.city = searchResult.getCity();
        this.state = searchResult.getState();
        this.postalCode = searchResult.getPostalCode();
        this.county = searchResult.getCounty();
        this.country = searchResult.getCountry();
        this.intersectionStreetPrimary = searchResult.getIntersectionStreetPrimary();
        this.intersectionStreetSecondary = searchResult.getIntersectionStreetSecondary();

        if (searchResult.getLocation() != null) {
            this.location = new GeoJsonView(searchResult.getLocation());
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public GeoJsonView getLocation() {
        return location;
    }

    public void setLocation(GeoJsonView location) {
        this.location = location;
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
}
