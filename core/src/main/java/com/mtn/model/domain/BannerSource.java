package com.mtn.model.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@AttributeOverride(name="id", column=@Column(name="source_id"))
public class BannerSource extends AuditingEntity {

    private String sourceName;
    private String sourceNativeId;
    private String sourceUrl;
    private String sourceBannerName;
    private LocalDateTime sourceCreatedDate;
    private LocalDateTime sourceEditedDate;
    private LocalDateTime sourceDeletedDate;

    private LocalDateTime validatedDate;
    private UserProfile validatedBy;

    private Banner banner;
    private List<StoreSource> storeSources;

    @ManyToOne
    @JoinColumn(name = "banner_id")
    public Banner getBanner() {
        return banner;
    }

    public void setBanner(Banner banner) {
        this.banner = banner;
    }

    @ManyToOne
    @JoinColumn(name = "validated_by")
    public UserProfile getValidatedBy() {
        return validatedBy;
    }

    public void setValidatedBy(UserProfile validatedBy) {
        this.validatedBy = validatedBy;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceNativeId() {
        return sourceNativeId;
    }

    public void setSourceNativeId(String sourceNativeId) {
        this.sourceNativeId = sourceNativeId;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getSourceBannerName() {
        return sourceBannerName;
    }

    public void setSourceBannerName(String sourceBannerName) {
        this.sourceBannerName = sourceBannerName;
    }

    public LocalDateTime getSourceCreatedDate() {
        return sourceCreatedDate;
    }

    public void setSourceCreatedDate(LocalDateTime sourceCreatedDate) {
        this.sourceCreatedDate = sourceCreatedDate;
    }

    public LocalDateTime getSourceEditedDate() {
        return sourceEditedDate;
    }

    public void setSourceEditedDate(LocalDateTime sourceEditedDate) {
        this.sourceEditedDate = sourceEditedDate;
    }

    public LocalDateTime getSourceDeletedDate() {
        return sourceDeletedDate;
    }

    public void setSourceDeletedDate(LocalDateTime sourceDeletedDate) {
        this.sourceDeletedDate = sourceDeletedDate;
    }

    public LocalDateTime getValidatedDate() {
        return validatedDate;
    }

    public void setValidatedDate(LocalDateTime validatedDate) {
        this.validatedDate = validatedDate;
    }

    @OneToMany(mappedBy = "bannerSource")
    public List<StoreSource> getStoreSources() {
        return storeSources;
    }

    public void setStoreSources(List<StoreSource> storeSources) {
        this.storeSources = storeSources;
    }
}
