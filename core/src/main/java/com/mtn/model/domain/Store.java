package com.mtn.model.domain;

import com.mtn.constant.StoreType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 4/26/2017.
 */
@Entity
@Table
public class Store extends AuditingEntity implements Identifiable {

    private Integer id;
    private Site site;
    private String storeName;
    private String storeNumber;
    private StoreType storeType;
    private LocalDateTime dateOpened;
    private LocalDateTime dateClosed;
    private Integer legacyLocationId;
    private Banner banner;
    private StoreStatus currentStatus;

    private List<StoreCasing> casings = new ArrayList<>();
    private List<Interaction> interactions = new ArrayList<>();
    private List<StoreModel> models = new ArrayList<>();
    private List<StoreSurvey> surveys = new ArrayList<>();
    private List<StoreVolume> volumes = new ArrayList<>();

    public Store() {
    }

    @Id
    @GeneratedValue
    @Column(name = "store_id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    @ManyToOne
    @JoinColumn(name = "current_store_status_id")
    public StoreStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(StoreStatus currentStatus) {
        this.currentStatus = currentStatus;
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
    public List<Interaction> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<Interaction> interactions) {
        this.interactions = interactions;
    }

}
