package com.mtn.model.view.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.PermissionType;
import com.mtn.model.domain.auth.Permission;

/**
 * Created by Allen on 5/6/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimplePermissionView {

    protected Integer id;
    protected PermissionType systemName;
    protected String displayName;
    protected String description;

    public SimplePermissionView() {
    }

    public SimplePermissionView(Permission permission) {
        this.id = permission.getId();
        this.systemName = permission.getSystemName();
        this.displayName = permission.getDisplayName();
        this.description = permission.getDescription();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PermissionType getSystemName() {
        return systemName;
    }

    public void setSystemName(PermissionType systemName) {
        this.systemName = systemName;
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
