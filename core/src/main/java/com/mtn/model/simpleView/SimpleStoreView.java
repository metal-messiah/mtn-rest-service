package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.StoreType;
import com.mtn.model.domain.Store;
import com.mtn.model.utils.StoreUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleStoreView extends SimpleAuditingEntityView {

	private String storeName;
	private String storeNumber;
	private String currentStoreStatus;
	private Boolean floating;
	private StoreType storeType;

	private SimpleSiteView site;
	private SimpleBannerView banner;
	private SimpleStoreVolumeView latestStoreVolume;

	public SimpleStoreView() {
	}

	public SimpleStoreView(Store store) {
		super(store);
		this.storeName = store.getStoreName();
		this.storeNumber = store.getStoreNumber();
		StoreUtil.getLatestStatusAsOfDateTime(store, LocalDateTime.now())
				.ifPresent(storeStatus -> this.currentStoreStatus = storeStatus.getStatus());
		this.floating = store.getFloating();
		this.storeType = store.getStoreType();

		this.site = new SimpleSiteView(store.getSite());
		this.banner = (store.getBanner() != null) ? new SimpleBannerView(store.getBanner()) : null;
		this.latestStoreVolume = StoreUtil.getLatestVolumeAsOfDateTime(store, LocalDate.now())
				.map(SimpleStoreVolumeView::new).orElse(null);
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreNumber() {
		return storeNumber;
	}

	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}

	public String getCurrentStoreStatus() {
		return currentStoreStatus;
	}

	public void setCurrentStoreStatus(String currentStoreStatus) {
		this.currentStoreStatus = currentStoreStatus;
	}

	public Boolean getFloating() {
		return floating;
	}

	public void setFloating(Boolean floating) {
		this.floating = floating;
	}

	public StoreType getStoreType() {
		return storeType;
	}

	public void setStoreType(StoreType storeType) {
		this.storeType = storeType;
	}

	public SimpleSiteView getSite() {
		return site;
	}

	public void setSite(SimpleSiteView site) {
		this.site = site;
	}

	public SimpleBannerView getBanner() {
		return banner;
	}

	public void setBanner(SimpleBannerView banner) {
		this.banner = banner;
	}

	public SimpleStoreVolumeView getLatestStoreVolume() {
		return latestStoreVolume;
	}

	public void setLatestStoreVolume(SimpleStoreVolumeView latestStoreVolume) {
		this.latestStoreVolume = latestStoreVolume;
	}
}
