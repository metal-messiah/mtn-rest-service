package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.StoreType;
import com.mtn.model.domain.Store;

import java.time.LocalDateTime;

/**
 * Created by Allen on 4/26/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleStoreView {

    protected Integer id;
    protected String name;
    protected StoreType type;
    protected LocalDateTime openedDate;
    protected LocalDateTime closedDate;
    protected Integer version;
    private String storeNumber;
    private Integer legacyLocationId;
    protected SimpleCompanyView parentCompany;

    public SimpleStoreView() {
    }

    public SimpleStoreView(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.type = store.getType();
        this.openedDate = store.getOpenedDate();
        this.closedDate = store.getClosedDate();
        this.version = store.getVersion();
        this.legacyLocationId = store.getLegacyLocationId();
        this.storeNumber = store.getStoreNumber();

        if (store.getParentCompany() != null) {
            this.parentCompany = new SimpleCompanyView(store.getParentCompany());
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StoreType getType() {
        return type;
    }

    public void setType(StoreType type) {
        this.type = type;
    }

    public LocalDateTime getOpenedDate() {
        return openedDate;
    }

    public void setOpenedDate(LocalDateTime openedDate) {
        this.openedDate = openedDate;
    }

    public LocalDateTime getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(LocalDateTime closedDate) {
        this.closedDate = closedDate;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public SimpleCompanyView getParentCompany() {
        return parentCompany;
    }

    public void setParentCompany(SimpleCompanyView parentCompany) {
        this.parentCompany = parentCompany;
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
}
