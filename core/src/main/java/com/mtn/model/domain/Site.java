package com.mtn.model.domain;

import com.mtn.constant.SiteType;
import com.mtn.constant.StoreType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 4/24/2017.
 */
@Entity
@Table
public class Site extends AuditingEntity implements Identifiable, Serializable {

    private Integer id;
    private Float latitude;
    private Float longitude;
    private SiteType type;
    private Integer footprintSqft;
    private String positionInCenter;
    private String address1;
    private String address2;
    private String city;
    private String county;
    private String state;
    private String postalCode;
    private String country;
    private String intersectionType;
    private String quad;
    private String intersectionStreetPrimary;
    private String intersectionStreetSecondary;
    private Boolean duplicate;

    private ShoppingCenter shoppingCenter;
    private List<Store> stores = new ArrayList<>();
    private List<Interaction> interactions = new ArrayList<>();

    public Site() {
    }

    @Id
    @GeneratedValue
    @Column(name = "site_id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "shopping_center_id")
    public ShoppingCenter getShoppingCenter() {
        return shoppingCenter;
    }

    public void setShoppingCenter(ShoppingCenter shoppingCenter) {
        this.shoppingCenter = shoppingCenter;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "site_type")
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

    @Column(name = "address_1")
    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    @Column(name = "address_2")
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

    @OneToMany(mappedBy = "site")
    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    @Column(name = "is_duplicate")
    public Boolean getDuplicate() {
        return duplicate;
    }

    public void setDuplicate(Boolean duplicate) {
        this.duplicate = duplicate;
    }

    @OneToMany(mappedBy = "site")
    public List<Interaction> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<Interaction> interactions) {
        this.interactions = interactions;
    }

    @Transient
    public Store findActiveStore() {
        return this.stores.stream().filter(store -> store.getStoreType() == StoreType.ACTIVE).findFirst().orElse(null);
    }


}
