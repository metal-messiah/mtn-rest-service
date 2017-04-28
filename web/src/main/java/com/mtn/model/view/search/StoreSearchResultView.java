package com.mtn.model.view.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.search.StoreSearchResult;

import java.time.LocalDateTime;

/**
 * Created by Allen on 4/26/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreSearchResultView {

    private Integer id;
    private LocalDateTime closedDate;
    private String name;
    private LocalDateTime openedDate;

    public StoreSearchResultView() {
    }

    public StoreSearchResultView(StoreSearchResult searchResult) {
        this.id = searchResult.getStoreId();
        this.name = searchResult.getName();
        this.closedDate = searchResult.getStoreClosedDate();
        this.openedDate = searchResult.getStoreOpenedDate();
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

    public LocalDateTime getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(LocalDateTime closedDate) {
        this.closedDate = closedDate;
    }

    public LocalDateTime getOpenedDate() {
        return openedDate;
    }

    public void setOpenedDate(LocalDateTime openedDate) {
        this.openedDate = openedDate;
    }
}
