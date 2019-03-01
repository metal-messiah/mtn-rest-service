package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.BannerSource;
import com.mtn.model.domain.StoreSource;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleBannerSourceView extends SimpleAuditingEntityView {

	private String sourceName;
	private String sourceBannerName;

	private SimpleBannerView banner;
	private String status;
	private Integer storeSourceCount;

	public SimpleBannerSourceView() {
	}

	public SimpleBannerSourceView(BannerSource bannerSource) {
		super(bannerSource);

		this.sourceName = bannerSource.getSourceName();
		this.sourceBannerName = bannerSource.getSourceBannerName();

		if (bannerSource.getBanner() != null) {
			this.banner = new SimpleBannerView(bannerSource.getBanner());
		}

		List<StoreSource> storeSources = bannerSource.getStoreSources();
		if (storeSources != null) {
			this.storeSourceCount = storeSources.size();
			if (bannerSource.getSourceDeletedDate() != null) {
				this.status = "DELETED";
			} else if (storeSources.stream().anyMatch(storeSource -> storeSource.getStore() == null)) {
				this.status = "INCOMPLETE";
			} else {
				this.status = "COMPLETE";
			}
		} else {
			this.storeSourceCount = 0;
			this.status = "INCOMPLETE";
		}
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getSourceBannerName() {
		return sourceBannerName;
	}

	public void setSourceBannerName(String sourceBannerName) {
		this.sourceBannerName = sourceBannerName;
	}

	public SimpleBannerView getBanner() {
		return banner;
	}

	public void setBanner(SimpleBannerView banner) {
		this.banner = banner;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getStoreSourceCount() {
		return storeSourceCount;
	}

	public void setStoreSourceCount(Integer storeSourceCount) {
		this.storeSourceCount = storeSourceCount;
	}
}
