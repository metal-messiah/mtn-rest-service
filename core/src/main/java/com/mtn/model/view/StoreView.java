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

    private SimpleSiteView site;
    private SimpleBannerView banner;

    private List<SimpleStoreCasingView> casings;
    private List<SimpleInteractionView> interactions;
    private List<SimpleStoreModelView> models;
    private List<SimpleStoreSurveyView> surveys;
    private List<StoreVolumeView> volumes;

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

        if (store.getBanner() != null) {
            this.banner = new SimpleBannerView(store.getBanner());
        }

        this.casings = store.getCasings().stream().filter(casing -> casing.getDeletedDate() == null).map(SimpleStoreCasingView::new).collect(Collectors.toList());
        this.interactions = store.getInteractions().stream().filter(interaction -> interaction.getDeletedDate() == null).map(SimpleInteractionView::new).collect(Collectors.toList());
        this.models = store.getModels().stream().filter(model -> model.getDeletedDate() == null).map(SimpleStoreModelView::new).collect(Collectors.toList());
        this.surveys = store.getSurveys().stream().filter(storeSurvey -> storeSurvey.getDeletedDate() == null).map(SimpleStoreSurveyView::new).collect(Collectors.toList());
        this.volumes = store.getVolumes().stream().filter(volume -> volume.getDeletedDate() == null).map(StoreVolumeView::new).collect(Collectors.toList());
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

    public List<SimpleStoreSurveyView> getSurveys() {
        return surveys;
    }

    public void setSurveys(List<SimpleStoreSurveyView> surveys) {
        this.surveys = surveys;
    }

    public SimpleBannerView getBanner() {
        return banner;
    }

    public void setBanner(SimpleBannerView banner) {
        this.banner = banner;
    }

    public List<SimpleStoreCasingView> getCasings() {
        return casings;
    }

    public void setCasings(List<SimpleStoreCasingView> casings) {
        this.casings = casings;
    }

    public List<StoreVolumeView> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<StoreVolumeView> volumes) {
        this.volumes = volumes;
    }

    public List<SimpleStoreModelView> getModels() {
        return models;
    }

    public void setModels(List<SimpleStoreModelView> models) {
        this.models = models;
    }

    public List<SimpleInteractionView> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<SimpleInteractionView> interactions) {
        this.interactions = interactions;
    }

}
