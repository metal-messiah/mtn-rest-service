package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreMergeRequest {

    private StoreView mergedStore;
    private List<Integer> storeIds;

    public StoreMergeRequest() {
    }

    public StoreView getMergedStore() {
        return mergedStore;
    }

    public void setMergedStore(StoreView mergedStore) {
        this.mergedStore = mergedStore;
    }

    public List<Integer> getStoreIds() {
        return storeIds;
    }

    public void setStoreIds(List<Integer> storeIds) {
        this.storeIds = storeIds;
    }
}
