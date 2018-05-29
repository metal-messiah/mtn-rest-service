package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Banner;

import java.io.Serializable;

/**
 * Created by Tyler on 2/14/2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleBannerView implements Serializable {

    private Integer id;
    private String bannerName;
    private String logoFileName;

    public SimpleBannerView(Banner banner) {
        this.id = banner.getId();
        this.bannerName = banner.getBannerName();
        this.logoFileName = banner.getLogoFileName();
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

    public String getLogoFileName() {
        return logoFileName;
    }

    public void setLogoFileName(String logoFileName) {
        this.logoFileName = logoFileName;
    }
}
