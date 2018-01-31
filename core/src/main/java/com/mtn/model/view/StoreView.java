package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.StoreType;
import com.mtn.model.domain.Store;
import com.mtn.model.simpleView.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 4/26/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreView extends AuditingEntityView {

    private Integer id;
    private String name;
    private StoreType type;
    private LocalDateTime openedDate;
    private LocalDateTime closedDate;
    private String storeNumber;
    private Integer legacyLocationId;

    private SimpleSiteView site;
    private SimpleCompanyView parentCompany;

    private List<SimpleStoreCasingView> casings;
    private List<SimpleInteractionView> interactions;
    private List<SimpleStoreModelView> models;
    private List<SimpleStoreSurveyView> surveys;
    private List<StoreVolumeView> volumes;

    public StoreView(Store store) {
        super(store);

        this.id = store.getId();
        this.name = store.getName();
        this.type = store.getType();
        this.openedDate = store.getOpenedDate();
        this.closedDate = store.getClosedDate();
        this.legacyLocationId = store.getLegacyLocationId();
        this.storeNumber = store.getStoreNumber();

        this.site = new SimpleSiteView(store.getSite());

        if (store.getParentCompany() != null) {
            this.parentCompany = new SimpleCompanyView(store.getParentCompany());
        }

        this.casings = store.getCasings().stream().filter(casing -> casing.getDeletedDate() == null).map(SimpleStoreCasingView::new).collect(Collectors.toList());
        this.interactions = store.getInteractions().stream().filter(interaction -> interaction.getDeletedDate() == null).map(SimpleInteractionView::new).collect(Collectors.toList());
        this.models = store.getModels().stream().filter(model -> model.getDeletedDate() == null).map(SimpleStoreModelView::new).collect(Collectors.toList());
        this.surveys = store.getSurveys().stream().filter(storeSurvey -> storeSurvey.getDeletedDate() == null).map(SimpleStoreSurveyView::new).collect(Collectors.toList());
        this.volumes = store.getVolumes().stream().filter(volume -> volume.getDeletedDate() == null).map(StoreVolumeView::new).collect(Collectors.toList());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StoreType getType() {
        return type;
    }

    public void setType(StoreType type) {
        this.type = type;
    }

    public LocalDateTime getOpenedDate() {
        return openedDate;
    }

    public void setOpenedDate(LocalDateTime openedDate) {
        this.openedDate = openedDate;
    }

    public LocalDateTime getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(LocalDateTime closedDate) {
        this.closedDate = closedDate;
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

    public SimpleSiteView getSite() {
        return site;
    }

    public void setSite(SimpleSiteView site) {
        this.site = site;
    }

    public List<SimpleStoreSurveyView> getSurveys() {
        return surveys;
    }

    public void setSurveys(List<SimpleStoreSurveyView> surveys) {
        this.surveys = surveys;
    }

    public SimpleCompanyView getParentCompany() {
        return parentCompany;
    }

    public void setParentCompany(SimpleCompanyView parentCompany) {
        this.parentCompany = parentCompany;
    }

    public List<SimpleStoreCasingView> getCasings() {
        return casings;
    }

    public void setCasings(List<SimpleStoreCasingView> casings) {
        this.casings = casings;
    }

    public List<StoreVolumeView> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<StoreVolumeView> volumes) {
        this.volumes = volumes;
    }

    public List<SimpleStoreModelView> getModels() {
        return models;
    }

    public void setModels(List<SimpleStoreModelView> models) {
        this.models = models;
    }

    public List<SimpleInteractionView> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<SimpleInteractionView> interactions) {
        this.interactions = interactions;
    }

}
