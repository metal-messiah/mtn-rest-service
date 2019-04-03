package com.mtn.model.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@AttributeOverride(name="id", column=@Column(name="source_id"))
public class BannerSourceSummary {

    @Id
    private Integer id;

    private String sourceName;
    private String sourceBannerName;
    private Integer totalStoreSources;
    private Integer matchedStoreSources;
    private Float percentComplete;
    private String matchingStatus;
    private Integer bannerId;
    private String bannerName;
    private String logoFileName;
    private LocalDateTime validatedDate;
    private String sourceUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceBannerName() {
        return sourceBannerName;
    }

    public void setSourceBannerName(String sourceBannerName) {
        this.sourceBannerName = sourceBannerName;
    }

    public Integer getTotalStoreSources() {
        return totalStoreSources;
    }

    public void setTotalStoreSources(Integer totalStoreSources) {
        this.totalStoreSources = totalStoreSources;
    }

    public Integer getMatchedStoreSources() {
        return matchedStoreSources;
    }

    public void setMatchedStoreSources(Integer matchedStoreSources) {
        this.matchedStoreSources = matchedStoreSources;
    }

    public String getMatchingStatus() {
        return matchingStatus;
    }

    public void setMatchingStatus(String matchingStatus) {
        this.matchingStatus = matchingStatus;
    }

    public Integer getBannerId() {
        return bannerId;
    }

    public void setBannerId(Integer bannerId) {
        this.bannerId = bannerId;
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

    public LocalDateTime getValidatedDate() {
        return validatedDate;
    }

    public void setValidatedDate(LocalDateTime validatedDate) {
        this.validatedDate = validatedDate;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public Float getPercentComplete() {
        return percentComplete;
    }

    public void setPercentComplete(Float percentComplete) {
        this.percentComplete = percentComplete;
    }
}
