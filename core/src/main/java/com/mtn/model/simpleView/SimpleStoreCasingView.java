package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.ConfidenceType;
import com.mtn.constant.RatingType;
import com.mtn.model.domain.StoreCasing;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleStoreCasingView {

    private Integer id;
    private String note;
    private String status;
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
    private String volumeNote;
    private Integer volumeTotal;
    private ConfidenceType volumeConfidence;
    private Integer version;
    private Integer legacyCasingId;
    private SimpleUserProfileView createdBy;
    private LocalDateTime createdDate;
    private SimpleUserProfileView updatedBy;
    private LocalDateTime updatedDate;

    public SimpleStoreCasingView() {
    }

    public SimpleStoreCasingView(StoreCasing casing) {
        this.id = casing.getId();
        this.note = casing.getNote();
        this.status = casing.getStatus();
        this.conditionCeiling = casing.getConditionCeiling();
        this.conditionCheckstands = casing.getConditionCheckstands();
        this.conditionFloors = casing.getConditionFloors();
        this.conditionFrozenRefrigerated = casing.getConditionFrozenRefrigerated();
        this.conditionShelvingGondolas = casing.getConditionShelvingGondolas();
        this.conditionWalls = casing.getConditionWalls();
        this.fuelGallonsWeekly = casing.getFuelGallonsWeekly();
        this.pharmacyScriptsWeekly = casing.getPharmacyScriptsWeekly();
        this.pharmacyAvgDollarsPerScript = casing.getPharmacyAvgDollarsPerScript();
        this.pharmacyPharmacistCount = casing.getPharmacyPharmacistCount();
        this.pharmacyTechnicianCount = casing.getPharmacyTechnicianCount();
        this.volumeGrocery = casing.getVolumeGrocery();
        this.volumePercentGrocery = casing.getVolumePercentGrocery();
        this.volumeMeat = casing.getVolumeMeat();
        this.volumePercentMeat = casing.getVolumePercentMeat();
        this.volumeNonFood = casing.getVolumeNonFood();
        this.volumePercentNonFood = casing.getVolumePercentNonFood();
        this.volumeOther = casing.getVolumeOther();
        this.volumePercentOther = casing.getVolumePercentOther();
        this.volumeProduce = casing.getVolumeProduce();
        this.volumePercentProduce = casing.getVolumePercentProduce();
        this.volumePlusMinus = casing.getVolumePlusMinus();
        this.volumeNote = casing.getVolumeNote();
        this.volumeTotal = casing.getVolumeTotal();
        this.volumeConfidence = casing.getVolumeConfidence();
        this.version = casing.getVersion();
        this.legacyCasingId = casing.getLegacyCasingId();
        this.createdBy = new SimpleUserProfileView(casing.getCreatedBy());
        this.createdDate = casing.getCreatedDate();
        this.updatedBy = new SimpleUserProfileView(casing.getUpdatedBy());
        this.updatedDate = casing.getUpdatedDate();
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

    public RatingType getConditionCeiling() {
        return conditionCeiling;
    }

    public void setConditionCeiling(RatingType conditionCeiling) {
        this.conditionCeiling = conditionCeiling;
    }

    public RatingType getConditionCheckstands() {
        return conditionCheckstands;
    }

    public void setConditionCheckstands(RatingType conditionCheckstands) {
        this.conditionCheckstands = conditionCheckstands;
    }

    public RatingType getConditionFloors() {
        return conditionFloors;
    }

    public void setConditionFloors(RatingType conditionFloors) {
        this.conditionFloors = conditionFloors;
    }

    public RatingType getConditionFrozenRefrigerated() {
        return conditionFrozenRefrigerated;
    }

    public void setConditionFrozenRefrigerated(RatingType conditionFrozenRefrigerated) {
        this.conditionFrozenRefrigerated = conditionFrozenRefrigerated;
    }

    public RatingType getConditionShelvingGondolas() {
        return conditionShelvingGondolas;
    }

    public void setConditionShelvingGondolas(RatingType conditionShelvingGondolas) {
        this.conditionShelvingGondolas = conditionShelvingGondolas;
    }

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
