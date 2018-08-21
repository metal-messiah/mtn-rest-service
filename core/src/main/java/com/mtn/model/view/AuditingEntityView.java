package com.mtn.model.view;

import com.mtn.model.domain.AuditingEntity;
import com.mtn.model.simpleView.SimpleUserProfileView;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class AuditingEntityView implements Serializable {

	protected Integer id;
	protected SimpleUserProfileView createdBy;
	protected LocalDateTime createdDate;
	protected SimpleUserProfileView updatedBy;
	protected LocalDateTime updatedDate;
	protected Integer version;

	AuditingEntityView() {}

	AuditingEntityView(AuditingEntity entity) {
		this.id = entity.getId();
		this.createdBy = new SimpleUserProfileView(entity.getCreatedBy());
		this.createdDate = entity.getCreatedDate();
		this.updatedBy = new SimpleUserProfileView(entity.getUpdatedBy());
		this.updatedDate = entity.getUpdatedDate();
		this.version = entity.getVersion();
	}

	public Integer getId() {
		return id;
	}

	public SimpleUserProfileView getCreatedBy() {
		return createdBy;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public SimpleUserProfileView getUpdatedBy() {
		return updatedBy;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public Integer getVersion() {
		return version;
	}
}
