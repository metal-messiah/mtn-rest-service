package com.mtn.model.domain;

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
    private StoreVolume storeVolume;
    private Integer legacyCasingId;

    private StoreSurvey storeSurvey;
    private List<Project> projects = new ArrayList<>();

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

    public Integer getLegacyCasingId() {
        return legacyCasingId;
    }

    public void setLegacyCasingId(Integer legacyCasingId) {
        this.legacyCasingId = legacyCasingId;
    }

    @ManyToOne
    @JoinColumn(name = "store_survey_id")
    public StoreSurvey getStoreSurvey() {
        return storeSurvey;
    }

    public void setStoreSurvey(StoreSurvey storeSurvey) {
        this.storeSurvey = storeSurvey;
    }

    @ManyToMany
    @JoinTable(
            name = "store_casing_project",
            joinColumns = @JoinColumn(name = "store_casing_id", referencedColumnName = "store_casing_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    )
    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @OneToOne
    @JoinColumn(name = "store_volume_id")
    public StoreVolume getStoreVolume() {
        return storeVolume;
    }

    public void setStoreVolume(StoreVolume storeVolume) {
        this.storeVolume = storeVolume;
    }


}
