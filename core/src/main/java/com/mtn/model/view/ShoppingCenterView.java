package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ShoppingCenter;
import com.mtn.model.simpleView.SimpleShoppingCenterCasingView;
import com.mtn.model.simpleView.SimpleSiteView;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCenterView extends AuditingEntityView {

    private String name;
    private String owner;
    private String centerType;
    private Integer legacyLocationId;
    private List<SimpleShoppingCenterCasingView> shoppingCenterCasings;
    private List<SimpleSiteView> sites;

    public ShoppingCenterView() {
    }

    public ShoppingCenterView(ShoppingCenter shoppingCenter) {
        super(shoppingCenter);

        this.name = shoppingCenter.getName();
        this.owner = shoppingCenter.getOwner();
        this.centerType = shoppingCenter.getCenterType();
        this.legacyLocationId = shoppingCenter.getLegacyLocationId();
        if (shoppingCenter.getCasings() != null) {
            this.shoppingCenterCasings = shoppingCenter.getCasings().stream().filter(casing -> casing.getDeletedDate() == null).map(SimpleShoppingCenterCasingView::new).collect(Collectors.toList());
        }
        if (shoppingCenter.getSites() != null) {
            this.sites = shoppingCenter.getSites().stream().filter(site -> site.getDeletedDate() == null).map(SimpleSiteView::new).collect(Collectors.toList());
        }
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

    public Integer getLegacyLocationId() {
        return legacyLocationId;
    }

    public void setLegacyLocationId(Integer legacyLocationId) {
        this.legacyLocationId = legacyLocationId;
    }

    public List<SimpleShoppingCenterCasingView> getShoppingCenterCasings() {
        return shoppingCenterCasings;
    }

    public void setShoppingCenterCasings(List<SimpleShoppingCenterCasingView> shoppingCenterCasings) {
        this.shoppingCenterCasings = shoppingCenterCasings;
    }

    public List<SimpleSiteView> getSites() {
        return sites;
    }

    public void setSites(List<SimpleSiteView> sites) {
        this.sites = sites;
    }
}
