package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Project;
import com.mtn.model.view.geojson.GeoJsonView;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleProjectView {

    protected Integer id;
    protected String projectName;
    protected String metroArea;
    protected String clientName;
    protected Integer projectYear;
    protected Integer projectMonth;
    protected Boolean isActive = false;
    protected Boolean isPrimaryData = false;
    protected LocalDateTime startedDate;
    protected LocalDateTime completedDate;
    protected String source;
    protected GeoJsonView boundary;
    protected Integer version;
    protected Integer legacyProjectId;
    protected SimpleUserProfileView createdBy;
    protected LocalDateTime createdDate;
    protected SimpleUserProfileView updatedBy;
    protected LocalDateTime updatedDate;

    public SimpleProjectView() {
    }

    public SimpleProjectView(Project project) {
        this.id = project.getId();
        this.projectName = project.getProjectName();
        this.metroArea = project.getMetroArea();
        this.clientName = project.getClientName();
        this.projectYear = project.getProjectYear();
        this.projectMonth = project.getProjectMonth();
        this.isActive = project.getIsActive();
        this.isPrimaryData = project.getIsPrimaryData();
        this.startedDate = project.getStartedDate();
        this.completedDate = project.getCompletedDate();
        this.source = project.getSource();
        this.boundary = new GeoJsonView(project.getBoundary());
        this.version = project.getVersion();
        this.legacyProjectId = project.getLegacyProjectId();
        this.createdBy = new SimpleUserProfileView(project.getCreatedBy());
        this.createdDate = project.getCreatedDate();
        this.updatedBy = new SimpleUserProfileView(project.getUpdatedBy());
        this.updatedDate = project.getUpdatedDate();
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

    public String getMetroArea() {
        return metroArea;
    }

    public void setMetroArea(String metroArea) {
        this.metroArea = metroArea;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Integer getProjectYear() {
        return projectYear;
    }

    public void setProjectYear(Integer projectYear) {
        this.projectYear = projectYear;
    }

    public Integer getProjectMonth() {
        return projectMonth;
    }

    public void setProjectMonth(Integer projectMonth) {
        this.projectMonth = projectMonth;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getPrimaryData() {
        return isPrimaryData;
    }

    public void setPrimaryData(Boolean primaryData) {
        isPrimaryData = primaryData;
    }

    public LocalDateTime getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(LocalDateTime startedDate) {
        this.startedDate = startedDate;
    }

    public LocalDateTime getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(LocalDateTime completedDate) {
        this.completedDate = completedDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public GeoJsonView getBoundary() {
        return boundary;
    }

    public void setBoundary(GeoJsonView boundary) {
        this.boundary = boundary;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getLegacyProjectId() {
        return legacyProjectId;
    }

    public void setLegacyProjectId(Integer legacyProjectId) {
        this.legacyProjectId = legacyProjectId;
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
}
