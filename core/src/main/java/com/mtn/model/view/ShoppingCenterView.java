package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ShoppingCenter;
import com.mtn.model.simpleView.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 4/23/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCenterView extends AuditingEntityView {

    private String name;
    private String owner;
    private String centerType;
    private Integer legacyLocationId;
    private List<SimpleShoppingCenterCasingView> shoppingCenterCasings;
    private List<SimpleSiteView> sites;

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

    public String getOwner() {
        return owner;
    }

    public String getCenterType() {
        return centerType;
    }

    public Integer getLegacyLocationId() {
        return legacyLocationId;
    }

    public List<SimpleShoppingCenterCasingView> getShoppingCenterCasings() {
        return shoppingCenterCasings;
    }

    public List<SimpleSiteView> getSites() {
        return sites;
    }
}
