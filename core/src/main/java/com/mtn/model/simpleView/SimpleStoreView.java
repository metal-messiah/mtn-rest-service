package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.StoreType;
import com.mtn.model.domain.Banner;
import com.mtn.model.domain.Store;
import com.mtn.model.domain.StoreStatus;
import com.mtn.model.domain.StoreVolume;

import java.util.Comparator;

/**
 * Created by Allen on 4/26/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleStoreView {

    private Integer id;
    private SimpleSiteView site;
    private String storeName;
    private String storeNumber;
    private Boolean floating;
    private StoreType storeType;
    private SimpleStoreVolumeView latestStoreVolume;

    private SimpleBannerView banner;
    private SimpleStoreStatusView currentStoreStatus;

    public SimpleStoreView(Store store) {
        this.id = store.getId();
        this.site = new SimpleSiteView(store.getSite());
        this.storeName = store.getStoreName();
        this.storeNumber = store.getStoreNumber();
        this.floating = store.getFloating();
        this.storeType = store.getStoreType();

        Banner banner = store.getBanner();
        if (banner != null) {
            this.banner = new SimpleBannerView(banner);
        }
        StoreStatus status = store.getCurrentStatus();
        if (status != null) {
            this.currentStoreStatus = new SimpleStoreStatusView(store.getCurrentStatus());
        }

        if (store.getVolumes() != null) {
            store.getVolumes().stream()
                    .max(Comparator.comparing(StoreVolume::getVolumeDate))
                    .ifPresent(latest -> this.latestStoreVolume = new SimpleStoreVolumeView(latest));
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SimpleSiteView getSite() {
        return site;
    }

    public void setSite(SimpleSiteView site) {
        this.site = site;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreNumber() {
        return storeNumber;
    }

    public void setStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }

    public SimpleBannerView getBanner() {
        return banner;
    }

    public void setBanner(SimpleBannerView banner) {
        this.banner = banner;
    }

    public SimpleStoreStatusView getCurrentStoreStatus() {
        return currentStoreStatus;
    }

    public void setCurrentStoreStatus(SimpleStoreStatusView currentStoreStatus) {
        this.currentStoreStatus = currentStoreStatus;
    }

    public Boolean getFloating() {
        return floating;
    }

    public void setFloating(Boolean floating) {
        this.floating = floating;
    }

    public StoreType getStoreType() {
        return storeType;
    }

    public void setStoreType(StoreType storeType) {
        this.storeType = storeType;
    }

    public SimpleStoreVolumeView getLatestStoreVolume() {
        return latestStoreVolume;
    }

    public void setLatestStoreVolume(SimpleStoreVolumeView latestStoreVolume) {
        this.latestStoreVolume = latestStoreVolume;
    }
}
