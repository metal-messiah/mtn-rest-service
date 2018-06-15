package com.mtn.model.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 6/10/2017.
 */
@Entity
@Table
public class Company extends AuditingEntity implements Identifiable {

    private Integer id;
    private String companyName;
    private String websiteUrl;
    private Company parentCompany;

    private List<Company> childCompanies = new ArrayList<>();
    private List<Banner> banners = new ArrayList<>();

    @Id
    @GeneratedValue
    @Column(name = "company_id")
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
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

    @ManyToOne
    @JoinColumn(name = "parent_company_id")
    public Company getParentCompany() {
        return parentCompany;
    }

    public void setParentCompany(Company parentCompany) {
        this.parentCompany = parentCompany;
    }

    @OneToMany(mappedBy = "parentCompany")
    public List<Company> getChildCompanies() {
        return childCompanies;
    }

    public void setChildCompanies(List<Company> childCompanies) {
        this.childCompanies = childCompanies;
    }

    @OneToMany(mappedBy = "company")
    public List<Banner> getBanners() {
        return banners;
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
    }
}
