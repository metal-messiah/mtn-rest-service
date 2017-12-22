package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ShoppingCenterCasing;
import com.mtn.model.simpleView.SimpleInteractionView;
import com.mtn.model.simpleView.SimpleShoppingCenterCasingView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCenterCasingView extends SimpleShoppingCenterCasingView {

    private List<SimpleInteractionView> interactions = new ArrayList<>();

    public ShoppingCenterCasingView() {
        super();
    }

    public ShoppingCenterCasingView(ShoppingCenterCasing casing) {
        super(casing);

        this.interactions = casing.getInteractions().stream().filter(interaction -> interaction.getDeletedDate() == null).map(SimpleInteractionView::new).collect(Collectors.toList());
    }

    public List<SimpleInteractionView> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<SimpleInteractionView> interactions) {
        this.interactions = interactions;
    }
}
