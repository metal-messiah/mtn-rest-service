package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.ModelType;
import com.mtn.constant.VolumeType;
import com.mtn.model.domain.StoreModel;
import com.mtn.model.domain.StoreVolume;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleStoreVolumeView {

    private Integer id;
    private Integer volumeTotal;
    private LocalDateTime volumeDate;
    private VolumeType volumeType;

    public SimpleStoreVolumeView(StoreVolume storeVolume) {
        this.id = storeVolume.getId();
        this.volumeTotal = storeVolume.getVolumeTotal();
        this.volumeDate = storeVolume.getVolumeDate();
        this.volumeType = storeVolume.getVolumeType();
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
}
