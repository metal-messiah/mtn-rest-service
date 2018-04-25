package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Banner;
import com.mtn.model.domain.Store;
import com.mtn.model.domain.StoreStatus;

import java.time.LocalDateTime;

/**
 * Created by Allen on 4/26/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleStoreView {

    private Integer id;
    private SimpleSiteView site;
    private String storeName;
    private String storeNumber;
    private SimpleBannerView banner;
    private SimpleStoreStatusView currentStoreStatus;

    public SimpleStoreView(Store store) {
        this.id = store.getId();
        this.site = new SimpleSiteView(store.getSite());
        this.storeName = store.getStoreName();
        this.storeNumber = store.getStoreNumber();
        Banner banner = store.getBanner();
        if (banner != null) {
            this.banner = new SimpleBannerView(banner);
        }
        StoreStatus status = store.getCurrentStatus();
        if (status != null) {
            this.currentStoreStatus = new SimpleStoreStatusView(store.getCurrentStatus());
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
}
