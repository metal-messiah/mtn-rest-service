package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Project;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleProjectView extends SimpleAuditingEntityView {

    private String projectName;
    private Boolean active;
    private Boolean primaryData;
    private Boolean hasBoundary;

    public SimpleProjectView() {
    }

    public SimpleProjectView(Project project) {
        super(project);
        this.projectName = project.getProjectName();
        this.active = project.getActive();
        this.primaryData = project.getPrimaryData();
        this.hasBoundary = project.getBoundary() != null;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getPrimaryData() {
        return primaryData;
    }

    public void setPrimaryData(Boolean primaryData) {
        this.primaryData = primaryData;
    }

    public Boolean getHasBoundary() {
        return hasBoundary;
    }

    public void setHasBoundary(Boolean hasBoundary) {
        this.hasBoundary = hasBoundary;
    }
}
