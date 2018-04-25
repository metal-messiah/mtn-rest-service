package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Role;

/**
 * Created by Allen on 5/6/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleRoleView {

    private Integer id;
    private String displayName;

    public SimpleRoleView(Role role) {
        this.id = role.getId();
        this.displayName = role.getDisplayName();
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

}
