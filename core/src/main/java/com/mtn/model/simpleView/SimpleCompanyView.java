package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Company;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleCompanyView extends SimpleAuditingEntityView {

    private String companyName;
    private String websiteUrl;

    public SimpleCompanyView() {
    }

    public SimpleCompanyView(Company company) {
        super(company);
        this.companyName = company.getCompanyName();
        this.websiteUrl = company.getWebsiteUrl();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }
}
