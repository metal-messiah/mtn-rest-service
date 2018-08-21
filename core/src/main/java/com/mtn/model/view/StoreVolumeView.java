package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.ConfidenceType;
import com.mtn.constant.VolumeType;
import com.mtn.model.domain.StoreVolume;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreVolumeView extends AuditingEntityView {

    private Integer volumeTotal;
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

    public StoreVolumeView(StoreVolume volume) {
        super(volume);

        this.volumeTotal = volume.getVolumeTotal();
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

    public LocalDate getVolumeDate() {
        return volumeDate;
    }

    public VolumeType getVolumeType() {
        return volumeType;
    }

    public String getSource() {
        return source;
    }

    public Integer getVolumeGrocery() {
        return volumeGrocery;
    }

    public Double getVolumePercentGrocery() {
        return volumePercentGrocery;
    }

    public Integer getVolumeMeat() {
        return volumeMeat;
    }

    public Double getVolumePercentMeat() {
        return volumePercentMeat;
    }

    public Integer getVolumeNonFood() {
        return volumeNonFood;
    }

    public Double getVolumePercentNonFood() {
        return volumePercentNonFood;
    }

    public Integer getVolumeOther() {
        return volumeOther;
    }

    public Double getVolumePercentOther() {
        return volumePercentOther;
    }

    public Integer getVolumeProduce() {
        return volumeProduce;
    }

    public Double getVolumePercentProduce() {
        return volumePercentProduce;
    }

    public Integer getVolumePlusMinus() {
        return volumePlusMinus;
    }

    public String getVolumeNote() {
        return volumeNote;
    }

    public ConfidenceType getVolumeConfidence() {
        return volumeConfidence;
    }

    public Integer getLegacyCasingId() {
        return legacyCasingId;
    }
}
