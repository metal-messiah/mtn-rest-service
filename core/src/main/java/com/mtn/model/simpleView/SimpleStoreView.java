package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.StoreType;
import com.mtn.model.domain.Store;
import com.mtn.model.utils.StoreUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleStoreView {

	private Integer id;
	private String storeName;
	private String storeNumber;
	private String currentStoreStatus;
	private Boolean floating;
	private StoreType storeType;

	private SimpleSiteView site;
	private SimpleBannerView banner;
	private SimpleStoreVolumeView latestStoreVolume;

	public SimpleStoreView(Store store) {
		this.id = store.getId();
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

	public Integer getId() {
		return id;
	}

	public String getStoreName() {
		return storeName;
	}

	public String getStoreNumber() {
		return storeNumber;
	}

	public String getCurrentStoreStatus() {
		return currentStoreStatus;
	}

	public Boolean getFloating() {
		return floating;
	}

	public StoreType getStoreType() {
		return storeType;
	}

	public SimpleSiteView getSite() {
		return site;
	}

	public SimpleBannerView getBanner() {
		return banner;
	}

	public SimpleStoreVolumeView getLatestStoreVolume() {
		return latestStoreVolume;
	}
}
