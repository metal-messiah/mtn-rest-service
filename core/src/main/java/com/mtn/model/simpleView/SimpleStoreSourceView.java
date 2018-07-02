package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.StoreSource;
import com.mtn.model.view.AuditingEntityView;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleStoreSourceView {

	private Integer id;
	private String sourceName;
	private String sourceNativeId;
	private String sourceStoreName;


	public SimpleStoreSourceView(StoreSource storeSource) {
		this.id = storeSource.getId();
		this.sourceName = storeSource.getSourceName();
		this.sourceNativeId = storeSource.getSourceNativeId();
		this.sourceStoreName = storeSource.getSourceStoreName();
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

	public String getSourceStoreName() {
		return sourceStoreName;
	}

	public void setSourceStoreName(String sourceStoreName) {
		this.sourceStoreName = sourceStoreName;
	}
}
