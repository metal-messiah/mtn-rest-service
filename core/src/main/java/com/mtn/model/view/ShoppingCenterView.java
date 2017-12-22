package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ShoppingCenter;
import com.mtn.model.simpleView.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 4/23/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCenterView extends SimpleShoppingCenterView {

    private List<SimpleShoppingCenterCasingView> casings = new ArrayList<>();
    private List<SimpleInteractionView> interactions = new ArrayList<>();
    private List<SimpleSiteView> sites = new ArrayList<>();
    private List<SimpleShoppingCenterSurveyView> surveys = new ArrayList<>();

    public ShoppingCenterView() {
    }

    public ShoppingCenterView(ShoppingCenter shoppingCenter) {
        super(shoppingCenter);

        this.casings = shoppingCenter.getCasings().stream().filter(casing -> casing.getDeletedDate() == null).map(SimpleShoppingCenterCasingView::new).collect(Collectors.toList());
        this.interactions = shoppingCenter.getInteractions().stream().filter(interaction -> interaction.getDeletedDate() == null).map(SimpleInteractionView::new).collect(Collectors.toList());
        this.sites = shoppingCenter.getSites().stream().filter(site -> site.getDeletedDate() == null).map(SimpleSiteView::new).collect(Collectors.toList());
        this.surveys = shoppingCenter.getSurveys().stream().filter(survey -> survey.getDeletedDate() == null).map(SimpleShoppingCenterSurveyView::new).collect(Collectors.toList());
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
