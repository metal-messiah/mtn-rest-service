package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.ConfidenceType;
import com.mtn.constant.RatingType;
import com.mtn.model.domain.StoreCasing;
import com.mtn.model.domain.StoreVolume;
import com.mtn.model.simpleView.SimpleProjectView;
import com.mtn.model.simpleView.SimpleStoreStatusView;
import com.mtn.model.simpleView.SimpleStoreVolumeView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreCasingView extends AuditingEntityView {

    private Integer id;
    private LocalDateTime casingDate;
    private String note;
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
    private ConfidenceType volumeConfidence;
    private Integer legacyCasingId;

    private SimpleStoreStatusView storeStatus;
    private StoreVolumeView storeVolume;
    private StoreSurveyView surveyView;

    private List<SimpleProjectView> projects = new ArrayList<>();

    public StoreCasingView(StoreCasing storeCasing) {
        super(storeCasing);
        this.id = storeCasing.getId();
        this.casingDate = storeCasing.getCasingDate();
        this.note = storeCasing.getNote();
        this.conditionCeiling = storeCasing.getConditionCeiling();
        this.conditionCheckstands = storeCasing.getConditionCheckstands();
        this.conditionFloors = storeCasing.getConditionFloors();
        this.conditionFrozenRefrigerated = storeCasing.getConditionFrozenRefrigerated();
        this.conditionShelvingGondolas = storeCasing.getConditionShelvingGondolas();
        this.conditionWalls = storeCasing.getConditionWalls();
        this.fuelGallonsWeekly = storeCasing.getFuelGallonsWeekly();
        this.pharmacyScriptsWeekly = storeCasing.getPharmacyScriptsWeekly();
        this.pharmacyAvgDollarsPerScript = storeCasing.getPharmacyAvgDollarsPerScript();
        this.pharmacyPharmacistCount = storeCasing.getPharmacyPharmacistCount();
        this.pharmacyTechnicianCount = storeCasing.getPharmacyTechnicianCount();
        this.volumeGrocery = storeCasing.getVolumeGrocery();
        this.volumePercentGrocery = storeCasing.getVolumePercentGrocery();
        this.volumeMeat = storeCasing.getVolumeMeat();
        this.volumePercentMeat = storeCasing.getVolumePercentMeat();
        this.volumeNonFood = storeCasing.getVolumeNonFood();
        this.volumePercentNonFood = storeCasing.getVolumePercentNonFood();
        this.volumeOther = storeCasing.getVolumeOther();
        this.volumePercentOther = storeCasing.getVolumePercentOther();
        this.volumeProduce = storeCasing.getVolumeProduce();
        this.volumePercentProduce = storeCasing.getVolumePercentProduce();
        this.volumePlusMinus = storeCasing.getVolumePlusMinus();
        this.volumeNote = storeCasing.getVolumeNote();
        this.volumeConfidence = storeCasing.getVolumeConfidence();
        this.legacyCasingId = storeCasing.getLegacyCasingId();


        if (storeCasing.getStoreStatus() != null) {
            this.storeStatus = new SimpleStoreStatusView(storeCasing.getStoreStatus());
        }
        if (storeCasing.getStoreVolume() != null) {
            this.storeVolume = new StoreVolumeView(storeCasing.getStoreVolume());
        }
        if (storeCasing.getStoreSurvey() != null) {
            this.surveyView = new StoreSurveyView(storeCasing.getStoreSurvey());
        }
        if (storeCasing.getProjects() != null)  {
            this.projects = storeCasing.getProjects().stream()
                    .filter(project -> project.getDeletedDate() == null)
                    .map(SimpleProjectView::new)
                    .collect(Collectors.toList());
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

    public StoreVolumeView getStoreVolume() {
        return storeVolume;
    }

    public void setStoreVolume(StoreVolume storeVolumeView) {
        this.storeVolume = storeVolume;
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

    public List<SimpleProjectView> getProjects() {
        return projects;
    }

    public void setProjects(List<SimpleProjectView> projects) {
        this.projects = projects;
    }

    public StoreSurveyView getSurveyView() {
        return surveyView;
    }

    public void setSurveyView(StoreSurveyView surveyView) {
        this.surveyView = surveyView;
    }

}
