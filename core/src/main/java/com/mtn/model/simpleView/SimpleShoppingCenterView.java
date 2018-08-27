package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ShoppingCenter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleShoppingCenterView extends SimpleAuditingEntityView {

	private String name;
	private String owner;
	private String centerType;

	public SimpleShoppingCenterView() {
	}

	public SimpleShoppingCenterView(ShoppingCenter shoppingCenter) {
		super(shoppingCenter);
		this.name = shoppingCenter.getName();
		this.owner = shoppingCenter.getOwner();
		this.centerType = shoppingCenter.getCenterType();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getCenterType() {
		return centerType;
	}

	public void setCenterType(String centerType) {
		this.centerType = centerType;
	}
}
