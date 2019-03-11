package com.mtn.model.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@AttributeOverride(name = "id", column = @Column(name = "store_list_id"))
public class StoreList extends AuditingEntity {

    private String storeListName;

    private List<Store> stores = new ArrayList<>();
    private List<UserProfile> subscribers = new ArrayList<>();

    public String getStoreListName() {
        return storeListName;
    }

    public void setStoreListName(String storeListName) {
        this.storeListName = storeListName;
    }

    @ManyToMany
    @JoinTable(name = "user_store_list_subscriptions", joinColumns = @JoinColumn(name = "store_list_id", referencedColumnName = "store_list_id"), inverseJoinColumns = @JoinColumn(name = "user_profile_id", referencedColumnName = "user_profile_id"))
    public List<UserProfile> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<UserProfile> subscribers) {
        this.subscribers = subscribers;
    }

    @ManyToMany
    @JoinTable(name = "store_list_stores", joinColumns = @JoinColumn(name = "store_list_id", referencedColumnName = "store_list_id"), inverseJoinColumns = @JoinColumn(name = "store_id", referencedColumnName = "store_id"))
    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }
}
