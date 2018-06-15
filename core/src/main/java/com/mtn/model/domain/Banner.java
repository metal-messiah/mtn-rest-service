package com.mtn.model.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tyler on 2/14/2018.
 */
@Entity
@Table
public class Banner extends AuditingEntity implements Identifiable {

    private Integer id;
    private String bannerName;
    private Company company;
    private Boolean isHistorical;
    private String defaultStoreFit;
    private Integer defaultSalesArea;
    private String logoFileName;

    private List<Store> stores = new ArrayList<>();

    @Id
    @GeneratedValue
    @Column(name = "banner_id")
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "company_id")
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    @Column(name = "is_historical")
    public Boolean getHistorical() {
        return isHistorical;
    }

    public void setHistorical(Boolean historical) {
        isHistorical = historical;
    }

    public String getDefaultStoreFit() {
        return defaultStoreFit;
    }

    public void setDefaultStoreFit(String defaultStoreFit) {
        this.defaultStoreFit = defaultStoreFit;
    }

    public Integer getDefaultSalesArea() {
        return defaultSalesArea;
    }

    public void setDefaultSalesArea(Integer defaultSalesArea) {
        this.defaultSalesArea = defaultSalesArea;
    }

    @OneToMany(mappedBy = "banner")
    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    public String getLogoFileName() {
        return logoFileName;
    }

    public void setLogoFileName(String logoFileName) {
        this.logoFileName = logoFileName;
    }
}
