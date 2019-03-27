package com.mtn.model.domain;

import com.mtn.model.StoreChild;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@AttributeOverride(name="id", column=@Column(name="source_id"))
public class StoreSource extends AuditingEntity implements StoreChild {

    private String sourceName;
    private String sourceNativeId;
    private String sourceUrl;
    private String sourceStoreName;
    private LocalDateTime sourceCreatedDate;
    private LocalDateTime sourceEditedDate;
    private LocalDateTime sourceDeletedDate;
    private Integer legacySourceId;

    private LocalDateTime validatedDate;

    private UserProfile validatedBy;
    private Store store;
    private BannerSource bannerSource;

    @ManyToOne
    @JoinColumn(name = "store_id")
    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @ManyToOne
    @JoinColumn(name = "banner_source_id")
    public BannerSource getBannerSource() {
        return bannerSource;
    }

    public void setBannerSource(BannerSource bannerSource) {
        this.bannerSource = bannerSource;
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

    public Integer getLegacySourceId() {
        return legacySourceId;
    }

    public void setLegacySourceId(Integer legacySourceId) {
        this.legacySourceId = legacySourceId;
    }

    @ManyToOne
    @JoinColumn(name = "validated_by")
    public UserProfile getValidatedBy() {
        return validatedBy;
    }

    public void setValidatedBy(UserProfile validatedBy) {
        this.validatedBy = validatedBy;
    }

    public LocalDateTime getValidatedDate() {
        return validatedDate;
    }

    public void setValidatedDate(LocalDateTime validatedDate) {
        this.validatedDate = validatedDate;
    }

    public String getSourceStoreName() {
        return sourceStoreName;
    }

    public void setSourceStoreName(String sourceStoreName) {
        this.sourceStoreName = sourceStoreName;
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
}
