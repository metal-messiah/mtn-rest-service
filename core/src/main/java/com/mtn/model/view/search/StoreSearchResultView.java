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
    private LocalDateTime dateClosed;
    private String name;
    private LocalDateTime dateOpened;

    public StoreSearchResultView() {
    }

    public StoreSearchResultView(StoreSearchResult searchResult) {
        this.id = searchResult.getStoreId();
        this.name = searchResult.getName();
        this.dateClosed = searchResult.getDateClosed();
        this.dateOpened = searchResult.getDateOpened();
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

    public LocalDateTime getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(LocalDateTime dateClosed) {
        this.dateClosed = dateClosed;
    }

    public LocalDateTime getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(LocalDateTime dateOpened) {
        this.dateOpened = dateOpened;
    }
}
