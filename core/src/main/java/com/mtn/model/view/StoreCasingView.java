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
    private Integer legacyCasingId;
    private String storeStatus;

    private StoreVolumeView storeVolume;
    private StoreSurveyView storeSurvey;
    private ShoppingCenterCasingView shoppingCenterCasing;
    private List<SimpleProjectView> projects = new ArrayList<>();

    public StoreCasingView() {
    }

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

    public void setCasingDate(LocalDateTime casingDate) {
        this.casingDate = casingDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public Integer getLegacyCasingId() {
        return legacyCasingId;
    }

    public void setLegacyCasingId(Integer legacyCasingId) {
        this.legacyCasingId = legacyCasingId;
    }

    public String getStoreStatus() {
        return storeStatus;
    }

    public void setStoreStatus(String storeStatus) {
        this.storeStatus = storeStatus;
    }

    public StoreVolumeView getStoreVolume() {
        return storeVolume;
    }

    public void setStoreVolume(StoreVolumeView storeVolume) {
        this.storeVolume = storeVolume;
    }

    public StoreSurveyView getStoreSurvey() {
        return storeSurvey;
    }

    public void setStoreSurvey(StoreSurveyView storeSurvey) {
        this.storeSurvey = storeSurvey;
    }

    public ShoppingCenterCasingView getShoppingCenterCasing() {
        return shoppingCenterCasing;
    }

    public void setShoppingCenterCasing(ShoppingCenterCasingView shoppingCenterCasing) {
        this.shoppingCenterCasing = shoppingCenterCasing;
    }

    public List<SimpleProjectView> getProjects() {
        return projects;
    }

    public void setProjects(List<SimpleProjectView> projects) {
        this.projects = projects;
    }
}
