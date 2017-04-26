package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.SiteLocationType;
import com.mtn.constant.SitePositionType;
import com.mtn.constant.SiteType;
import com.mtn.model.domain.Site;
import com.mtn.model.view.geojson.GeoJsonView;

/**
 * Created by Allen on 4/24/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleSiteView {

    protected Integer id;
    protected GeoJsonView location;
    protected SiteType type;
    protected SiteLocationType locationType;
    protected String address1;
    protected String address2;
    protected String city;
    protected String state;
    protected String postalCode;
    protected String county;
    protected String country;
    protected Integer footprintSqft;
    protected String intersectionStreetPrimary;
    protected String intersectionStreetSecondary;
    protected String intersectionQuad;
    protected SitePositionType positionInCenter;
    protected Integer version;

    public SimpleSiteView() {
    }

    public SimpleSiteView(Site site) {
        this.id = site.getId();
        this.location = new GeoJsonView(site.getLocation());
        this.type = site.getType();
        this.locationType = site.getLocationType();
        this.address1 = site.getAddress1();
        this.address2 = site.getAddress2();
        this.city = site.getCity();
        this.state = site.getState();
        this.postalCode = site.getPostalCode();
        this.county = site.getCounty();
        this.country = site.getCountry();
        this.footprintSqft = site.getFootprintSqft();
        this.intersectionStreetPrimary = site.getIntersectionStreetPrimary();
        this.intersectionStreetSecondary = site.getIntersectionStreetSecondary();
        this.intersectionQuad = site.getIntersectionQuad();
        this.positionInCenter = site.getPositionInCenter();
        this.version = site.getVersion();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GeoJsonView getLocation() {
        return location;
    }

    public void setLocation(GeoJsonView location) {
        this.location = location;
    }

    public SiteType getType() {
        return type;
    }

    public void setType(SiteType type) {
        this.type = type;
    }

    public SiteLocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(SiteLocationType locationType) {
        this.locationType = locationType;
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

    public Integer getFootprintSqft() {
        return footprintSqft;
    }

    public void setFootprintSqft(Integer footprintSqft) {
        this.footprintSqft = footprintSqft;
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

    public String getIntersectionQuad() {
        return intersectionQuad;
    }

    public void setIntersectionQuad(String intersectionQuad) {
        this.intersectionQuad = intersectionQuad;
    }

    public SitePositionType getPositionInCenter() {
        return positionInCenter;
    }

    public void setPositionInCenter(SitePositionType positionInCenter) {
        this.positionInCenter = positionInCenter;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
