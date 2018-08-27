package com.mtn.model.simpleView;

import com.mtn.model.domain.AuditingEntity;

import java.io.Serializable;

public abstract class SimpleAuditingEntityView implements Serializable {

	private Integer id;

	SimpleAuditingEntityView() {}

	SimpleAuditingEntityView(AuditingEntity entity) {
		this.id = entity.getId();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
