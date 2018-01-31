package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Project;
import com.mtn.model.simpleView.SimpleInteractionView;
import com.mtn.model.simpleView.SimpleStoreModelView;
import com.mtn.model.view.geojson.GeoJsonView;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectView extends AuditingEntityView {

    private Integer id;
    private String projectName;
    private String metroArea;
    private String clientName;
    private Integer projectYear;
    private Integer projectMonth;
    private Boolean isActive;
    private Boolean isPrimaryData;
    private LocalDateTime startedDate;
    private LocalDateTime completedDate;
    private String source;
    private GeoJsonView boundary;
    private Integer legacyProjectId;

    private List<SimpleInteractionView> interactions;
    private List<SimpleStoreModelView> models;

    public ProjectView(Project project) {
        super(project);
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
        this.legacyProjectId = project.getLegacyProjectId();
        this.interactions = project.getInteractions().stream().filter(interaction -> interaction.getDeletedDate() == null).map(SimpleInteractionView::new).collect(Collectors.toList());
        this.models = project.getModels().stream().filter(model -> model.getDeletedDate() == null).map(SimpleStoreModelView::new).collect(Collectors.toList());
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

    public Integer getLegacyProjectId() {
        return legacyProjectId;
    }

    public void setLegacyProjectId(Integer legacyProjectId) {
        this.legacyProjectId = legacyProjectId;
    }

    public List<SimpleInteractionView> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<SimpleInteractionView> interactions) {
        this.interactions = interactions;
    }

    public List<SimpleStoreModelView> getModels() {
        return models;
    }

    public void setModels(List<SimpleStoreModelView> models) {
        this.models = models;
    }
}
