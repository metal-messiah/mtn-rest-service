package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.SiteType;
import com.mtn.model.domain.Site;
import com.mtn.model.simpleView.SimpleUserProfileView;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 4/25/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SiteView extends AuditingEntityView {

    private Float latitude;
    private Float longitude;
    private SiteType type;
    private String intersectionType;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String postalCode;
    private String county;
    private String country;
    private Integer footprintSqft;
    private String intersectionStreetPrimary;
    private String intersectionStreetSecondary;
    private String quad;
    private String positionInCenter;
    private Boolean duplicate;

    private ShoppingCenterView shoppingCenter;
    private List<StoreView> stores;
    private SimpleUserProfileView assignee;

    public SiteView(Site site) {
        super(site);

        this.latitude = site.getLatitude();
        this.longitude = site.getLongitude();
        this.type = site.getType();
        this.intersectionType = site.getIntersectionType();
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
        this.quad = site.getQuad();
        this.positionInCenter = site.getPositionInCenter();
        this.duplicate = site.getDuplicate();

        if (site.getShoppingCenter() != null) {
            this.shoppingCenter = new ShoppingCenterView(site.getShoppingCenter());
        }
        if (site.getStores() != null) {
            this.stores = site.getStores().stream()
                    .filter(store -> store.getDeletedDate() == null)
                    .map(StoreView::new)
                    .collect(Collectors.toList());
        }
        if (site.getAssignee() != null) {
            this.assignee = new SimpleUserProfileView(site.getAssignee());
        }
    }

    public Float getLatitude() {
        return latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public SiteType getType() {
        return type;
    }

    public String getIntersectionType() {
        return intersectionType;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCounty() {
        return county;
    }

    public String getCountry() {
        return country;
    }

    public Integer getFootprintSqft() {
        return footprintSqft;
    }

    public String getIntersectionStreetPrimary() {
        return intersectionStreetPrimary;
    }

    public String getIntersectionStreetSecondary() {
        return intersectionStreetSecondary;
    }

    public String getQuad() {
        return quad;
    }

    public String getPositionInCenter() {
        return positionInCenter;
    }

    public Boolean getDuplicate() {
        return duplicate;
    }

    public ShoppingCenterView getShoppingCenter() {
        return shoppingCenter;
    }

    public List<StoreView> getStores() {
        return stores;
    }

    public SimpleUserProfileView getAssignee() {
        return assignee;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public void setType(SiteType type) {
        this.type = type;
    }

    public void setIntersectionType(String intersectionType) {
        this.intersectionType = intersectionType;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setFootprintSqft(Integer footprintSqft) {
        this.footprintSqft = footprintSqft;
    }

    public void setIntersectionStreetPrimary(String intersectionStreetPrimary) {
        this.intersectionStreetPrimary = intersectionStreetPrimary;
    }

    public void setIntersectionStreetSecondary(String intersectionStreetSecondary) {
        this.intersectionStreetSecondary = intersectionStreetSecondary;
    }

    public void setQuad(String quad) {
        this.quad = quad;
    }

    public void setPositionInCenter(String positionInCenter) {
        this.positionInCenter = positionInCenter;
    }

    public void setDuplicate(Boolean duplicate) {
        this.duplicate = duplicate;
    }

    public void setShoppingCenter(ShoppingCenterView shoppingCenter) {
        this.shoppingCenter = shoppingCenter;
    }

    public void setStores(List<StoreView> stores) {
        this.stores = stores;
    }

    public void setAssignee(SimpleUserProfileView assignee) {
        this.assignee = assignee;
    }
}
