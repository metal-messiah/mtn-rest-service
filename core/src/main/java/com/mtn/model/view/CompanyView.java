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
public class CompanyView extends AuditingEntityView implements Serializable {

    private Integer id;
    private String name;
    private String websiteUrl;

    private CompanyView parentCompany;

    private List<SimpleCompanyView> childCompanies;
    private List<SimpleStoreView> stores;

    public CompanyView(Company company) {
        super(company);

        this.id = company.getId();
        this.name = company.getName();
        this.websiteUrl = company.getWebsiteUrl();

        if (company.getParentCompany() != null) {
            this.parentCompany = new CompanyView(company.getParentCompany());
        }

        this.childCompanies = company.getChildCompanies().stream().map(SimpleCompanyView::new).collect(Collectors.toList());
        this.stores = company.getStores().stream().filter(store -> store.getDeletedDate() == null).map(SimpleStoreView::new).collect(Collectors.toList());
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

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
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

}
