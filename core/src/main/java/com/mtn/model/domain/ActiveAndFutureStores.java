package com.mtn.model.domain;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Immutable
@Table
public class ActiveAndFutureStores {

    @Id
    private Integer storeId;
    private Float latitude;
    private Float longitude;
    @Column(name = "address_1")
    private String address1;
    @Column(name = "address_2")
    private String address2;
    private String city;
    private String county;
    private String state;
    private String postalCode;
    private String quad;
    private String intersectionStreetPrimary;
    private String intersectionStreetSecondary;
    private String storeType;
    private String storeName;
    private String storeNumber;
    private String storeStatus;
    private Float storeStrength;
    private String storeFit;
    private String usingDefaultFit;
    private Integer bannerId;
    private String bannerName;
    private Integer companyId;
    private String companyName;
    private Integer parentCompanyId;
    private String parentCompanyName;
    private Integer areaSales;
    private Integer areaTotal;
    private String volumeChoice;
    private Integer volumeTotal;
    private LocalDate volumeDate;
    private Float shoppingCenterFeatureScore;
    private String ratingSynergy;
    private Integer tenantVacantCount;
    private Integer tenantOccupiedCount;
    private Integer parkingSpaceCount;
    private Integer legacyLocationId;
    private Integer cbsaId;
    private Float scConditionScore;
    private String volumeConfidence;
    private Integer power;
    private LocalDate powerDate;

    @Transient
    private String usingAreaSalesFrom;
    @Transient
    private String usingAreaTotalFrom;
    @Transient
    private Integer pseudoPower;
    @Transient
    private Float dollarsPerSqft;

    public Integer getStoreId() {
        return storeId;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Float getLongitude() {
        return longitude;
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

    public String getCounty() {
        return county;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getQuad() {
        return quad;
    }

    public String getIntersectionStreetPrimary() {
        return intersectionStreetPrimary;
    }

    public String getIntersectionStreetSecondary() {
        return intersectionStreetSecondary;
    }

    public String getStoreType() {
        return storeType;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getStoreNumber() {
        return storeNumber;
    }

    public String getStoreStatus() {
        return storeStatus;
    }

    public Float getStoreStrength() {
        return storeStrength;
    }

    public String getStoreFit() {
        return storeFit;
    }

    public String getUsingDefaultFit() {
        return usingDefaultFit;
    }

    public Integer getBannerId() {
        return bannerId;
    }

    public String getBannerName() {
        return bannerName;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Integer getParentCompanyId() {
        return parentCompanyId;
    }

    public String getParentCompanyName() {
        return parentCompanyName;
    }

    public Integer getAreaSales() {
        return areaSales;
    }

    public Integer getAreaTotal() {
        return areaTotal;
    }

    public String getVolumeChoice() {
        return volumeChoice;
    }

    public Integer getVolumeTotal() {
        return volumeTotal;
    }

    public LocalDate getVolumeDate() {
        return volumeDate;
    }

    public Float getShoppingCenterFeatureScore() {
        return shoppingCenterFeatureScore;
    }

    public String getRatingSynergy() {
        return ratingSynergy;
    }

    public Integer getTenantVacantCount() {
        return tenantVacantCount;
    }

    public Integer getTenantOccupiedCount() {
        return tenantOccupiedCount;
    }

    public Integer getParkingSpaceCount() {
        return parkingSpaceCount;
    }

    public Integer getLegacyLocationId() {
        return legacyLocationId;
    }

    public Integer getCbsaId() {
        return cbsaId;
    }

    public Float getScConditionScore() {
        return scConditionScore;
    }

    public String getVolumeConfidence() {
        return volumeConfidence;
    }

    public Integer getPower() {
        return power;
    }

    public LocalDate getPowerDate() {
        return powerDate;
    }

    public String getUsingAreaSalesFrom() {
        return usingAreaSalesFrom;
    }

    public String getUsingAreaTotalFrom() {
        return usingAreaTotalFrom;
    }

    public Integer getPseudoPower() {
        return pseudoPower;
    }

    public Float getDollarsPerSqft() {
        return dollarsPerSqft;
    }

    public void setUsingAreaSalesFrom(String usingAreaSalesFrom) {
        this.usingAreaSalesFrom = usingAreaSalesFrom;
    }

    public void setUsingAreaTotalFrom(String usingAreaTotalFrom) {
        this.usingAreaTotalFrom = usingAreaTotalFrom;
    }

    public void setPseudoPower(Integer pseudoPower) {
        this.pseudoPower = pseudoPower;
    }

    public void setDollarsPerSqft(Float dollarsPerSqft) {
        this.dollarsPerSqft = dollarsPerSqft;
    }
}
