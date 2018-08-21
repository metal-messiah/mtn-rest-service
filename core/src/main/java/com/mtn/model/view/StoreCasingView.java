package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.RatingType;
import com.mtn.model.domain.StoreCasing;
import com.mtn.model.simpleView.SimpleProjectView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreCasingView extends AuditingEntityView {

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
    private Integer legacyCasingId;
    private String storeStatus;

    private StoreVolumeView storeVolume;
    private StoreSurveyView storeSurvey;
    private ShoppingCenterCasingView shoppingCenterCasing;
    private List<SimpleProjectView> projects = new ArrayList<>();

    public StoreCasingView(StoreCasing storeCasing) {
        super(storeCasing);

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
        this.legacyCasingId = storeCasing.getLegacyCasingId();
        this.storeStatus = storeCasing.getStoreStatus();

        if (storeCasing.getStoreVolume() != null) {
            this.storeVolume = new StoreVolumeView(storeCasing.getStoreVolume());
        }
        if (storeCasing.getStoreSurvey() != null) {
            this.storeSurvey = new StoreSurveyView(storeCasing.getStoreSurvey());
        }
        if (storeCasing.getProjects() != null)  {
            this.projects = storeCasing.getProjects().stream()
                    .filter(project -> project.getDeletedDate() == null)
                    .map(SimpleProjectView::new)
                    .collect(Collectors.toList());
        }
        if (storeCasing.getShoppingCenterCasing() != null) {
            this.shoppingCenterCasing = new ShoppingCenterCasingView(storeCasing.getShoppingCenterCasing());
        }
    }

    public LocalDateTime getCasingDate() {
        return casingDate;
    }

    public String getNote() {
        return note;
    }

    public RatingType getConditionCeiling() {
        return conditionCeiling;
    }

    public RatingType getConditionCheckstands() {
        return conditionCheckstands;
    }

    public RatingType getConditionFloors() {
        return conditionFloors;
    }

    public RatingType getConditionFrozenRefrigerated() {
        return conditionFrozenRefrigerated;
    }

    public RatingType getConditionShelvingGondolas() {
        return conditionShelvingGondolas;
    }

    public RatingType getConditionWalls() {
        return conditionWalls;
    }

    public Integer getFuelGallonsWeekly() {
        return fuelGallonsWeekly;
    }

    public Integer getPharmacyScriptsWeekly() {
        return pharmacyScriptsWeekly;
    }

    public Double getPharmacyAvgDollarsPerScript() {
        return pharmacyAvgDollarsPerScript;
    }

    public Integer getPharmacyPharmacistCount() {
        return pharmacyPharmacistCount;
    }

    public Integer getPharmacyTechnicianCount() {
        return pharmacyTechnicianCount;
    }

    public Integer getLegacyCasingId() {
        return legacyCasingId;
    }

    public String getStoreStatus() {
        return storeStatus;
    }

    public StoreVolumeView getStoreVolume() {
        return storeVolume;
    }

    public StoreSurveyView getStoreSurvey() {
        return storeSurvey;
    }

    public ShoppingCenterCasingView getShoppingCenterCasing() {
        return shoppingCenterCasing;
    }

    public List<SimpleProjectView> getProjects() {
        return projects;
    }
}
