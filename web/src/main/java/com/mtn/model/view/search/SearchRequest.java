package com.mtn.model.view.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.SearchType;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by Allen on 4/26/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchRequest {

    private SearchType searchType;
    private String name;
    private String owner;
    private String city;
    private String state;
    private String postalCode;
    private String county;

    private LocalDateTime openedBefore;
    private LocalDateTime openedAfter;
    private LocalDateTime closedBefore;
    private LocalDateTime closedAfter;

    public SearchType getSearchType() {
        return searchType;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public LocalDateTime getOpenedBefore() {
        return openedBefore.toLocalDate().atTime(LocalTime.MAX);
    }

    public void setOpenedBefore(LocalDateTime openedBefore) {
        this.openedBefore = openedBefore;
    }

    public LocalDateTime getOpenedAfter() {
        return openedAfter.toLocalDate().atStartOfDay();
    }

    public void setOpenedAfter(LocalDateTime openedAfter) {
        this.openedAfter = openedAfter;
    }

    public LocalDateTime getClosedBefore() {
        return closedBefore.toLocalDate().atTime(LocalTime.MAX);
    }

    public void setClosedBefore(LocalDateTime closedBefore) {
        this.closedBefore = closedBefore;
    }

    public LocalDateTime getClosedAfter() {
        return closedAfter.toLocalDate().atStartOfDay();
    }

    public void setClosedAfter(LocalDateTime closedAfter) {
        this.closedAfter = closedAfter;
    }
}
