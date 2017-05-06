package com.mtn.model.view.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.auth.Role;

/**
 * Created by Allen on 5/6/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleRoleView {

    protected Integer id;
    protected String displayName;
    protected String description;

    public SimpleRoleView() {
    }

    public SimpleRoleView(Role role) {
        this.id = role.getId();
        this.displayName = role.getDisplayName();
        this.description = role.getDescription();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
