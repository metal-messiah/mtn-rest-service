package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.CrudAction;
import com.mtn.model.domain.Permission;
import com.mtn.model.simpleView.SimplePermissionView;
import com.mtn.model.simpleView.SimpleRoleView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 5/6/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PermissionView extends AuditingEntityView {

    private String systemName;
    private String displayName;
    private String description;
    private String subject;
    private CrudAction action;

    private List<SimpleRoleView> roles;

    public PermissionView(Permission permission) {
        super(permission);

        this.systemName = permission.getSystemName();
        this.displayName = permission.getDisplayName();
        this.description = permission.getDescription();
        this.subject = permission.getSubject();
        this.action = permission.getAction();
        this.roles = permission.getRoles().stream().filter(role -> role.getDeletedDate() == null).map(SimpleRoleView::new).collect(Collectors.toList());
    }

    public String getSystemName() {
        return systemName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public String getSubject() {
        return subject;
    }

    public CrudAction getAction() {
        return action;
    }

    public List<SimpleRoleView> getRoles() {
        return roles;
    }

}
