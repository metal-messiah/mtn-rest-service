package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.StoreStatus;
import com.mtn.model.simpleView.SimpleStoreView;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreStatusView extends AuditingEntityView {

	private SimpleStoreView store;
	private String status;
	private LocalDateTime statusStartDate;
	private Integer legacyLocationId;
	private Integer legacyCasingId;

	public StoreStatusView(StoreStatus storeStatus) {
		super(storeStatus);

		this.store = new SimpleStoreView(storeStatus.getStore());
		this.status = storeStatus.getStatus();
		this.statusStartDate = storeStatus.getStatusStartDate();
		this.legacyLocationId = storeStatus.getLegacyLocationId();
		this.legacyCasingId = storeStatus.getLegacyCasingId();
	}

	public SimpleStoreView getStore() {
		return store;
	}

	public String getStatus() {
		return status;
	}

	public LocalDateTime getStatusStartDate() {
		return statusStartDate;
	}

	public Integer getLegacyLocationId() {
		return legacyLocationId;
	}

	public Integer getLegacyCasingId() {
		return legacyCasingId;
	}
}
