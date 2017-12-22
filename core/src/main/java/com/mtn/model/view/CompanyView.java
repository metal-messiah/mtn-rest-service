package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Company;
import com.mtn.model.simpleView.SimpleCompanyView;
import com.mtn.model.simpleView.SimpleStoreView;
import com.mtn.model.simpleView.SimpleUserProfileView;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 6/10/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyView extends SimpleCompanyView implements Serializable {

    private CompanyView parentCompany;
    private SimpleUserProfileView createdBy;
    private LocalDateTime createdDate;
    private SimpleUserProfileView updatedBy;
    private LocalDateTime updatedDate;
    private Integer version;

    private List<SimpleCompanyView> childCompanies = new ArrayList<>();
    private List<SimpleStoreView> stores = new ArrayList<>();

    public CompanyView() {
        super();
    }

    public CompanyView(Company company) {
        super(company);

        if (company.getParentCompany() != null) {
            this.parentCompany = new CompanyView(company.getParentCompany());
        }

        this.childCompanies = company.getChildCompanies().stream().map(SimpleCompanyView::new).collect(Collectors.toList());
        this.stores = company.getStores().stream().filter(store -> store.getDeletedDate() == null).map(SimpleStoreView::new).collect(Collectors.toList());
    }

    public CompanyView getParentCompany() {
        return parentCompany;
    }

    public void setParentCompany(CompanyView parentCompany) {
        this.parentCompany = parentCompany;
    }

    public List<SimpleCompanyView> getChildCompanies() {
        return childCompanies;
    }

    public void setChildCompanies(List<SimpleCompanyView> childCompanies) {
        this.childCompanies = childCompanies;
    }

    public List<SimpleStoreView> getStores() {
        return stores;
    }

    public void setStores(List<SimpleStoreView> stores) {
        this.stores = stores;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
