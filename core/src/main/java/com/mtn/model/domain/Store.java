package com.mtn.model.domain;

import com.mtn.constant.StoreType;
import com.mtn.model.simpleView.SimpleStoreView;
import com.mtn.model.view.StoreView;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 4/26/2017.
 */
@Entity
@Table
public class Store extends AuditingEntity implements Identifiable {

    private Integer id;
    private Site site;
    private String name;
    private StoreType type;
    private LocalDateTime openedDate;
    private LocalDateTime closedDate;
    private Company parentCompany;
    private String storeNumber;
    private Integer legacyLocationId;

    private List<StoreCasing> casings = new ArrayList<>();
    private List<Interaction> interactions = new ArrayList<>();
    private List<StoreModel> models = new ArrayList<>();
    private List<StoreSurvey> surveys = new ArrayList<>();
    private List<StoreVolume> volumes = new ArrayList<>();

    public Store() {
    }

    @Id
    @GeneratedValue
    @Column(name = "store_id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "site_id")
    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "store_type")
    public StoreType getType() {
        return type;
    }

    public void setType(StoreType type) {
        this.type = type;
    }

    @Column(name = "opened_date")
    public LocalDateTime getOpenedDate() {
        return openedDate;
    }

    public void setOpenedDate(LocalDateTime openedDate) {
        this.openedDate = openedDate;
    }

    @Column(name = "closed_date")
    public LocalDateTime getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(LocalDateTime closedDate) {
        this.closedDate = closedDate;
    }

    @OneToMany(mappedBy = "store")
    public List<StoreSurvey> getSurveys() {
        return surveys;
    }

    public void setSurveys(List<StoreSurvey> surveys) {
        this.surveys = surveys;
    }

    @ManyToOne
    @JoinColumn(name = "parent_company_id")
    public Company getParentCompany() {
        return parentCompany;
    }

    public void setParentCompany(Company parentCompany) {
        this.parentCompany = parentCompany;
    }

    public String getStoreNumber() {
        return storeNumber;
    }

    public void setStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }

    public Integer getLegacyLocationId() {
        return legacyLocationId;
    }

    public void setLegacyLocationId(Integer legacyLocationId) {
        this.legacyLocationId = legacyLocationId;
    }

    @OneToMany(mappedBy = "store")
    public List<StoreVolume> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<StoreVolume> volumes) {
        this.volumes = volumes;
    }

    @OneToMany(mappedBy = "store")
    public List<StoreCasing> getCasings() {
        return casings;
    }

    public void setCasings(List<StoreCasing> casings) {
        this.casings = casings;
    }

    @OneToMany(mappedBy = "store")
    public List<StoreModel> getModels() {
        return models;
    }

    public void setModels(List<StoreModel> models) {
        this.models = models;
    }

    @OneToMany(mappedBy = "store")
    public List<Interaction> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<Interaction> interactions) {
        this.interactions = interactions;
    }
}
