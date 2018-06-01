package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.StoreStatus;
import com.mtn.model.simpleView.SimpleStoreView;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreStatusView extends AuditingEntityView {

	private Integer id;
	private SimpleStoreView store;
	private String status;
	private LocalDate statusStartDate;
	private Integer legacyLocationId;
	private Integer legacyCasingId;

	public StoreStatusView(StoreStatus storeStatus) {
		super(storeStatus);
		this.id = storeStatus.getId();
		this.store = new SimpleStoreView(storeStatus.getStore());
		this.status = storeStatus.getStatus();
		this.statusStartDate = storeStatus.getStatusStartDate();
		this.legacyLocationId = storeStatus.getLegacyLocationId();
		this.legacyCasingId = storeStatus.getLegacyCasingId();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SimpleStoreView getStore() {
		return store;
	}

	public void setStore(SimpleStoreView store) {
		this.store = store;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getStatusStartDate() {
		return statusStartDate;
	}

	public void setStatusStartDate(LocalDate statusStartDate) {
		this.statusStartDate = statusStartDate;
	}

	public Integer getLegacyLocationId() {
		return legacyLocationId;
	}

	public void setLegacyLocationId(Integer legacyLocationId) {
		this.legacyLocationId = legacyLocationId;
	}

	public Integer getLegacyCasingId() {
		return legacyCasingId;
	}

	public void setLegacyCasingId(Integer legacyCasingId) {
		this.legacyCasingId = legacyCasingId;
	}

}
