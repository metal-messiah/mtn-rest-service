package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Company;
import com.mtn.model.simpleView.SimpleBannerView;
import com.mtn.model.simpleView.SimpleCompanyView;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyView extends AuditingEntityView {

    private String companyName;
    private String websiteUrl;

    private CompanyView parentCompany;

    private List<SimpleCompanyView> childCompanies;
    private List<SimpleBannerView> banners;

    public CompanyView() {
    }

    public CompanyView(Company company) {
        super(company);

        this.companyName = company.getCompanyName();
        this.websiteUrl = company.getWebsiteUrl();

        if (company.getParentCompany() != null) {
            this.parentCompany = new CompanyView(company.getParentCompany());
        }

        this.childCompanies = company.getChildCompanies().stream().map(SimpleCompanyView::new).collect(Collectors.toList());
        this.banners = company.getBanners().stream().filter(store -> store.getDeletedDate() == null).map(SimpleBannerView::new).collect(Collectors.toList());
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public void setParentCompany(CompanyView parentCompany) {
        this.parentCompany = parentCompany;
    }

    public void setChildCompanies(List<SimpleCompanyView> childCompanies) {
        this.childCompanies = childCompanies;
    }

    public void setBanners(List<SimpleBannerView> banners) {
        this.banners = banners;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public CompanyView getParentCompany() {
        return parentCompany;
    }

    public List<SimpleCompanyView> getChildCompanies() {
        return childCompanies;
    }

    public List<SimpleBannerView> getBanners() {
        return banners;
    }

}
