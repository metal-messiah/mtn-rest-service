package com.mtn.model.domain;

import com.mtn.constant.IntersectionType;
import com.mtn.constant.SitePositionType;
import com.mtn.constant.SiteType;
import com.mtn.constant.StoreType;
import com.mtn.model.simpleView.SimpleSiteView;
import com.mtn.model.view.SiteView;
import com.mtn.model.view.geojson.PointGeometry;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 4/24/2017.
 */
@Entity
@Table
public class Site extends AuditingEntity implements Identifiable {

    private Integer id;
    private ShoppingCenter shoppingCenter;
    private Point location;
    private SiteType type;
    private IntersectionType intersectionType;
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
    private String intersectionQuad;
    private SitePositionType positionInCenter;

    private List<Store> stores = new ArrayList<>();

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

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    @Enumerated(EnumType.STRING)
    public SiteType getType() {
        return type;
    }

    public void setType(SiteType type) {
        this.type = type;
    }

    @Enumerated(EnumType.STRING)
    public IntersectionType getIntersectionType() {
        return intersectionType;
    }

    public void setIntersectionType(IntersectionType intersectionType) {
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

    public String getIntersectionQuad() {
        return intersectionQuad;
    }

    public void setIntersectionQuad(String intersectionQuad) {
        this.intersectionQuad = intersectionQuad;
    }

    @Enumerated(EnumType.STRING)
    public SitePositionType getPositionInCenter() {
        return positionInCenter;
    }

    public void setPositionInCenter(SitePositionType positionInCenter) {
        this.positionInCenter = positionInCenter;
    }

    @OneToMany(mappedBy = "site")
    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    @Transient
    public Store findActiveStore() {
        return this.stores.stream().filter(store -> store.getType() == StoreType.ACTIVE).findFirst().orElse(null);
    }
}
