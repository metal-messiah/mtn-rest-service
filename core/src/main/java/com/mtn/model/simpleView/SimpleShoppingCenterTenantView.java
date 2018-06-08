package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ShoppingCenterTenant;

/**
 * Created by Allen on 5/4/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleShoppingCenterTenantView {

	private Integer id;
	private String name;
	private Boolean outparcel;

	public SimpleShoppingCenterTenantView(ShoppingCenterTenant tenant) {
		this.id = tenant.getId();
		this.name = tenant.getName();
		this.outparcel = tenant.getOutparcel();
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

	public Boolean getOutparcel() {
		return outparcel;
	}

	public void setOutparcel(Boolean outparcel) {
		this.outparcel = outparcel;
	}
}
