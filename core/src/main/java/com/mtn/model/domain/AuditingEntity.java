package com.mtn.model.domain;

import javax.annotation.PreDestroy;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AuditingEntity implements Serializable {

    private Integer id;
    private UserProfile createdBy;
    private LocalDateTime createdDate;
    private UserProfile deletedBy;
    private LocalDateTime deletedDate;
    private UserProfile updatedBy;
    private LocalDateTime updatedDate;
    private Integer version;

    @PreDestroy
    public void genericPreDestroy() {
        this.deletedDate = LocalDateTime.now();
    }

    @PrePersist
    public void genericPrePersist() {
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }

    @PreUpdate
    public void genericPreUpdate() {
        this.updatedDate = LocalDateTime.now();

        if (this.deletedBy != null && this.deletedDate == null) {
            this.deletedDate = LocalDateTime.now();
        }
    }

    @Id
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "created_by")
    public UserProfile getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final UserProfile createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "created_date")
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @ManyToOne
    @JoinColumn(name = "deleted_by")
    public UserProfile getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(final UserProfile deletedBy) {
        this.deletedBy = deletedBy;
    }

    @Column(name = "deleted_date")
    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(final LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }

    @ManyToOne
    @JoinColumn(name = "updated_by")
    public UserProfile getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final UserProfile updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Column(name = "updated_date")
    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Version
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
