package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Role;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleRoleView extends SimpleAuditingEntityView {

    private String displayName;

    public SimpleRoleView() {
    }

    public SimpleRoleView(Role role) {
        super(role);
        this.displayName = role.getDisplayName();
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}
