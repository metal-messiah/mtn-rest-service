package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.StoreType;
import com.mtn.model.domain.Store;
import com.mtn.model.simpleView.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 4/26/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreView extends AuditingEntityView {

    private Integer id;
    private String storeName;
    private StoreType storeType;
    private LocalDateTime dateOpened;
    private LocalDateTime dateClosed;
    private String storeNumber;
    private Integer legacyLocationId;
    private SimpleStoreStatusView currentStoreStatus;
    private SimpleStoreSurveyView currentStoreSurvey;

    private SimpleSiteView site;
    private SimpleBannerView banner;

    private List<SimpleStoreCasingView> storeCasings;
    private List<SimpleStoreModelView> storeModels;
    private List<SimpleStoreVolumeView> storeVolumes;

    public StoreView(Store store) {
        super(store);

        this.id = store.getId();
        this.storeName = store.getStoreName();
        this.storeType = store.getStoreType();
        this.dateOpened = store.getDateOpened();
        this.dateClosed = store.getDateClosed();
        this.legacyLocationId = store.getLegacyLocationId();
        this.storeNumber = store.getStoreNumber();

        this.site = new SimpleSiteView(store.getSite());

        if (store.getCurrentStatus() != null) {
            this.currentStoreStatus = new SimpleStoreStatusView(store.getCurrentStatus());
        }
        if (store.getCurrentStoreSurvey() != null) {
            this.currentStoreSurvey = new SimpleStoreSurveyView((store.getCurrentStoreSurvey()));
        }
        if (store.getBanner() != null) {
            this.banner = new SimpleBannerView(store.getBanner());
        }

        if (store.getCasings() != null) {
            this.storeCasings = store.getCasings().stream().filter(casing -> casing.getDeletedDate() == null).map(SimpleStoreCasingView::new).collect(Collectors.toList());
        }
        if (store.getModels() != null) {
            this.storeModels = store.getModels().stream().filter(model -> model.getDeletedDate() == null).map(SimpleStoreModelView::new).collect(Collectors.toList());
        }
        if (store.getVolumes() != null) {
            this.storeVolumes = store.getVolumes().stream().filter(volume -> volume.getDeletedDate() == null).map(SimpleStoreVolumeView::new).collect(Collectors.toList());
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

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

    public SimpleSiteView getSite() {
        return site;
    }

    public void setSite(SimpleSiteView site) {
        this.site = site;
    }

    public SimpleBannerView getBanner() {
        return banner;
    }

    public void setBanner(SimpleBannerView banner) {
        this.banner = banner;
    }

    public List<SimpleStoreCasingView> getStoreCasings() {
        return storeCasings;
    }

    public void setStoreCasings(List<SimpleStoreCasingView> storeCasings) {
        this.storeCasings = storeCasings;
    }

    public List<SimpleStoreVolumeView> getStoreVolumes() {
        return storeVolumes;
    }

    public void setStoreVolumes(List<SimpleStoreVolumeView> storeVolumes) {
        this.storeVolumes = storeVolumes;
    }

    public List<SimpleStoreModelView> getStoreModels() {
        return storeModels;
    }

    public void setStoreModels(List<SimpleStoreModelView> storeModels) {
        this.storeModels = storeModels;
    }

    public SimpleStoreStatusView getCurrentStoreStatus() {
        return currentStoreStatus;
    }

    public void setCurrentStoreStatus(SimpleStoreStatusView currentStoreStatus) {
        this.currentStoreStatus = currentStoreStatus;
    }

    public SimpleStoreSurveyView getCurrentStoreSurvey() {
        return currentStoreSurvey;
    }

    public void setCurrentStoreSurvey(SimpleStoreSurveyView currentStoreSurvey) {
        this.currentStoreSurvey = currentStoreSurvey;
    }
}
