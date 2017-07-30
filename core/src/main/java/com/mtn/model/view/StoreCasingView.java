package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.StoreCasing;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreCasingView extends SimpleStoreCasingView {

    private List<SimpleInteractionView> interactions = new ArrayList<>();

    public StoreCasingView() {
        super();
    }

    public StoreCasingView(StoreCasing storeCasing) {
        super(storeCasing);

        this.interactions = storeCasing.getInteractions().stream().filter(interaction -> interaction.getDeletedDate() == null).map(SimpleInteractionView::new).collect(Collectors.toList());
    }

    public List<SimpleInteractionView> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<SimpleInteractionView> interactions) {
        this.interactions = interactions;
    }
}
