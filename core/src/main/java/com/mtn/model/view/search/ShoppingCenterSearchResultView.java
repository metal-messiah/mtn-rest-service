package com.mtn.model.view.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.search.ShoppingCenterSearchResult;

/**
 * Created by Allen on 4/26/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCenterSearchResultView {

    private Integer id;
    private String name;
    private String owner;

    public ShoppingCenterSearchResultView() {
    }

    public ShoppingCenterSearchResultView(ShoppingCenterSearchResult searchResult) {
        this.id = searchResult.getShoppingCenterId();
        this.name = searchResult.getName();
        this.owner = searchResult.getOwner();
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
}
