package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ShoppingCenterTenant;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleShoppingCenterTenantView extends SimpleAuditingEntityView {

	private String name;
	private Boolean outparcel;

	public SimpleShoppingCenterTenantView() {
	}

	public SimpleShoppingCenterTenantView(ShoppingCenterTenant tenant) {
		super(tenant);
		this.name = tenant.getName();
		this.outparcel = tenant.getOutparcel();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getOutparcel() {
		return outparcel;
	}

	public void setOutparcel(Boolean outparcel) {
		this.outparcel = outparcel;
	}
}
