package com.mtn.model.view.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.search.StoreSearchResult;

/**
 * Created by Allen on 4/26/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreSearchResultView {

    private Integer id;
    private String name;

    public StoreSearchResultView() {
    }

    public StoreSearchResultView(StoreSearchResult searchResult) {
        this.id = searchResult.getStoreId();
        this.name = searchResult.getName();
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
}
