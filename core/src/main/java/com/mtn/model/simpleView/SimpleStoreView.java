package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.StoreType;
import com.mtn.model.domain.Banner;
import com.mtn.model.domain.Store;
import com.mtn.model.utils.StoreUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleStoreView {

	private Store store;

	public SimpleStoreView(Store store) {
		this.store = store;
	}

	public Integer getId() {
		return store.getId();
	}

	public SimpleSiteView getSite() {
		return new SimpleSiteView(this.store.getSite());
	}

	public String getStoreName() {
		return this.store.getStoreName();
	}

	public String getStoreNumber() {
		return this.store.getStoreNumber();
	}

	public SimpleBannerView getBanner() {
		Banner banner = this.store.getBanner();
		if (banner != null) {
			return new SimpleBannerView(banner);
		} else {
			return null;
		}
	}

	public SimpleStoreStatusView getCurrentStoreStatus() {
		return StoreUtil.getLatestStatusAsOfDateTime(this.store, LocalDateTime.now())
				.map(SimpleStoreStatusView::new).orElse(null);
	}

	public Boolean getFloating() {
		return this.store.getFloating();
	}

	public StoreType getStoreType() {
		return this.store.getStoreType();
	}

	public SimpleStoreVolumeView getLatestStoreVolume() {
		return StoreUtil.getLatestVolumeAsOfDateTime(store, LocalDate.now())
				.map(SimpleStoreVolumeView::new).orElse(null);
	}

}
