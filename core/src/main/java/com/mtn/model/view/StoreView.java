package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Store;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 4/26/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreView extends SimpleStoreView {

    private SimpleSiteView site;
    private CompanyView parentCompany;

    private List<SimpleStoreCasingView> casings = new ArrayList<>();
    private List<SimpleInteractionView> interactions = new ArrayList<>();
    private List<SimpleStoreModelView> models = new ArrayList<>();
    private List<SimpleStoreSurveyView> surveys = new ArrayList<>();
    private List<SimpleStoreVolumeView> volumes = new ArrayList<>();

    public StoreView() {
    }

    public StoreView(Store store) {
        super(store);

        this.site = new SimpleSiteView(store.getSite());

        if (store.getParentCompany() != null) {
            this.parentCompany = new CompanyView(store.getParentCompany());
        }

        this.casings = store.getCasings().stream().filter(casing -> casing.getDeletedDate() == null).map(SimpleStoreCasingView::new).collect(Collectors.toList());
        this.interactions = store.getInteractions().stream().filter(interaction -> interaction.getDeletedDate() == null).map(SimpleInteractionView::new).collect(Collectors.toList());
        this.models = store.getModels().stream().filter(model -> model.getDeletedDate() == null).map(SimpleStoreModelView::new).collect(Collectors.toList());
        this.surveys = store.getSurveys().stream().filter(storeSurvey -> storeSurvey.getDeletedDate() == null).map(SimpleStoreSurveyView::new).collect(Collectors.toList());
        this.volumes = store.getVolumes().stream().filter(volume -> volume.getDeletedDate() == null).map(SimpleStoreVolumeView::new).collect(Collectors.toList());
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

    @Override
    public CompanyView getParentCompany() {
        return parentCompany;
    }

    public void setParentCompany(CompanyView parentCompany) {
        this.parentCompany = parentCompany;
    }

    public List<SimpleStoreCasingView> getCasings() {
        return casings;
    }

    public void setCasings(List<SimpleStoreCasingView> casings) {
        this.casings = casings;
    }

    public List<SimpleStoreVolumeView> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<SimpleStoreVolumeView> volumes) {
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
