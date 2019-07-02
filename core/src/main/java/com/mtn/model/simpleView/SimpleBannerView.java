package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Banner;
import com.mtn.model.domain.Company;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleBannerView extends SimpleAuditingEntityView {

    private String bannerName;
    private String logoFileName;
    private String companyName;
    private String parentCompanyName;

    public SimpleBannerView() {
    }

    public SimpleBannerView(Banner banner) {
        super(banner);
        this.bannerName = banner.getBannerName();
        this.logoFileName = banner.getLogoFileName();
        Company company = banner.getCompany();
        if (company != null) {
            this.companyName = company.getCompanyName();
            this.parentCompanyName = this.getHighestParentCompanyName(company);
        }
    }

    private String getHighestParentCompanyName(Company company) {
        Company parentCompany = company.getParentCompany();
        if (parentCompany == null) {
            return company.getCompanyName();
        } else {
            return this.getHighestParentCompanyName(parentCompany);
        }
    }

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public String getLogoFileName() {
        return logoFileName;
    }

    public void setLogoFileName(String logoFileName) {
        this.logoFileName = logoFileName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getParentCompanyName() {
        return parentCompanyName;
    }

    public void setParentCompanyName(String parentCompanyName) {
        this.parentCompanyName = parentCompanyName;
    }
}
