package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ShoppingCenterSurvey;
import com.mtn.model.simpleView.SimpleInteractionView;
import com.mtn.model.simpleView.SimpleShoppingCenterAccessView;
import com.mtn.model.simpleView.SimpleShoppingCenterSurveyView;
import com.mtn.model.simpleView.SimpleShoppingCenterTenantView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 5/4/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCenterSurveyView extends SimpleShoppingCenterSurveyView {

    protected List<SimpleInteractionView> interactions = new ArrayList<>();
    protected List<SimpleShoppingCenterAccessView> accesses = new ArrayList<>();
    protected List<SimpleShoppingCenterTenantView> tenants = new ArrayList<>();

    public ShoppingCenterSurveyView() {
    }

    public ShoppingCenterSurveyView(ShoppingCenterSurvey survey) {
        super(survey);

        this.accesses = survey.getAccesses().stream().filter(access -> access.getDeletedDate() == null).map(SimpleShoppingCenterAccessView::new).collect(Collectors.toList());
        this.interactions = survey.getInteractions().stream().filter(interaction -> interaction.getDeletedDate() == null).map(SimpleInteractionView::new).collect(Collectors.toList());
        this.tenants = survey.getTenants().stream().filter(tenant -> tenant.getDeletedDate() == null).map(SimpleShoppingCenterTenantView::new).collect(Collectors.toList());
    }

    public List<SimpleInteractionView> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<SimpleInteractionView> interactions) {
        this.interactions = interactions;
    }

    public List<SimpleShoppingCenterAccessView> getAccesses() {
        return accesses;
    }

    public void setAccesses(List<SimpleShoppingCenterAccessView> accesses) {
        this.accesses = accesses;
    }

    public List<SimpleShoppingCenterTenantView> getTenants() {
        return tenants;
    }

    public void setTenants(List<SimpleShoppingCenterTenantView> tenants) {
        this.tenants = tenants;
    }
}
