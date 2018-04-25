package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.ConfidenceType;
import com.mtn.model.domain.StoreCasing;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleStoreCasingView {

    private Integer id;
    private LocalDateTime casingDate;
    private String note;
    private SimpleStoreStatusView storeStatus;
    private String volumeNote;
    private ConfidenceType volumeConfidence;
    private SimpleStoreVolumeView storeVolume;

    public SimpleStoreCasingView(StoreCasing casing) {
        this.id = casing.getId();
        this.casingDate = casing.getCasingDate();
        this.note = casing.getNote();
        this.volumeNote = casing.getVolumeNote();
        this.volumeConfidence = casing.getVolumeConfidence();
        if (casing.getStoreStatus() != null) {
            this.storeStatus = new SimpleStoreStatusView(casing.getStoreStatus());
        }
        if (casing.getStoreVolume() != null) {
            this.storeVolume = new SimpleStoreVolumeView(casing.getStoreVolume());
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getCasingDate() {
        return casingDate;
    }

    public void setCasingDate(LocalDateTime casingDate) {
        this.casingDate = casingDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public SimpleStoreStatusView getStoreStatus() {
        return storeStatus;
    }

    public void setStoreStatus(SimpleStoreStatusView storeStatus) {
        this.storeStatus = storeStatus;
    }

    public String getVolumeNote() {
        return volumeNote;
    }

    public void setVolumeNote(String volumeNote) {
        this.volumeNote = volumeNote;
    }

    public SimpleStoreVolumeView getStoreVolume() {
        return storeVolume;
    }

    public void setStoreVolume(SimpleStoreVolumeView storeVolume) {
        this.storeVolume = storeVolume;
    }

    public ConfidenceType getVolumeConfidence() {
        return volumeConfidence;
    }

    public void setVolumeConfidence(ConfidenceType volumeConfidence) {
        this.volumeConfidence = volumeConfidence;
    }

}
