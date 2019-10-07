package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.ConfidenceType;
import com.mtn.constant.VolumeType;
import com.mtn.model.domain.StoreVolume;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreVolumeView extends AuditingEntityView {

    private Integer volumeTotal;
    private Integer volumeBoxTotal;
    private LocalDate volumeDate;
    private VolumeType volumeType;
    private String source;
    private Integer volumeGrocery;
    private Double volumePercentGrocery;
    private Integer volumeMeat;
    private Double volumePercentMeat;
    private Integer volumeNonFood;
    private Double volumePercentNonFood;
    private Integer volumeOther;
    private Double volumePercentOther;
    private Integer volumeProduce;
    private Double volumePercentProduce;
    private Integer volumePlusMinus;
    private String volumeNote;
    private ConfidenceType volumeConfidence;
    private Integer legacyCasingId;

    public StoreVolumeView() {
    }

    public StoreVolumeView(StoreVolume volume) {
        super(volume);

        this.volumeTotal = volume.getVolumeTotal();
        this.volumeBoxTotal = volume.getVolumeBoxTotal();
        this.volumeDate = volume.getVolumeDate();
        this.volumeType = volume.getVolumeType();
        this.source = volume.getSource();
        this.volumeGrocery = volume.getVolumeGrocery();
        this.volumePercentGrocery = volume.getVolumePercentGrocery();
        this.volumeMeat = volume.getVolumeMeat();
        this.volumePercentMeat = volume.getVolumePercentMeat();
        this.volumeNonFood = volume.getVolumeNonFood();
        this.volumePercentNonFood = volume.getVolumePercentNonFood();
        this.volumeOther = volume.getVolumeOther();
        this.volumePercentOther = volume.getVolumePercentOther();
        this.volumeProduce = volume.getVolumeProduce();
        this.volumePercentProduce = volume.getVolumePercentProduce();
        this.volumePlusMinus = volume.getVolumePlusMinus();
        this.volumeNote = volume.getVolumeNote();
        this.volumeConfidence = volume.getVolumeConfidence();
        this.legacyCasingId = volume.getLegacyCasingId();
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getVolumeGrocery() {
        return volumeGrocery;
    }

    public void setVolumeGrocery(Integer volumeGrocery) {
        this.volumeGrocery = volumeGrocery;
    }

    public Double getVolumePercentGrocery() {
        return volumePercentGrocery;
    }

    public void setVolumePercentGrocery(Double volumePercentGrocery) {
        this.volumePercentGrocery = volumePercentGrocery;
    }

    public Integer getVolumeMeat() {
        return volumeMeat;
    }

    public void setVolumeMeat(Integer volumeMeat) {
        this.volumeMeat = volumeMeat;
    }

    public Double getVolumePercentMeat() {
        return volumePercentMeat;
    }

    public void setVolumePercentMeat(Double volumePercentMeat) {
        this.volumePercentMeat = volumePercentMeat;
    }

    public Integer getVolumeNonFood() {
        return volumeNonFood;
    }

    public void setVolumeNonFood(Integer volumeNonFood) {
        this.volumeNonFood = volumeNonFood;
    }

    public Double getVolumePercentNonFood() {
        return volumePercentNonFood;
    }

    public void setVolumePercentNonFood(Double volumePercentNonFood) {
        this.volumePercentNonFood = volumePercentNonFood;
    }

    public Integer getVolumeOther() {
        return volumeOther;
    }

    public void setVolumeOther(Integer volumeOther) {
        this.volumeOther = volumeOther;
    }

    public Double getVolumePercentOther() {
        return volumePercentOther;
    }

    public void setVolumePercentOther(Double volumePercentOther) {
        this.volumePercentOther = volumePercentOther;
    }

    public Integer getVolumeProduce() {
        return volumeProduce;
    }

    public void setVolumeProduce(Integer volumeProduce) {
        this.volumeProduce = volumeProduce;
    }

    public Double getVolumePercentProduce() {
        return volumePercentProduce;
    }

    public void setVolumePercentProduce(Double volumePercentProduce) {
        this.volumePercentProduce = volumePercentProduce;
    }

    public Integer getVolumePlusMinus() {
        return volumePlusMinus;
    }

    public void setVolumePlusMinus(Integer volumePlusMinus) {
        this.volumePlusMinus = volumePlusMinus;
    }

    public String getVolumeNote() {
        return volumeNote;
    }

    public void setVolumeNote(String volumeNote) {
        this.volumeNote = volumeNote;
    }

    public ConfidenceType getVolumeConfidence() {
        return volumeConfidence;
    }

    public void setVolumeConfidence(ConfidenceType volumeConfidence) {
        this.volumeConfidence = volumeConfidence;
    }

    public Integer getLegacyCasingId() {
        return legacyCasingId;
    }

    public void setLegacyCasingId(Integer legacyCasingId) {
        this.legacyCasingId = legacyCasingId;
    }

    public Integer getVolumeBoxTotal() {
        return volumeBoxTotal;
    }

    public void setVolumeBoxTotal(Integer volumeBoxTotal) {
        this.volumeBoxTotal = volumeBoxTotal;
    }
}