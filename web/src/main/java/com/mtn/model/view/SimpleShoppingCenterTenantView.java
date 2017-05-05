package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ShoppingCenterTenant;

/**
 * Created by Allen on 5/4/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleShoppingCenterTenantView {

    protected Integer id;
    protected String name;
    protected String type;
    protected Boolean isOutparcel = false;
    protected Integer sqft;

    public SimpleShoppingCenterTenantView() {
    }

    public SimpleShoppingCenterTenantView(ShoppingCenterTenant tenant) {
        this.id = tenant.getId();
        this.name = tenant.getName();
        this.type = tenant.getType();
        this.isOutparcel = tenant.getIsOutparcel();
        this.sqft = tenant.getSqft();
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIsOutparcel() {
        return isOutparcel;
    }

    public void setIsOutparcel(Boolean outparcel) {
        isOutparcel = outparcel;
    }

    public Integer getSqft() {
        return sqft;
    }

    public void setSqft(Integer sqft) {
        this.sqft = sqft;
    }
}
