package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Banner;
import com.mtn.model.simpleView.SimpleStoreView;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BannerView extends AuditingEntityView {

    private String bannerName;
    private Boolean isHistorical;
    private String defaultStoreFit;
    private Integer defaultSalesArea;
    private String logoFileName;

    private CompanyView company;

    private List<SimpleStoreView> stores;

    public BannerView() {
    }

    public BannerView(Banner banner) {
        super(banner);

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

    public String getBannerName() {
        return bannerName;
    }

    public List<SimpleStoreView> getStores() {
        return stores;
    }

    public Boolean getHistorical() {
        return isHistorical;
    }

    public String getDefaultStoreFit() {
        return defaultStoreFit;
    }

    public Integer getDefaultSalesArea() {
        return defaultSalesArea;
    }

    public CompanyView getCompany() {
        return company;
    }

    public String getLogoFileName() {
        return logoFileName;
    }

}
