package com.mtn.model.domain;

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
    private LocalDateTime dateStarted;
    private LocalDateTime dateCompleted;
    private String source;
    private Integer legacyProjectId;

    private List<StoreModel> models = new ArrayList<>();
    private List<StoreCasing> storeCasings = new ArrayList<>();
    private List<ShoppingCenterCasing> shoppingCenterCasings = new ArrayList<>();

    @Id
    @GeneratedValue
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

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public Boolean getIsPrimaryData() {
        return isPrimaryData;
    }

    public void setIsPrimaryData(Boolean primaryData) {
        isPrimaryData = primaryData;
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

    @OneToMany(mappedBy = "project")
    public List<StoreModel> getModels() {
        return models;
    }

    public void setModels(List<StoreModel> models) {
        this.models = models;
    }

    @ManyToMany(mappedBy = "projects")
    public List<StoreCasing> getStoreCasings() {
        return storeCasings;
    }

    public void setStoreCasings(List<StoreCasing> storeCasings) {
        this.storeCasings = storeCasings;
    }

    @ManyToMany(mappedBy = "projects")
    public List<ShoppingCenterCasing> getShoppingCenterCasings() {
        return shoppingCenterCasings;
    }

    public void setShoppingCenterCasings(List<ShoppingCenterCasing> shoppingCenterCasings) {
        this.shoppingCenterCasings = shoppingCenterCasings;
    }
}
