package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Banner;
import com.mtn.model.domain.Company;
import com.mtn.model.simpleView.SimpleCompanyView;
import com.mtn.model.simpleView.SimpleStoreView;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Tyler on 2/14/2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BannerView extends AuditingEntityView implements Serializable {

    private Integer id;
    private String bannerName;
    private Boolean isHistorical;
    private String defaultStoreFit;
    private Integer defaultSalesArea;
    private String logoFileName;

    private CompanyView company;

    private List<SimpleStoreView> stores;

    public BannerView(Banner banner) {
        super(banner);

        this.id = banner.getId();
        this.bannerName = banner.getBannerName();
        this.isHistorical = banner.getHistorical();
        this.defaultStoreFit = banner.getDefaultStoreFit();
        this.defaultSalesArea = banner.getDefaultSalesArea();
        this.logoFileName = banner.getLogoFileName();

        if (banner.getCompany() != null) {
            this.company = new CompanyView(banner.getCompany());
        }

        this.stores = banner.getStores().stream().filter(store -> store.getDeletedDate() == null).map(SimpleStoreView::new).collect(Collectors.toList());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public List<SimpleStoreView> getStores() {
        return stores;
    }

    public void setStores(List<SimpleStoreView> stores) {
        this.stores = stores;
    }

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

    public CompanyView getCompany() {
        return company;
    }

    public void setCompany(CompanyView company) {
        this.company = company;
    }

    public String getLogoFileName() {
        return logoFileName;
    }

    public void setLogoFileName(String logoFileName) {
        this.logoFileName = logoFileName;
    }
}
