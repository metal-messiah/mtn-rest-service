package com.mtn.model.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@AttributeOverride(name = "id", column = @Column(name = "store_list_id"))
public class StoreList extends AuditingEntity {

    private String storeListName;

    private List<StoreList> subscribedStoreLists = new ArrayList<>();
    private List<StoreList> createdStoreLists = new ArrayList<>();

    public String getStoreListName() {
        return storeListName;
    }

    public void setStoreListName(String storeListName) {
        this.storeListName = storeListName;
    }

    @ManyToMany
    @JoinTable(name = "user_store_list_subscriptions", joinColumns = @JoinColumn(name = "store_list_id", referencedColumnName = "store_list_id"), inverseJoinColumns = @JoinColumn(name = "user_profile_id", referencedColumnName = "user_profile_id"))
    public List<StoreList> getSubscribedStoreLists() {
        return subscribedStoreLists;
    }
}
