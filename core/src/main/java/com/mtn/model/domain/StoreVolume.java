package com.mtn.model.domain;

import com.mtn.constant.VolumeType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
public class StoreVolume extends AuditingEntity implements Identifiable {

    private Integer id;
    private Store store;
    private Integer volumeTotal;
    private LocalDateTime volumeDate;
    private VolumeType volumeType;
    private String source;
    private Integer version;
    private Integer legacyCasingId;

    @PrePersist
    public void prePersist() {
        version = 1;
    }

    @PreUpdate
    public void preUpdate() {
        version++;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_store_volume_id")
    @SequenceGenerator(name = "seq_store_volume_id", sequenceName = "seq_store_volume_id", allocationSize = 1)
    @Column(name = "store_volume_id")
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

    public Integer getVolumeTotal() {
        return volumeTotal;
    }

    public void setVolumeTotal(Integer volumeTotal) {
        this.volumeTotal = volumeTotal;
    }

    public LocalDateTime getVolumeDate() {
        return volumeDate;
    }

    public void setVolumeDate(LocalDateTime volumeDate) {
        this.volumeDate = volumeDate;
    }

    @Enumerated(EnumType.STRING)
    public VolumeType getVolumeType() {
        return volumeType;
    }

    public void setVolumeType(VolumeType volumeType) {
        this.volumeType = volumeType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getLegacyCasingId() {
        return legacyCasingId;
    }

    public void setLegacyCasingId(Integer legacyCasingId) {
        this.legacyCasingId = legacyCasingId;
    }
}
