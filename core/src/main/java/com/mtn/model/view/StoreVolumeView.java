package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.VolumeType;
import com.mtn.model.domain.StoreVolume;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreVolumeView extends AuditingEntityView {

    private Integer id;
    private Integer volumeTotal;
    private LocalDateTime volumeDate;
    private VolumeType volumeType;
    private String source;
    private Integer legacyCasingId;

    public StoreVolumeView(StoreVolume volume) {
        super(volume);

        this.id = volume.getId();
        this.volumeTotal = volume.getVolumeTotal();
        this.volumeDate = volume.getVolumeDate();
        this.volumeType = volume.getVolumeType();
        this.source = volume.getSource();
        this.legacyCasingId = volume.getLegacyCasingId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
