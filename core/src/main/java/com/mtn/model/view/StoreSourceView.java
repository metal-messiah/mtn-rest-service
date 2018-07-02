package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.StoreSource;
import com.mtn.model.simpleView.SimpleStoreView;
import com.mtn.model.simpleView.SimpleUserProfileView;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreSourceView extends AuditingEntityView {

	private Integer id;
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

	public StoreSourceView(StoreSource storeSource) {
		super(storeSource);
		this.id = storeSource.getId();
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getLegacySourceId() {
		return legacySourceId;
	}

	public void setLegacySourceId(Integer legacySourceId) {
		this.legacySourceId = legacySourceId;
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

	public SimpleUserProfileView getValidatedBy() {
		return validatedBy;
	}

	public void setValidatedBy(SimpleUserProfileView validatedBy) {
		this.validatedBy = validatedBy;
	}

	public SimpleStoreView getStore() {
		return store;
	}

	public void setStore(SimpleStoreView store) {
		this.store = store;
	}
}
