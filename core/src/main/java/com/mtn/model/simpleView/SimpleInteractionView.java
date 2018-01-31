package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Interaction;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleInteractionView {

    private Integer id;
    private SimpleProjectView project;
    private LocalDateTime interactionDate;

    public SimpleInteractionView(Interaction interaction) {
        this.id = interaction.getId();
        this.interactionDate = interaction.getInteractionDate();

        if (interaction.getProject() != null) {
            this.project = new SimpleProjectView(interaction.getProject());
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

    public LocalDateTime getInteractionDate() {
        return interactionDate;
    }

    public void setInteractionDate(LocalDateTime interactionDate) {
        this.interactionDate = interactionDate;
    }
}
