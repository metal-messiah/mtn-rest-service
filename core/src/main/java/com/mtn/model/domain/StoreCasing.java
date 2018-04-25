package com.mtn.model.domain;

import com.mtn.constant.ConfidenceType;
import com.mtn.constant.RatingType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class StoreCasing extends AuditingEntity implements Identifiable {

    private Integer id;
    private Store store;
    private LocalDateTime casingDate;
    private String note;
    private StoreStatus storeStatus;
    private RatingType conditionCeiling;
    private RatingType conditionCheckstands;
    private RatingType conditionFloors;
    private RatingType conditionFrozenRefrigerated;
    private RatingType conditionShelvingGondolas;
    private RatingType conditionWalls;
    private Integer fuelGallonsWeekly;
    private Integer pharmacyScriptsWeekly;
    private Double pharmacyAvgDollarsPerScript;
    private Integer pharmacyPharmacistCount;
    private Integer pharmacyTechnicianCount;
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
    private StoreVolume storeVolume;
    private String volumeNote;
    private ConfidenceType volumeConfidence;
    private Integer legacyCasingId;

    private List<Interaction> interactions = new ArrayList<>();

    @Id
    @GeneratedValue
    @Column(name = "store_casing_id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "store_id")
    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Column(name = "store_casing_date")
    public LocalDateTime getCasingDate() {
        return casingDate;
    }

    public void setCasingDate(LocalDateTime casingDate) {
        this.casingDate = casingDate;
    }

    @Column(name = "store_casing_note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @OneToOne
    @JoinColumn(name = "store_status_id")
    public StoreStatus getStoreStatus() {
        return storeStatus;
    }

    public void setStoreStatus(StoreStatus storeStatus) {
        this.storeStatus = storeStatus;
    }

    @Enumerated(EnumType.STRING)
    public RatingType getConditionCeiling() {
        return conditionCeiling;
    }

    public void setConditionCeiling(RatingType conditionCeiling) {
        this.conditionCeiling = conditionCeiling;
    }

    @Enumerated(EnumType.STRING)
    public RatingType getConditionCheckstands() {
        return conditionCheckstands;
    }

    public void setConditionCheckstands(RatingType conditionCheckstands) {
        this.conditionCheckstands = conditionCheckstands;
    }

    @Enumerated(EnumType.STRING)
    public RatingType getConditionFloors() {
        return conditionFloors;
    }

    public void setConditionFloors(RatingType conditionFloors) {
        this.conditionFloors = conditionFloors;
    }

    @Enumerated(EnumType.STRING)
    public RatingType getConditionFrozenRefrigerated() {
        return conditionFrozenRefrigerated;
    }

    public void setConditionFrozenRefrigerated(RatingType conditionFrozenRefrigerated) {
        this.conditionFrozenRefrigerated = conditionFrozenRefrigerated;
    }

    @Enumerated(EnumType.STRING)
    public RatingType getConditionShelvingGondolas() {
        return conditionShelvingGondolas;
    }

    public void setConditionShelvingGondolas(RatingType conditionShelvingGondolas) {
        this.conditionShelvingGondolas = conditionShelvingGondolas;
    }

    @Enumerated(EnumType.STRING)
    public RatingType getConditionWalls() {
        return conditionWalls;
    }

    public void setConditionWalls(RatingType conditionWalls) {
        this.conditionWalls = conditionWalls;
    }

    public Integer getFuelGallonsWeekly() {
        return fuelGallonsWeekly;
    }

    public void setFuelGallonsWeekly(Integer fuelGallonsWeekly) {
        this.fuelGallonsWeekly = fuelGallonsWeekly;
    }

    public Integer getPharmacyScriptsWeekly() {
        return pharmacyScriptsWeekly;
    }

    public void setPharmacyScriptsWeekly(Integer pharmacyScriptsWeekly) {
        this.pharmacyScriptsWeekly = pharmacyScriptsWeekly;
    }

    public Double getPharmacyAvgDollarsPerScript() {
        return pharmacyAvgDollarsPerScript;
    }

    public void setPharmacyAvgDollarsPerScript(Double pharmacyAvgDollarsPerScript) {
        this.pharmacyAvgDollarsPerScript = pharmacyAvgDollarsPerScript;
    }

    public Integer getPharmacyPharmacistCount() {
        return pharmacyPharmacistCount;
    }

    public void setPharmacyPharmacistCount(Integer pharmacyPharmacistCount) {
        this.pharmacyPharmacistCount = pharmacyPharmacistCount;
    }

    public Integer getPharmacyTechnicianCount() {
        return pharmacyTechnicianCount;
    }

    public void setPharmacyTechnicianCount(Integer pharmacyTechnicianCount) {
        this.pharmacyTechnicianCount = pharmacyTechnicianCount;
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

    @OneToOne
    @JoinColumn(name = "store_volume_id")
    public StoreVolume getStoreVolume() {
        return storeVolume;
    }

    public void setStoreVolume(StoreVolume storeVolume) {
        this.storeVolume = storeVolume;
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

    public Integer getLegacyCasingId() {
        return legacyCasingId;
    }

    public void setLegacyCasingId(Integer legacyCasingId) {
        this.legacyCasingId = legacyCasingId;
    }

    @OneToMany(mappedBy = "storeCasing")
    public List<Interaction> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<Interaction> interactions) {
        this.interactions = interactions;
    }
}
