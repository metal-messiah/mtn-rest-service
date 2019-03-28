package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Site;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleSiteView extends SimpleAuditingEntityView {

    private Float latitude;
    private Float longitude;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String postalCode;
    private String intersectionStreetPrimary;
    private String intersectionStreetSecondary;
    private String quad;
    private Boolean duplicate;
    private SimpleUserProfileView assignee;
    private Integer shoppingCenterId;

    public SimpleSiteView() {
    }

    public SimpleSiteView(Site site) {
        super(site);
        this.latitude = site.getLatitude();
        this.longitude = site.getLongitude();
        this.address1 = site.getAddress1();
        this.address2 = site.getAddress2();
        this.city = site.getCity();
        this.state = site.getState();
        this.postalCode = site.getPostalCode();
        this.intersectionStreetPrimary = site.getIntersectionStreetPrimary();
        this.intersectionStreetSecondary = site.getIntersectionStreetSecondary();
        this.quad = site.getQuad();
        this.duplicate = site.getDuplicate();

        this.shoppingCenterId = site.getShoppingCenter().getId();

        if (site.getAssignee() != null) {
            this.assignee = new SimpleUserProfileView(site.getAssignee());
        }
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

    public Boolean getDuplicate() {
        return duplicate;
    }

    public void setDuplicate(Boolean duplicate) {
        this.duplicate = duplicate;
    }

    public SimpleUserProfileView getAssignee() {
        return assignee;
    }

    public void setAssignee(SimpleUserProfileView assignee) {
        this.assignee = assignee;
    }

    public Integer getShoppingCenterId() {
        return shoppingCenterId;
    }

    public void setShoppingCenterId(Integer shoppingCenterId) {
        this.shoppingCenterId = shoppingCenterId;
    }
}
