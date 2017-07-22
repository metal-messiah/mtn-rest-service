package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.StoreModel;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreModelView extends SimpleStoreModelView {

    private SimpleUserProfileView createdBy;
    private LocalDateTime createdDate;
    private SimpleUserProfileView updatedBy;
    private LocalDateTime updatedDate;

    private SimpleProjectView project;

    public StoreModelView() {
        super();
    }

    public StoreModelView(StoreModel storeModel) {
        super(storeModel);

        this.createdBy = new SimpleUserProfileView(storeModel.getCreatedBy());
        this.createdDate = storeModel.getCreatedDate();
        this.updatedBy = new SimpleUserProfileView(storeModel.getUpdatedBy());
        this.updatedDate = storeModel.getUpdatedDate();

        if (storeModel.getProject() != null) {
            this.project = new SimpleProjectView(storeModel.getProject());
        }
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

    public SimpleProjectView getProject() {
        return project;
    }

    public void setProject(SimpleProjectView project) {
        this.project = project;
    }
}
