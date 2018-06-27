package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Project;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleProjectView {

    private Integer id;
    private String projectName;
    private boolean active;
    private boolean primaryData;

    public SimpleProjectView(Project project) {
        this.id = project.getId();
        this.projectName = project.getProjectName();
        this.active = project.getActive();
        this.primaryData = project.getPrimaryData();
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isPrimaryData() {
        return primaryData;
    }

    public void setPrimaryData(boolean primaryData) {
        this.primaryData = primaryData;
    }
}
