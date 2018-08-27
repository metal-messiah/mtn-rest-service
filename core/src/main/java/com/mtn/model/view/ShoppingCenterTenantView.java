package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ShoppingCenterTenant;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCenterTenantView extends AuditingEntityView {

	private String name;
	private Boolean isAnchor;
	private Boolean outparcel;
	private Integer tenantSqft;
	private Integer legacyCasingId;

	public ShoppingCenterTenantView() {
	}

	public ShoppingCenterTenantView(ShoppingCenterTenant tenant) {
		super(tenant);

		this.name = tenant.getName();
		this.isAnchor = tenant.getIsAnchor();
		this.outparcel = tenant.getOutparcel();
		this.tenantSqft = tenant.getTenantSqft();
		this.legacyCasingId = tenant.getLegacyCasingId();
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
		return outparcel;
	}

	public void setOutparcel(Boolean outparcel) {
		this.outparcel = outparcel;
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
