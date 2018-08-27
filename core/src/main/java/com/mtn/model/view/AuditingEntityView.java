package com.mtn.model.view;

import com.mtn.model.domain.AuditingEntity;
import com.mtn.model.simpleView.SimpleUserProfileView;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class AuditingEntityView implements Serializable {

	private Integer id;
	private SimpleUserProfileView createdBy;
	private LocalDateTime createdDate;
	private SimpleUserProfileView updatedBy;
	private LocalDateTime updatedDate;
	private Integer version;

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

	public void setId(Integer id) {
		this.id = id;
	}

	public SimpleUserProfileView getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(SimpleUserProfileView createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public SimpleUserProfileView getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(SimpleUserProfileView updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
