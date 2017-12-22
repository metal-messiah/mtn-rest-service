package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Project;
import com.mtn.model.simpleView.SimpleInteractionView;
import com.mtn.model.simpleView.SimpleProjectView;
import com.mtn.model.simpleView.SimpleStoreModelView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectView extends SimpleProjectView {

    private List<SimpleInteractionView> interactions = new ArrayList<>();
    private List<SimpleStoreModelView> models = new ArrayList<>();

    public ProjectView() {
        super();
    }

    public ProjectView(Project project) {
        super(project);

        this.interactions = project.getInteractions().stream().filter(interaction -> interaction.getDeletedDate() == null).map(SimpleInteractionView::new).collect(Collectors.toList());
        this.models = project.getModels().stream().filter(model -> model.getDeletedDate() == null).map(SimpleStoreModelView::new).collect(Collectors.toList());
    }

    public List<SimpleInteractionView> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<SimpleInteractionView> interactions) {
        this.interactions = interactions;
    }

    public List<SimpleStoreModelView> getModels() {
        return models;
    }

    public void setModels(List<SimpleStoreModelView> models) {
        this.models = models;
    }
}
