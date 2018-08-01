package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Project;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleProjectView {

    private Integer id;
    private String projectName;
    private Boolean active;
    private Boolean primaryData;
    private Boolean hasBoundary;

    public SimpleProjectView(Project project) {
        this.id = project.getId();
        this.projectName = project.getProjectName();
        this.active = project.getActive();
        this.primaryData = project.getPrimaryData();
        this.hasBoundary = project.getBoundary() != null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean isPrimaryData() {
        return primaryData;
    }

    public void setPrimaryData(Boolean primaryData) {
        this.primaryData = primaryData;
    }

    public Boolean getActive() {
        return active;
    }

    public Boolean getPrimaryData() {
        return primaryData;
    }

    public Boolean getHasBoundary() {
        return hasBoundary;
    }

    public void setHasBoundary(Boolean hasBoundary) {
        this.hasBoundary = hasBoundary;
    }
}
