package com.mtn.model.view.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.search.ShoppingCenterSearchResult;
import com.mtn.model.domain.search.StoreSearchResult;

/**
 * Created by Allen on 4/26/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResultView {

    private ShoppingCenterSearchResultView shoppingCenter;
    private SiteSearchResultView site;
    private StoreSearchResultView store;

    public SearchResultView() {
    }

    public SearchResultView(ShoppingCenterSearchResult searchResult) {
        if (searchResult.getShoppingCenterId() != null) {
            this.shoppingCenter = new ShoppingCenterSearchResultView(searchResult);
        }
        if (searchResult.getSiteId() != null) {
            this.site = new SiteSearchResultView(searchResult);
        }
    }

    public SearchResultView(StoreSearchResult searchResult) {
        if (searchResult.getStoreId() != null) {
            this.store = new StoreSearchResultView(searchResult);
        }
        if (searchResult.getSiteId() != null) {
            this.site = new SiteSearchResultView(searchResult);
        }
    }

    public ShoppingCenterSearchResultView getShoppingCenter() {
        return shoppingCenter;
    }

    public void setShoppingCenter(ShoppingCenterSearchResultView shoppingCenter) {
        this.shoppingCenter = shoppingCenter;
    }

    public SiteSearchResultView getSite() {
        return site;
    }

    public void setSite(SiteSearchResultView site) {
        this.site = site;
    }

    public StoreSearchResultView getStore() {
        return store;
    }

    public void setStore(StoreSearchResultView store) {
        this.store = store;
    }
}
