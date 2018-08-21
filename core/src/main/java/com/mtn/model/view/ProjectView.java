package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Project;
import com.mtn.model.simpleView.SimpleStoreModelView;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectView extends AuditingEntityView {

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
        this.legacyProjectId = project.getLegacyProjectId();
        this.models = project.getModels().stream().filter(model -> model.getDeletedDate() == null).map(SimpleStoreModelView::new).collect(Collectors.toList());
    }

    public String getProjectName() {
        return projectName;
    }

    public String getMetroArea() {
        return metroArea;
    }

    public String getClientName() {
        return clientName;
    }

    public Integer getProjectYear() {
        return projectYear;
    }

    public Integer getProjectMonth() {
        return projectMonth;
    }

    public Boolean getActive() {
        return active;
    }

    public Boolean getPrimaryData() {
        return primaryData;
    }

    public LocalDateTime getDateStarted() {
        return dateStarted;
    }

    public LocalDateTime getDateCompleted() {
        return dateCompleted;
    }

    public String getSource() {
        return source;
    }

    public Integer getLegacyProjectId() {
        return legacyProjectId;
    }

    public List<SimpleStoreModelView> getModels() {
        return models;
    }

}
