package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.SiteType;
import com.mtn.model.domain.Site;
import com.mtn.model.simpleView.SimpleUserProfileView;

import java.util.List;
import java.util.stream.Collectors;

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
    private String intersectionStreetPrimary;
    private String intersectionStreetSecondary;
    private String quad;
    private String positionInCenter;
    private Boolean duplicate;
    private Boolean backfilledNonGrocery;

    private ShoppingCenterView shoppingCenter;
    private List<StoreView> stores;
    private SimpleUserProfileView assignee;

    public SiteView() {
    }

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
        this.intersectionStreetPrimary = site.getIntersectionStreetPrimary();
        this.intersectionStreetSecondary = site.getIntersectionStreetSecondary();
        this.quad = site.getQuad();
        this.positionInCenter = site.getPositionInCenter();
        this.duplicate = site.getDuplicate();
        this.backfilledNonGrocery = site.getBackfilledNonGrocery();

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

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public SiteType getType() {
        return type;
    }

    public void setType(SiteType type) {
        this.type = type;
    }

    public String getIntersectionType() {
        return intersectionType;
    }

    public void setIntersectionType(String intersectionType) {
        this.intersectionType = intersectionType;
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

    public String getPositionInCenter() {
        return positionInCenter;
    }

    public void setPositionInCenter(String positionInCenter) {
        this.positionInCenter = positionInCenter;
    }

    public Boolean getDuplicate() {
        return duplicate;
    }

    public void setDuplicate(Boolean duplicate) {
        this.duplicate = duplicate;
    }

    public ShoppingCenterView getShoppingCenter() {
        return shoppingCenter;
    }

    public void setShoppingCenter(ShoppingCenterView shoppingCenter) {
        this.shoppingCenter = shoppingCenter;
    }

    public List<StoreView> getStores() {
        return stores;
    }

    public void setStores(List<StoreView> stores) {
        this.stores = stores;
    }

    public SimpleUserProfileView getAssignee() {
        return assignee;
    }

    public void setAssignee(SimpleUserProfileView assignee) {
        this.assignee = assignee;
    }

    public Boolean getBackfilledNonGrocery() {
        return backfilledNonGrocery;
    }

    public void setBackfilledNonGrocery(Boolean backfilledNonGrocery) {
        this.backfilledNonGrocery = backfilledNonGrocery;
    }
}
