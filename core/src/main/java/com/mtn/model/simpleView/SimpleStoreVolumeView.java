package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.VolumeType;
import com.mtn.model.domain.StoreVolume;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleStoreVolumeView {

    protected Integer id;
    protected Integer volumeTotal;
    protected LocalDateTime volumeDate;
    protected VolumeType volumeType;
    protected String source;
    protected Integer version;
    protected Integer legacyCasingId;
    protected SimpleUserProfileView createdBy;
    protected LocalDateTime createdDate;
    protected SimpleUserProfileView updatedBy;
    protected LocalDateTime updatedDate;

    public SimpleStoreVolumeView() {
    }

    public SimpleStoreVolumeView(StoreVolume volume) {
        this.id = volume.getId();
        this.volumeTotal = volume.getVolumeTotal();
        this.volumeDate = volume.getVolumeDate();
        this.volumeType = volume.getVolumeType();
        this.source = volume.getSource();
        this.version = volume.getVersion();
        this.legacyCasingId = volume.getLegacyCasingId();
        this.createdBy = new SimpleUserProfileView(volume.getCreatedBy());
        this.createdDate = volume.getCreatedDate();
        this.updatedBy = new SimpleUserProfileView(volume.getUpdatedBy());
        this.updatedDate = volume.getUpdatedDate();
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

    public SimpleUserProfileView getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(SimpleUserProfileView createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public SimpleUserProfileView getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(SimpleUserProfileView updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
}
