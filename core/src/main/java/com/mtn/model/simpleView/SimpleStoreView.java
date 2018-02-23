package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.StoreType;
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
    private StoreType storeType;
    private LocalDateTime dateOpened;
    private LocalDateTime dateClosed;
    private String storeNumber;
    private Integer legacyLocationId;
    private SimpleBannerView banner;

    public SimpleStoreView(Store store) {
        this.id = store.getId();
        this.storeName = store.getStoreName();
        this.storeType = store.getStoreType();
        this.dateOpened = store.getDateOpened();
        this.dateClosed = store.getDateClosed();
        this.legacyLocationId = store.getLegacyLocationId();
        this.storeNumber = store.getStoreNumber();
        Banner banner = store.getBanner();
        if (banner != null) {
            this.banner = new SimpleBannerView(store.getBanner());
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

    public SimpleBannerView getBanner() {
        return banner;
    }

    public void setBanner(SimpleBannerView banner) {
        this.banner = banner;
    }
}
