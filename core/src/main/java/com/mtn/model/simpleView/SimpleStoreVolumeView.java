package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.VolumeType;
import com.mtn.model.domain.StoreVolume;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleStoreVolumeView extends SimpleAuditingEntityView {

    private Integer volumeTotal;
    private LocalDate volumeDate;
    private VolumeType volumeType;

    public SimpleStoreVolumeView() {
    }

    public SimpleStoreVolumeView(StoreVolume storeVolume) {
        super(storeVolume);
        this.volumeTotal = storeVolume.getVolumeTotal();
        this.volumeDate = storeVolume.getVolumeDate();
        this.volumeType = storeVolume.getVolumeType();
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

    public VolumeType getVolumeType() {
        return volumeType;
    }

    public void setVolumeType(VolumeType volumeType) {
        this.volumeType = volumeType;
    }

}
