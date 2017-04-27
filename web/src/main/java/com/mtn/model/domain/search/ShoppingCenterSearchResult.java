package com.mtn.model.domain.search;

import com.vividsolutions.jts.geom.Point;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Allen on 4/26/2017.
 */
@Entity
@Table(name = "shopping_center")
@SecondaryTables({
        @SecondaryTable(name = "site", pkJoinColumns = {
                @PrimaryKeyJoinColumn(name = "shopping_center_id", referencedColumnName = "id")
        })
})
public class ShoppingCenterSearchResult {

    private Integer siteId;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String postalCode;
    private String county;
    private String country;
    private Point location;
    private String intersectionStreetPrimary;
    private String intersectionStreetSecondary;
    private LocalDateTime siteDeletedDate;

    private Integer shoppingCenterId;
    private String name;
    private String owner;
    private LocalDateTime shoppingCenterDeletedDate;

    @Column(table = "site", name = "id", insertable = false, updatable = false)
    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    @Column(table = "site", name = "address_1", insertable = false, updatable = false)
    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    @Column(table = "site", name = "address_2", insertable = false, updatable = false)
    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    @Column(table = "site", insertable = false, updatable = false)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(table = "site", insertable = false, updatable = false)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(table = "site", insertable = false, updatable = false)
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Column(table = "site", insertable = false, updatable = false)
    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    @Column(table = "site", insertable = false, updatable = false)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(table = "site", insertable = false, updatable = false)
    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    @Column(table = "site", insertable = false, updatable = false)
    public String getIntersectionStreetPrimary() {
        return intersectionStreetPrimary;
    }

    public void setIntersectionStreetPrimary(String intersectionStreetPrimary) {
        this.intersectionStreetPrimary = intersectionStreetPrimary;
    }

    @Column(table = "site", insertable = false, updatable = false)
    public String getIntersectionStreetSecondary() {
        return intersectionStreetSecondary;
    }

    public void setIntersectionStreetSecondary(String intersectionStreetSecondary) {
        this.intersectionStreetSecondary = intersectionStreetSecondary;
    }

    @Column(table = "site", name = "deleted_date", insertable = false, updatable = false)
    public LocalDateTime getSiteDeletedDate() {
        return siteDeletedDate;
    }

    public void setSiteDeletedDate(LocalDateTime siteDeletedDate) {
        this.siteDeletedDate = siteDeletedDate;
    }

    @Id
    @Column(table = "shopping_center", name = "id", insertable = false, updatable = false)
    public Integer getShoppingCenterId() {
        return shoppingCenterId;
    }

    public void setShoppingCenterId(Integer shoppingCenterId) {
        this.shoppingCenterId = shoppingCenterId;
    }

    @Column(table = "shopping_center", insertable = false, updatable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(table = "shopping_center", insertable = false, updatable = false)
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Column(table = "shopping_center", name = "deleted_date", insertable = false, updatable = false)
    public LocalDateTime getShoppingCenterDeletedDate() {
        return shoppingCenterDeletedDate;
    }

    public void setShoppingCenterDeletedDate(LocalDateTime shoppingCenterDeletedDate) {
        this.shoppingCenterDeletedDate = shoppingCenterDeletedDate;
    }
}
