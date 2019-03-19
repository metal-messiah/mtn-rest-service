package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.StoreList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleStoreListView extends SimpleAuditingEntityView {

    private String storeListName;
    private Integer storeCount;
    private Integer subscriberCount;
    private Integer createdById;

    public SimpleStoreListView() {
    }

    public SimpleStoreListView(StoreList storeList) {
        super(storeList);

        this.storeListName = storeList.getStoreListName();
        this.storeCount = storeList.getStores().size();
        this.subscriberCount = storeList.getSubscribers().size();
        this.createdById = storeList.getCreatedBy().getId();
    }

    public String getStoreListName() {
        return storeListName;
    }

    public void setStoreListName(String storeListName) {
        this.storeListName = storeListName;
    }

    public Integer getStoreCount() {
        return this.storeCount;
    }

    public Integer getSubscriberCount() {
        return this.subscriberCount;
    }

    public Integer getCreatedById() {
        return this.createdById;
    }

    public void getCreatedById(StoreList storeList) {
        this.createdById = storeList.getCreatedBy().getId();
    }
}
