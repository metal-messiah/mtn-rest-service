package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Project;
import com.mtn.model.simpleView.SimpleStoreModelView;

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
    private Boolean active;
    private Boolean primaryData;
    private LocalDateTime dateStarted;
    private LocalDateTime dateCompleted;
    private String source;
    private Integer legacyProjectId;

    private List<SimpleStoreModelView> models;

    public ProjectView(Project project) {
        super(project);
        this.id = project.getId();
        this.projectName = project.getProjectName();
        this.metroArea = project.getMetroArea();
        this.clientName = project.getClientName();
        this.projectYear = project.getProjectYear();
        this.projectMonth = project.getProjectMonth();
        this.active = project.getActive();
        this.primaryData = project.getPrimaryData();
        this.dateStarted = project.getDateStarted();
        this.dateCompleted = project.getDateCompleted();
        this.source = project.getSource();
//        this.boundary = project.getBoundary();
        this.legacyProjectId = project.getLegacyProjectId();
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

    public LocalDateTime getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(LocalDateTime dateStarted) {
        this.dateStarted = dateStarted;
    }

    public LocalDateTime getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(LocalDateTime dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getLegacyProjectId() {
        return legacyProjectId;
    }

    public void setLegacyProjectId(Integer legacyProjectId) {
        this.legacyProjectId = legacyProjectId;
    }

    public List<SimpleStoreModelView> getModels() {
        return models;
    }

    public void setModels(List<SimpleStoreModelView> models) {
        this.models = models;
    }
}
