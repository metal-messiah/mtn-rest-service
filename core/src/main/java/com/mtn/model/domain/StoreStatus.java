package com.mtn.model.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
public class StoreStatus extends AuditingEntity implements Identifiable {

    private Integer id;
    private Store store;
    private String status;
    private LocalDateTime statusStartDate;
    private Integer legacyLocationId;
    private Integer legacyCasingId;

    @Id
    @GeneratedValue
    @Column(name = "store_status_id")
    public Integer getId() {
        return id;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getStatusStartDate() {
        return statusStartDate;
    }

    public void setStatusStartDate(LocalDateTime statusStartDate) {
        this.statusStartDate = statusStartDate;
    }

    public Integer getLegacyLocationId() {
        return legacyLocationId;
    }

    public void setLegacyLocationId(Integer legacyLocationId) {
        this.legacyLocationId = legacyLocationId;
    }

    public Integer getLegacyCasingId() {
        return legacyCasingId;
    }

    public void setLegacyCasingId(Integer legacyCasingId) {
        this.legacyCasingId = legacyCasingId;
    }
}
