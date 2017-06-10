package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Store;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 4/26/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreView extends SimpleStoreView {

    private SimpleSiteView site;
    private SimpleUserProfileView createdBy;
    private LocalDateTime createdDate;
    private SimpleUserProfileView updatedBy;
    private LocalDateTime updatedDate;

    private CompanyView parentCompany;

    private List<SimpleStoreSurveyView> surveys = new ArrayList<>();

    public StoreView() {
    }

    public StoreView(Store store) {
        super(store);

        this.site = new SimpleSiteView(store.getSite());
        this.createdBy = new SimpleUserProfileView(store.getCreatedBy());
        this.createdDate = store.getCreatedDate();
        this.updatedBy = new SimpleUserProfileView(store.getUpdatedBy());
        this.updatedDate = store.getUpdatedDate();

        if (store.getParentCompany() != null) {
            this.parentCompany = new CompanyView(store.getParentCompany());
        }

        this.surveys = store.getSurveys().stream().map(SimpleStoreSurveyView::new).collect(Collectors.toList());
    }

    public SimpleSiteView getSite() {
        return site;
    }

    public void setSite(SimpleSiteView site) {
        this.site = site;
    }

    public SimpleUserProfileView getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(SimpleUserProfileView createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public SimpleUserProfileView getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(SimpleUserProfileView updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<SimpleStoreSurveyView> getSurveys() {
        return surveys;
    }

    public void setSurveys(List<SimpleStoreSurveyView> surveys) {
        this.surveys = surveys;
    }

    @Override
    public CompanyView getParentCompany() {
        return parentCompany;
    }

    public void setParentCompany(CompanyView parentCompany) {
        this.parentCompany = parentCompany;
    }
}
