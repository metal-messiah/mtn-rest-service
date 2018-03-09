package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ShoppingCenterTenant;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCenterTenantView extends AuditingEntityView {

	private Integer id;
	private String name;
	private Boolean isAnchor;
	private Boolean isOutparcel;
	private Integer tenantSqft;
	private Integer legacyCasingId;

	public ShoppingCenterTenantView(ShoppingCenterTenant tenant) {
		super(tenant);
		this.id = tenant.getId();
		this.name = tenant.getName();
		this.isAnchor = tenant.getIsAnchor();
		this.isOutparcel = tenant.getIsOutparcel();
		this.tenantSqft = tenant.getTenantSqft();
		this.legacyCasingId = tenant.getLegacyCasingId();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getAnchor() {
		return isAnchor;
	}

	public void setAnchor(Boolean anchor) {
		isAnchor = anchor;
	}

	public Boolean getOutparcel() {
		return isOutparcel;
	}

	public void setOutparcel(Boolean outparcel) {
		isOutparcel = outparcel;
	}

	public Integer getTenantSqft() {
		return tenantSqft;
	}

	public void setTenantSqft(Integer tenantSqft) {
		this.tenantSqft = tenantSqft;
	}

	public Integer getLegacyCasingId() {
		return legacyCasingId;
	}

	public void setLegacyCasingId(Integer legacyCasingId) {
		this.legacyCasingId = legacyCasingId;
	}
}
