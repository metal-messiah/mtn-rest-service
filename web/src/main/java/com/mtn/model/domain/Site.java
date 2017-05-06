package com.mtn.model.domain;

import com.mtn.constant.SiteLocationType;
import com.mtn.constant.SitePositionType;
import com.mtn.constant.SiteType;
import com.mtn.model.view.SimpleSiteView;
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
    private SiteLocationType locationType;
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
    private Integer version;

    private List<Store> stores = new ArrayList<>();

    public Site() {
    }

    public Site(SimpleSiteView simpleSiteView) {
        this.id = simpleSiteView.getId();
        this.type = simpleSiteView.getType();
        this.locationType = simpleSiteView.getLocationType();
        this.address1 = simpleSiteView.getAddress1();
        this.address2 = simpleSiteView.getAddress2();
        this.city = simpleSiteView.getCity();
        this.state = simpleSiteView.getState();
        this.postalCode = simpleSiteView.getPostalCode();
        this.county = simpleSiteView.getCounty();
        this.country = simpleSiteView.getCountry();
        this.footprintSqft = simpleSiteView.getFootprintSqft();
        this.intersectionStreetPrimary = simpleSiteView.getIntersectionStreetPrimary();
        this.intersectionStreetSecondary = simpleSiteView.getIntersectionStreetSecondary();
        this.intersectionQuad = simpleSiteView.getIntersectionQuad();
        this.positionInCenter = simpleSiteView.getPositionInCenter();
        this.version = simpleSiteView.getVersion();

        if (simpleSiteView.getLocation() != null) {
            PointGeometry point = (PointGeometry) simpleSiteView.getLocation().getGeometry();
            this.location = new GeometryFactory().createPoint(new Coordinate(point.getCoordinates()[0], point.getCoordinates()[1]));
        }
    }

    public Site(SiteView siteView) {
        this((SimpleSiteView) siteView);

        if (siteView.getShoppingCenter() != null) {
            this.shoppingCenter = new ShoppingCenter(siteView.getShoppingCenter());
        }

        if (siteView.getStores() != null) {
            this.stores = siteView.getStores().stream().map(Store::new).collect(Collectors.toList());
        }
    }

    @PrePersist
    public void prePersist() {
        version = 1;
    }

    @PreUpdate
    public void preUpdate() {
        version++;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_site_id")
    @SequenceGenerator(name = "seq_site_id", sequenceName = "seq_site_id", allocationSize = 1)
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
    public SiteLocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(SiteLocationType locationType) {
        this.locationType = locationType;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @OneToMany(mappedBy = "site")
    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }
}
