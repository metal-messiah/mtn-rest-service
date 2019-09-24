package com.mtn.model.domain;

import com.mtn.constant.ConfidenceType;
import com.mtn.constant.VolumeType;
import com.mtn.model.StoreChild;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
@AttributeOverride(name="id", column=@Column(name="store_volume_id"))
public class StoreVolume extends AuditingEntity implements StoreChild {

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

    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
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

    @Enumerated(EnumType.STRING)
    public ConfidenceType getVolumeConfidence() {
        return volumeConfidence;
    }

    public void setVolumeConfidence(ConfidenceType volumeConfidence) {
        this.volumeConfidence = volumeConfidence;
    }

    public Integer getVolumeBoxTotal() {
        return volumeBoxTotal;
    }

    public void setVolumeBoxTotal(Integer volumeBoxTotal) {
        this.volumeBoxTotal = volumeBoxTotal;
    }
}
