package com.mtn.model.view;

import com.mtn.model.domain.AuditingEntity;
import com.mtn.model.simpleView.SimpleUserProfileView;

import java.time.LocalDateTime;

public abstract class AuditingEntityView {
	protected SimpleUserProfileView createdBy;
	protected LocalDateTime createdDate;
	protected SimpleUserProfileView updatedBy;
	protected LocalDateTime updatedDate;
	protected Integer version;

	AuditingEntityView() {}

	AuditingEntityView(AuditingEntity entity) {
		this.createdBy = new SimpleUserProfileView(entity.getCreatedBy());
		this.createdDate = entity.getCreatedDate();
		this.updatedBy = new SimpleUserProfileView(entity.getUpdatedBy());
		this.updatedDate = entity.getUpdatedDate();
		this.version = entity.getVersion();
	}

	final public SimpleUserProfileView getCreatedBy() {
		return createdBy;
	}

	final public void setCreatedBy(SimpleUserProfileView createdBy) {
		this.createdBy = createdBy;
	}

	final public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	final public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	final public SimpleUserProfileView getUpdatedBy() {
		return updatedBy;
	}

	final public void setUpdatedBy(SimpleUserProfileView updatedBy) {
		this.updatedBy = updatedBy;
	}

	final public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	final public Integer getVersion() {
		return version;
	}

	final public void setVersion(Integer version) {
		this.version = version;
	}

	final public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}
}
