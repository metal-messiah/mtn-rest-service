package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.StoreSurvey;
import com.mtn.model.simpleView.SimpleInteractionView;
import com.mtn.model.simpleView.SimpleStoreSurveyView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreSurveyView extends SimpleStoreSurveyView {

    private List<SimpleInteractionView> interactions = new ArrayList<>();

    public StoreSurveyView() {
        super();
    }

    public StoreSurveyView(StoreSurvey storeSurvey) {
        super(storeSurvey);

        this.interactions = storeSurvey.getInteractions().stream().filter(interaction -> interaction.getDeletedDate() == null).map(SimpleInteractionView::new).collect(Collectors.toList());
    }

    public List<SimpleInteractionView> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<SimpleInteractionView> interactions) {
        this.interactions = interactions;
    }
}
