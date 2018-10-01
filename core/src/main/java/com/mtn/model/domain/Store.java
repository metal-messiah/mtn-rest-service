package com.mtn.model.domain;

import com.mtn.constant.StoreType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@AttributeOverride(name="id", column=@Column(name="store_id"))
public class Store extends AuditingEntity {

    private String storeName;
    private String storeNumber;
    private StoreType storeType;
    private LocalDateTime dateOpened;
    private LocalDateTime dateClosed;
    private String fit;
    private String format;
    private Integer areaSales;
    private Double areaSalesPercentOfTotal;
    private Integer areaTotal;
    private Boolean areaIsEstimate = true;
    private Boolean storeIsOpen24 = false;
    private Boolean naturalFoodsAreIntegrated = false;
    private Boolean floating;
    private Integer legacyLocationId;
    private UserProfile validatedBy;
    private LocalDateTime validatedDate;

    private Site site;
    private Banner banner;

    private List<StoreCasing> casings = new ArrayList<>();
    private List<StoreModel> models = new ArrayList<>();
    private List<StoreSurvey> surveys = new ArrayList<>();
    private List<StoreVolume> volumes = new ArrayList<>();
    private List<StoreStatus> statuses = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "site_id")
    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    @Enumerated(EnumType.STRING)
    public StoreType getStoreType() {
        return storeType;
    }

    public void setStoreType(StoreType storeType) {
        this.storeType = storeType;
    }

    public LocalDateTime getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(LocalDateTime dateOpened) {
        this.dateOpened = dateOpened;
    }

    public LocalDateTime getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(LocalDateTime dateClosed) {
        this.dateClosed = dateClosed;
    }

    @Column(name = "store_fit")
    public String getFit() {
        return fit;
    }

    public void setFit(String fit) {
        this.fit = fit;
    }

    @Column(name = "store_format")
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getAreaSales() {
        return areaSales;
    }

    public void setAreaSales(Integer areaSales) {
        this.areaSales = areaSales;
    }

    public Double getAreaSalesPercentOfTotal() {
        return areaSalesPercentOfTotal;
    }

    public void setAreaSalesPercentOfTotal(Double areaSalesPercentOfTotal) {
        this.areaSalesPercentOfTotal = areaSalesPercentOfTotal;
    }

    public Integer getAreaTotal() {
        return areaTotal;
    }

    public void setAreaTotal(Integer areaTotal) {
        this.areaTotal = areaTotal;
    }

    public Boolean getAreaIsEstimate() {
        return areaIsEstimate;
    }

    public void setAreaIsEstimate(Boolean areaIsEstimate) {
        this.areaIsEstimate = areaIsEstimate;
    }
    @Column(name = "store_is_open_24")
    public Boolean getStoreIsOpen24() {
        return storeIsOpen24;
    }

    public void setStoreIsOpen24(Boolean storeIsOpen24) {
        this.storeIsOpen24 = storeIsOpen24;
    }

    public Boolean getNaturalFoodsAreIntegrated() {
        return naturalFoodsAreIntegrated;
    }

    public void setNaturalFoodsAreIntegrated(Boolean naturalFoodsAreIntegrated) {
        this.naturalFoodsAreIntegrated = naturalFoodsAreIntegrated;
    }

    @OneToMany(mappedBy = "store")
    public List<StoreSurvey> getSurveys() {
        return surveys;
    }

    public void setSurveys(List<StoreSurvey> surveys) {
        this.surveys = surveys;
    }

    @ManyToOne
    @JoinColumn(name = "banner_id")
    public Banner getBanner() {
        return banner;
    }

    public void setBanner(Banner banner) {
        this.banner = banner;
    }

    public String getStoreNumber() {
        return storeNumber;
    }

    public void setStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }

    public Integer getLegacyLocationId() {
        return legacyLocationId;
    }

    public void setLegacyLocationId(Integer legacyLocationId) {
        this.legacyLocationId = legacyLocationId;
    }

    @OneToMany(mappedBy = "store")
    public List<StoreVolume> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<StoreVolume> volumes) {
        this.volumes = volumes;
    }

    @OneToMany(mappedBy = "store")
    public List<StoreCasing> getCasings() {
        return casings;
    }

    public void setCasings(List<StoreCasing> casings) {
        this.casings = casings;
    }

    @OneToMany(mappedBy = "store")
    public List<StoreModel> getModels() {
        return models;
    }

    public void setModels(List<StoreModel> models) {
        this.models = models;
    }

    @OneToMany(mappedBy = "store")
    public List<StoreStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<StoreStatus> statuses) {
        this.statuses = statuses;
    }

    @Column(name = "is_float")
    public Boolean getFloating() {
        return floating;
    }

    public void setFloating(Boolean floating) {
        this.floating = floating;
    }

    @ManyToOne
    @JoinColumn(name = "validated_by")
    public UserProfile getValidatedBy() {
        return validatedBy;
    }

    public void setValidatedBy(UserProfile validatedBy) {
        this.validatedBy = validatedBy;
    }

    public LocalDateTime getValidatedDate() {
        return validatedDate;
    }

    public void setValidatedDate(LocalDateTime validatedDate) {
        this.validatedDate = validatedDate;
    }
}
