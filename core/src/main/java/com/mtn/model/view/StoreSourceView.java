package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.StoreSource;
import com.mtn.model.simpleView.SimpleStoreView;
import com.mtn.model.simpleView.SimpleUserProfileView;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreSourceView extends AuditingEntityView {

	private String sourceName;
	private String sourceNativeId;
	private String sourceUrl;
	private Integer legacySourceId;
	private LocalDateTime validatedDate;
	private String sourceStoreName;
	private LocalDateTime sourceCreatedDate;
	private LocalDateTime sourceEditedDate;

	private SimpleUserProfileView validatedBy;
	private SimpleStoreView store;

	public StoreSourceView() {

	}

	public StoreSourceView(StoreSource storeSource) {
		super(storeSource);

		this.sourceName = storeSource.getSourceName();
		this.sourceNativeId = storeSource.getSourceNativeId();
		this.sourceUrl = storeSource.getSourceUrl();
		this.legacySourceId = storeSource.getLegacySourceId();
		this.validatedDate = storeSource.getValidatedDate();
		this.sourceStoreName = storeSource.getSourceStoreName();
		this.sourceCreatedDate = storeSource.getSourceCreatedDate();
		this.sourceEditedDate = storeSource.getSourceEditedDate();

		if (storeSource.getValidatedBy() != null) {
			this.validatedBy = new SimpleUserProfileView(storeSource.getValidatedBy());
		}
		if (storeSource.getStore() != null) {
			this.store = new SimpleStoreView(storeSource.getStore());
		}
	}

	public String getSourceName() {
		return sourceName;
	}

	public String getSourceNativeId() {
		return sourceNativeId;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public Integer getLegacySourceId() {
		return legacySourceId;
	}

	public LocalDateTime getValidatedDate() {
		return validatedDate;
	}

	public String getSourceStoreName() {
		return sourceStoreName;
	}

	public LocalDateTime getSourceCreatedDate() {
		return sourceCreatedDate;
	}

	public LocalDateTime getSourceEditedDate() {
		return sourceEditedDate;
	}

	public SimpleUserProfileView getValidatedBy() {
		return validatedBy;
	}

	public SimpleStoreView getStore() {
		return store;
	}
}
