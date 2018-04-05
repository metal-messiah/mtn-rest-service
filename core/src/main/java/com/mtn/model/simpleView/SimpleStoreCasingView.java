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
    private String status;
    private String volumeNote;
    private SimpleStoreVolumeView storeVolume;
    private ConfidenceType volumeConfidence;

    public SimpleStoreCasingView(StoreCasing casing) {
        this.id = casing.getId();
        this.casingDate = casing.getCasingDate();
        this.note = casing.getNote();
        this.status = casing.getStatus();
        this.volumeNote = casing.getVolumeNote();
        this.storeVolume = new SimpleStoreVolumeView(casing.getStoreVolume());
        this.volumeConfidence = casing.getVolumeConfidence();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
