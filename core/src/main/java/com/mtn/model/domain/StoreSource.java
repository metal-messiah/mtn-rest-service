package com.mtn.model.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Allen on 4/26/2017.
 */
@Entity
@Table
public class StoreSource extends AuditingEntity implements Identifiable {

    private Integer id;
    private String sourceName;
    private String sourceNativeId;
    private String sourceUrl;
    private Integer legacySourceId;
    private UserProfile validatedBy;
    private LocalDateTime validatedDate;
    private String sourceStoreName;
    private LocalDateTime sourceCreatedDate;
    private LocalDateTime sourceEditedDate;

    private Store store;

    @Id
    @GeneratedValue
    @Column(name = "source_id")
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "store_id")
    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
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
}
