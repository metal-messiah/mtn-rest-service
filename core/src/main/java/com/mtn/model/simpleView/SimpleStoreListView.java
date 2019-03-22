package com.mtn.model.simpleView;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.StoreList;

import com.mtn.model.simpleView.SimpleUserProfileView;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleStoreListView extends SimpleAuditingEntityView {

    private String storeListName;
    private Integer storeCount;
    private Integer subscriberCount;
    private List<SimpleUserProfileView> subscribers;
    private SimpleUserProfileView createdBy;

    public SimpleStoreListView() {
    }

    public SimpleStoreListView(StoreList storeList) {
        super(storeList);

        this.storeListName = storeList.getStoreListName();
        this.storeCount = storeList.getStores().size();
        this.subscriberCount = storeList.getSubscribers().size();
        this.subscribers = storeList.getSubscribers().stream().map(SimpleUserProfileView::new)
                .collect(Collectors.toList());
        this.createdBy = new SimpleUserProfileView(storeList.getCreatedBy());
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

    public List<SimpleUserProfileView> getSubscribers() {
        return this.subscribers;
    }

    public SimpleUserProfileView getCreatedBy() {
        return this.createdBy;
    }

}
