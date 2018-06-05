package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.ConfidenceType;
import com.mtn.model.domain.StoreCasing;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleStoreCasingView {

    private Integer id;
    private LocalDateTime casingDate;
    private String note;

    private SimpleStoreVolumeView storeVolume;
    private SimpleStoreStatusView storeStatus;
    private SimpleStoreSurveyView storeSurvey;
    private List<SimpleProjectView> projects = new ArrayList<>();

    public SimpleStoreCasingView(StoreCasing casing) {
        this.id = casing.getId();
        this.casingDate = casing.getCasingDate();
        this.note = casing.getNote();
        if (casing.getStoreStatus() != null) {
            this.storeStatus = new SimpleStoreStatusView(casing.getStoreStatus());
        }
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
}
