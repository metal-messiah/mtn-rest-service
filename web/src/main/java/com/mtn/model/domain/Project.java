package com.mtn.model.domain;

import com.vividsolutions.jts.geom.Polygon;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Project extends AuditingEntity implements Identifiable {

    private Integer id;
    private String projectName;
    private String metroArea;
    private String clientName;
    private Integer projectYear;
    private Integer projectMonth;
    private Boolean isActive = false;
    private Boolean isPrimaryData = false;
    private LocalDateTime startedDate;
    private LocalDateTime completedDate;
    private String source;
    private Polygon boundary;
    private Integer version;
    private Integer legacyProjectId;

    private List<StoreModel> models = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        version = 1;
    }

    @PreUpdate
    public void preUpdate() {
        version++;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_project_id")
    @SequenceGenerator(name = "seq_project_id", sequenceName = "seq_project_id", allocationSize = 1)
    @Column(name = "project_id")
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getIsPrimaryData() {
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

    public Polygon getBoundary() {
        return boundary;
    }

    public void setBoundary(Polygon boundary) {
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

    @OneToMany(mappedBy = "project")
    public List<StoreModel> getModels() {
        return models;
    }

    public void setModels(List<StoreModel> models) {
        this.models = models;
    }
}
