package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ShoppingCenter;
import com.mtn.model.simpleView.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 4/23/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCenterView extends AuditingEntityView {

    private Integer id;
    private String name;
    private String owner;
    private Integer legacyCasingId;
    private Integer legacyLocationId;
    private List<SimpleShoppingCenterCasingView> casings;
    private List<SimpleInteractionView> interactions;
    private List<SimpleSiteView> sites;
    private List<SimpleShoppingCenterSurveyView> surveys;

    public ShoppingCenterView(ShoppingCenter shoppingCenter) {
        super(shoppingCenter);
        this.id = shoppingCenter.getId();
        this.name = shoppingCenter.getName();
        this.owner = shoppingCenter.getOwner();
        this.legacyCasingId = shoppingCenter.getLegacyCasingId();
        this.legacyLocationId = shoppingCenter.getLegacyLocationId();
        this.casings = shoppingCenter.getCasings().stream().filter(casing -> casing.getDeletedDate() == null).map(SimpleShoppingCenterCasingView::new).collect(Collectors.toList());
        this.interactions = shoppingCenter.getInteractions().stream().filter(interaction -> interaction.getDeletedDate() == null).map(SimpleInteractionView::new).collect(Collectors.toList());
        this.sites = shoppingCenter.getSites().stream().filter(site -> site.getDeletedDate() == null).map(SimpleSiteView::new).collect(Collectors.toList());
        this.surveys = shoppingCenter.getSurveys().stream().filter(survey -> survey.getDeletedDate() == null).map(SimpleShoppingCenterSurveyView::new).collect(Collectors.toList());
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getLegacyCasingId() {
        return legacyCasingId;
    }

    public void setLegacyCasingId(Integer legacyCasingId) {
        this.legacyCasingId = legacyCasingId;
    }

    public Integer getLegacyLocationId() {
        return legacyLocationId;
    }

    public void setLegacyLocationId(Integer legacyLocationId) {
        this.legacyLocationId = legacyLocationId;
    }

    public List<SimpleSiteView> getSites() {
        return sites;
    }

    public void setSites(List<SimpleSiteView> sites) {
        this.sites = sites;
    }

    public List<SimpleInteractionView> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<SimpleInteractionView> interactions) {
        this.interactions = interactions;
    }

    public List<SimpleShoppingCenterSurveyView> getSurveys() {
        return surveys;
    }

    public void setSurveys(List<SimpleShoppingCenterSurveyView> surveys) {
        this.surveys = surveys;
    }

    public List<SimpleShoppingCenterCasingView> getCasings() {
        return casings;
    }

    public void setCasings(List<SimpleShoppingCenterCasingView> casings) {
        this.casings = casings;
    }
}
