package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Banner;
import com.mtn.model.domain.Store;

import java.time.LocalDateTime;

/**
 * Created by Allen on 4/26/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleStoreView {

    private Integer id;
    private String storeName;
    private String storeNumber;
    private String bannerName;
    private Integer latestSalesArea;
    private Integer latestTotalArea;
    private Integer latestVolume;
    private LocalDateTime latestVolumeDate;
    private String currentStoreStatus;

    public SimpleStoreView(Store store) {
        this.id = store.getId();
        this.storeName = store.getStoreName();
        this.storeNumber = store.getStoreNumber();
        Banner banner = store.getBanner();
        if (banner != null) {
            this.bannerName = store.getBanner().getBannerName();
        }
        this.latestSalesArea = store.getLatestSalesArea();
        this.latestTotalArea = store.getLatestTotalArea();
        this.latestVolume = store.getLatestVolume();
        this.latestVolumeDate = store.getLatestVolumeDate();
        this.currentStoreStatus = store.getCurrentStatus();
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

    public String getStoreNumber() {
        return storeNumber;
    }

    public void setStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public Integer getLatestSalesArea() {
        return latestSalesArea;
    }

    public void setLatestSalesArea(Integer latestSalesArea) {
        this.latestSalesArea = latestSalesArea;
    }

    public Integer getLatestTotalArea() {
        return latestTotalArea;
    }

    public void setLatestTotalArea(Integer latestTotalArea) {
        this.latestTotalArea = latestTotalArea;
    }

    public Integer getLatestVolume() {
        return latestVolume;
    }

    public void setLatestVolume(Integer latestVolume) {
        this.latestVolume = latestVolume;
    }

    public LocalDateTime getLatestVolumeDate() {
        return latestVolumeDate;
    }

    public void setLatestVolumeDate(LocalDateTime latestVolumeDate) {
        this.latestVolumeDate = latestVolumeDate;
    }

    public String getCurrentStoreStatus() {
        return currentStoreStatus;
    }

    public void setCurrentStoreStatus(String currentStoreStatus) {
        this.currentStoreStatus = currentStoreStatus;
    }
}
