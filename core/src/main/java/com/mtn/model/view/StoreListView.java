package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.StoreList;

import com.mtn.model.simpleView.*;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreListView extends AuditingEntityView {

	private String storeListName;

	private List<SimpleStoreView> stores;
	private List<SimpleUserProfileView> subscribers;

	public StoreListView() {
	}

	public StoreListView(StoreList storeList) {
		super(storeList);

		this.storeListName = storeList.getStoreListName();

		if (storeList.getSubscribers() != null) {
			this.subscribers = storeList.getSubscribers().stream().map(SimpleUserProfileView::new)
					.collect(Collectors.toList());
		}

		if (storeList.getStores() != null) {
			this.stores = storeList.getStores().stream().map(SimpleStoreView::new).collect(Collectors.toList());
		}
	}

	public String getStoreListName() {
		return storeListName;
	}

	public void setStoreListName(String storeListName) {
		this.storeListName = storeListName;
	}

	public List<SimpleUserProfileView> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(List<SimpleUserProfileView> subscribers) {
		this.subscribers = subscribers;
	}

	public List<SimpleStoreView> getStores() {
		return stores;
	}

	public void setStores(List<SimpleStoreView> stores) {
		this.stores = stores;
	}
}
