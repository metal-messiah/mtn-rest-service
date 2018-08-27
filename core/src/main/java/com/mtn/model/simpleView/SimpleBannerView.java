package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Banner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleBannerView extends SimpleAuditingEntityView {

    private String bannerName;
    private String logoFileName;

    public SimpleBannerView() {
    }

    public SimpleBannerView(Banner banner) {
        super(banner);
        this.bannerName = banner.getBannerName();
        this.logoFileName = banner.getLogoFileName();
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
}
