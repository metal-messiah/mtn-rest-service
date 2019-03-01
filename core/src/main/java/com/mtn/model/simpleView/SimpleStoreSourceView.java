package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.StoreSource;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleStoreSourceView extends SimpleAuditingEntityView {

	private String sourceName;
	private String sourceNativeId;
	private String sourceUrl;
	private String sourceStoreName;
	private String sourceBannerName;
	private LocalDateTime validatedDate;
	private Integer storeId;

	public SimpleStoreSourceView() {
	}

	public SimpleStoreSourceView(StoreSource storeSource) {
		super(storeSource);

		this.sourceName = storeSource.getSourceName();
		this.sourceNativeId = storeSource.getSourceNativeId();
		this.sourceUrl = storeSource.getSourceUrl();
		this.validatedDate = storeSource.getValidatedDate();
		this.sourceStoreName = storeSource.getSourceStoreName();
		if (storeSource.getStore() != null) {
			this.storeId = storeSource.getStore().getId();
		}
		if (storeSource.getBannerSource() != null) {
			this.sourceBannerName = storeSource.getBannerSource().getSourceBannerName();
		}
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getSourceNativeId() {
		return sourceNativeId;
	}

	public void setSourceNativeId(String sourceNativeId) {
		this.sourceNativeId = sourceNativeId;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public LocalDateTime getValidatedDate() {
		return validatedDate;
	}

	public void setValidatedDate(LocalDateTime validatedDate) {
		this.validatedDate = validatedDate;
	}

	public String getSourceStoreName() {
		return sourceStoreName;
	}

	public void setSourceStoreName(String sourceStoreName) {
		this.sourceStoreName = sourceStoreName;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public String getSourceBannerName() {
		return sourceBannerName;
	}

	public void setSourceBannerName(String sourceBannerName) {
		this.sourceBannerName = sourceBannerName;
	}
}
