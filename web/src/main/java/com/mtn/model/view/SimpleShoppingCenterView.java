package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ShoppingCenter;

/**
 * Created by Allen on 4/23/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleShoppingCenterView {

    protected Integer id;
    protected String name;
    protected String owner;
    protected Integer version;

    public SimpleShoppingCenterView() {
    }

    public SimpleShoppingCenterView(ShoppingCenter shoppingCenter) {
        this.id = shoppingCenter.getId();
        this.name = shoppingCenter.getName();
        this.owner = shoppingCenter.getOwner();
        this.version = shoppingCenter.getVersion();
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
