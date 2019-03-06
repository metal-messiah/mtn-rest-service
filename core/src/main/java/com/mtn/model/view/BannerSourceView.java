package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.BannerSource;
import com.mtn.model.simpleView.SimpleBannerView;
import com.mtn.model.simpleView.SimpleUserProfileView;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BannerSourceView extends AuditingEntityView {

	private String sourceName;
	private String sourceNativeId;
	private String sourceUrl;
	private String sourceBannerName;
	private LocalDateTime sourceCreatedDate;
	private LocalDateTime sourceEditedDate;
	private LocalDateTime sourceDeletedDate;

	private LocalDateTime validatedDate;
	private SimpleUserProfileView validatedBy;

	private SimpleBannerView banner;

	public BannerSourceView() {
	}

	public BannerSourceView(BannerSource bannerSource) {
		super(bannerSource);

		this.sourceName = bannerSource.getSourceName();
		this.sourceNativeId = bannerSource.getSourceNativeId();
		this.sourceUrl = bannerSource.getSourceUrl();
		this.sourceBannerName = bannerSource.getSourceBannerName();
		this.sourceCreatedDate = bannerSource.getSourceCreatedDate();
		this.sourceEditedDate = bannerSource.getSourceEditedDate();
		this.sourceDeletedDate = bannerSource.getSourceDeletedDate();

		this.validatedDate = bannerSource.getValidatedDate();
		if (bannerSource.getValidatedBy() != null) {
			this.validatedBy = new SimpleUserProfileView(bannerSource.getValidatedBy());
		}
		if (bannerSource.getBanner() != null) {
			this.banner = new SimpleBannerView(bannerSource.getBanner());
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

	public String getSourceBannerName() {
		return sourceBannerName;
	}

	public void setSourceBannerName(String sourceBannerName) {
		this.sourceBannerName = sourceBannerName;
	}

	public LocalDateTime getSourceCreatedDate() {
		return sourceCreatedDate;
	}

	public void setSourceCreatedDate(LocalDateTime sourceCreatedDate) {
		this.sourceCreatedDate = sourceCreatedDate;
	}

	public LocalDateTime getSourceEditedDate() {
		return sourceEditedDate;
	}

	public void setSourceEditedDate(LocalDateTime sourceEditedDate) {
		this.sourceEditedDate = sourceEditedDate;
	}

	public LocalDateTime getSourceDeletedDate() {
		return sourceDeletedDate;
	}

	public void setSourceDeletedDate(LocalDateTime sourceDeletedDate) {
		this.sourceDeletedDate = sourceDeletedDate;
	}

	public LocalDateTime getValidatedDate() {
		return validatedDate;
	}

	public void setValidatedDate(LocalDateTime validatedDate) {
		this.validatedDate = validatedDate;
	}

	public SimpleUserProfileView getValidatedBy() {
		return validatedBy;
	}

	public void setValidatedBy(SimpleUserProfileView validatedBy) {
		this.validatedBy = validatedBy;
	}

	public SimpleBannerView getBanner() {
		return banner;
	}

	public void setBanner(SimpleBannerView banner) {
		this.banner = banner;
	}
}
