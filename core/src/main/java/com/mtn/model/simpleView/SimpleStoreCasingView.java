package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.StoreCasing;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleStoreCasingView extends SimpleAuditingEntityView {

    private LocalDateTime casingDate;
    private String note;
    private String storeStatus;

    private SimpleStoreVolumeView storeVolume;
    private SimpleStoreSurveyView storeSurvey;
    private SimpleShoppingCenterCasingView shoppingCenterCasing;
    private List<SimpleProjectView> projects = new ArrayList<>();

    public SimpleStoreCasingView() {
    }

    public SimpleStoreCasingView(StoreCasing casing) {
        super(casing);
        this.casingDate = casing.getCasingDate();
        this.note = casing.getNote();
        this.storeStatus = casing.getStoreStatus();
        if (casing.getStoreVolume() != null) {
            this.storeVolume = new SimpleStoreVolumeView(casing.getStoreVolume());
        }
        if (casing.getStoreSurvey() != null) {
            this.storeSurvey = new SimpleStoreSurveyView(casing.getStoreSurvey());
        }
        if (casing.getProjects() != null) {
            this.projects = casing.getProjects().stream()
                    .filter(project -> project.getDeletedDate() == null)
                    .map(SimpleProjectView::new)
                    .collect(Collectors.toList());
        }
        if (casing.getShoppingCenterCasing() != null) {
            this.shoppingCenterCasing = new SimpleShoppingCenterCasingView(casing.getShoppingCenterCasing());
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

    public String getStoreStatus() {
        return storeStatus;
    }

    public void setStoreStatus(String storeStatus) {
        this.storeStatus = storeStatus;
    }

    public SimpleStoreVolumeView getStoreVolume() {
        return storeVolume;
    }

    public void setStoreVolume(SimpleStoreVolumeView storeVolume) {
        this.storeVolume = storeVolume;
    }

   public List<SimpleProjectView> getProjects() {
        return projects;
    }

    public void setProjects(List<SimpleProjectView> projects) {
        this.projects = projects;
    }

    public SimpleStoreSurveyView getStoreSurvey() {
        return storeSurvey;
    }

    public void setStoreSurvey(SimpleStoreSurveyView storeSurvey) {
        this.storeSurvey = storeSurvey;
    }

    public SimpleShoppingCenterCasingView getShoppingCenterCasing() {
        return shoppingCenterCasing;
    }

    public void setShoppingCenterCasing(SimpleShoppingCenterCasingView shoppingCenterCasing) {
        this.shoppingCenterCasing = shoppingCenterCasing;
    }
}
