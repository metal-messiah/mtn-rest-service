package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ShoppingCenter;

/**
 * Created by Allen on 4/23/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleShoppingCenterView {

	private Integer id;
	private String name;
	private String owner;
	private String centerType;

	public SimpleShoppingCenterView(ShoppingCenter shoppingCenter) {
		this.id = shoppingCenter.getId();
		this.name = shoppingCenter.getName();
		this.owner = shoppingCenter.getOwner();
		this.centerType = shoppingCenter.getCenterType();
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
