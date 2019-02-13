package com.mtn.model.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@AttributeOverride(name="id", column=@Column(name="source_id"))
public class Source extends AuditingEntity {

    private String sourceName;
    private LocalDateTime lastSyncDate;

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public LocalDateTime getLastSyncDate() {
        return lastSyncDate;
    }

    public void setLastSyncDate(LocalDateTime lastSyncDate) {
        this.lastSyncDate = lastSyncDate;
    }
}
