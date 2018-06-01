package com.mtn.model.domain;

import com.mtn.constant.VolumeType;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class StoreVolume extends AuditingEntity implements Identifiable {

    private Integer id;
    private Store store;
    private Integer volumeTotal;
    private LocalDate volumeDate;
    private VolumeType volumeType;
    private String source;
    private Integer legacyCasingId;

    @Id
    @GeneratedValue
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

    public LocalDate getVolumeDate() {
        return volumeDate;
    }

    public void setVolumeDate(LocalDate volumeDate) {
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

    public Integer getLegacyCasingId() {
        return legacyCasingId;
    }

    public void setLegacyCasingId(Integer legacyCasingId) {
        this.legacyCasingId = legacyCasingId;
    }
}
