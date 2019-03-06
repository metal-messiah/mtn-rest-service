package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Source;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SourceView extends AuditingEntityView {

	private String sourceName;
	private LocalDateTime lastSyncDate;

	public SourceView() {
	}

	public SourceView(Source source) {
		super(source);

		this.sourceName = source.getSourceName();
		this.lastSyncDate = source.getLastSyncDate();
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public LocalDateTime getLastSyncDate() {
		return lastSyncDate;
	}

	public void setLastSyncDate(LocalDateTime lastSyncDate) {
		this.lastSyncDate = lastSyncDate;
	}
}
