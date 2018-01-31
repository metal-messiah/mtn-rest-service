package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Interaction;
import com.mtn.model.simpleView.*;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InteractionView extends AuditingEntityView {

    private Integer id;
    private LocalDateTime interactionDate;
    private Integer legacyCasingId;
    private SimpleProjectView project;
    private SimpleShoppingCenterView shoppingCenter;
    private SimpleShoppingCenterSurveyView shoppingCenterSurvey;
    private SimpleShoppingCenterCasingView shoppingCenterCasing;
    private SimpleStoreView store;
    private SimpleStoreSurveyView storeSurvey;
    private SimpleStoreCasingView storeCasing;

    public InteractionView(Interaction interaction) {
        super(interaction);
        this.id = interaction.getId();
        this.interactionDate = interaction.getInteractionDate();
        this.legacyCasingId = interaction.getLegacyCasingId();

        if (interaction.getProject() != null) {
            this.project = new SimpleProjectView(interaction.getProject());
        }

        if (interaction.getShoppingCenter() != null) {
            this.shoppingCenter = new SimpleShoppingCenterView(interaction.getShoppingCenter());
        }

        if (interaction.getShoppingCenterCasing() != null) {
            this.shoppingCenterCasing = new SimpleShoppingCenterCasingView(interaction.getShoppingCenterCasing());
        }

        if (interaction.getShoppingCenterSurvey() != null) {
            this.shoppingCenterSurvey = new SimpleShoppingCenterSurveyView(interaction.getShoppingCenterSurvey());
        }

        if (interaction.getStore() != null) {
            this.store = new SimpleStoreView(interaction.getStore());
        }

        if (interaction.getStoreCasing() != null) {
            this.storeCasing = new SimpleStoreCasingView(interaction.getStoreCasing());
        }

        if (interaction.getStoreSurvey() != null) {
            this.storeSurvey = new SimpleStoreSurveyView(interaction.getStoreSurvey());
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SimpleProjectView getProject() {
        return project;
    }

    public void setProject(SimpleProjectView project) {
        this.project = project;
    }

    public SimpleShoppingCenterView getShoppingCenter() {
        return shoppingCenter;
    }

    public void setShoppingCenter(SimpleShoppingCenterView shoppingCenter) {
        this.shoppingCenter = shoppingCenter;
    }

    public SimpleShoppingCenterSurveyView getShoppingCenterSurvey() {
        return shoppingCenterSurvey;
    }

    public void setShoppingCenterSurvey(SimpleShoppingCenterSurveyView shoppingCenterSurvey) {
        this.shoppingCenterSurvey = shoppingCenterSurvey;
    }

    public SimpleShoppingCenterCasingView getShoppingCenterCasing() {
        return shoppingCenterCasing;
    }

    public void setShoppingCenterCasing(SimpleShoppingCenterCasingView shoppingCenterCasing) {
        this.shoppingCenterCasing = shoppingCenterCasing;
    }

    public SimpleStoreView getStore() {
        return store;
    }

    public void setStore(SimpleStoreView store) {
        this.store = store;
    }

    public SimpleStoreSurveyView getStoreSurvey() {
        return storeSurvey;
    }

    public void setStoreSurvey(SimpleStoreSurveyView storeSurvey) {
        this.storeSurvey = storeSurvey;
    }

    public SimpleStoreCasingView getStoreCasing() {
        return storeCasing;
    }

    public void setStoreCasing(SimpleStoreCasingView storeCasing) {
        this.storeCasing = storeCasing;
    }

    public LocalDateTime getInteractionDate() {
        return interactionDate;
    }

    public void setInteractionDate(LocalDateTime interactionDate) {
        this.interactionDate = interactionDate;
    }

    public Integer getLegacyCasingId() {
        return legacyCasingId;
    }

    public void setLegacyCasingId(Integer legacyCasingId) {
        this.legacyCasingId = legacyCasingId;
    }
}
