package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.CrudAction;
import com.mtn.model.domain.Permission;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimplePermissionView extends SimpleAuditingEntityView {

    private Integer id;
    private String systemName;
    private String displayName;
    private String description;
    private String subject;
    private CrudAction action;

    public SimplePermissionView() {
    }

    public SimplePermissionView(Permission permission) {
        super(permission);
        this.id = permission.getId();
        this.systemName = permission.getSystemName();
        this.displayName = permission.getDisplayName();
        this.description = permission.getDescription();
        this.subject = permission.getSubject();
        this.action = permission.getAction();
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public CrudAction getAction() {
        return action;
    }

    public void setAction(CrudAction action) {
        this.action = action;
    }
}
