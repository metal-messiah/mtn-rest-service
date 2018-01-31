package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.ConfidenceType;
import com.mtn.model.domain.StoreCasing;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleStoreCasingView {

    private Integer id;
    private String note;
    private String status;
    private String volumeNote;
    private Integer volumeTotal;
    private ConfidenceType volumeConfidence;

    public SimpleStoreCasingView(StoreCasing casing) {
        this.id = casing.getId();
        this.note = casing.getNote();
        this.status = casing.getStatus();
        this.volumeNote = casing.getVolumeNote();
        this.volumeTotal = casing.getVolumeTotal();
        this.volumeConfidence = casing.getVolumeConfidence();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getVolumeTotal() {
        return volumeTotal;
    }

    public void setVolumeTotal(Integer volumeTotal) {
        this.volumeTotal = volumeTotal;
    }

    public ConfidenceType getVolumeConfidence() {
        return volumeConfidence;
    }

    public void setVolumeConfidence(ConfidenceType volumeConfidence) {
        this.volumeConfidence = volumeConfidence;
    }

}
