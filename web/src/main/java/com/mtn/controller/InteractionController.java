package com.mtn.controller;

import com.mtn.model.domain.Interaction;
import com.mtn.model.simpleView.SimpleInteractionView;
import com.mtn.model.view.InteractionView;
import com.mtn.service.InteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interaction")
public class InteractionController extends CrudControllerImpl<Interaction> {

    @Autowired
    private InteractionService interactionService;

    @Override
    public InteractionService getEntityService() {
        return interactionService;
    }

    @Override
    public Object getViewFromModel(Interaction model) {
        return new InteractionView(model);
    }

    @Override
    public Object getSimpleViewFromModel(Interaction model) {
        return new SimpleInteractionView(model);
    }
}
