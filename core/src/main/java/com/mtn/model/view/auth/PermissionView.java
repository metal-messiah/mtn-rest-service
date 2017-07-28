package com.mtn.model.view.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.auth.Permission;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 5/6/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PermissionView extends SimplePermissionView {

    private List<SimpleRoleView> roles = new ArrayList<>();

    public PermissionView() {
    }

    public PermissionView(Permission permission) {
        super(permission);

        this.roles = permission.getRoles().stream().filter(role -> role.getDeletedDate() == null).map(SimpleRoleView::new).collect(Collectors.toList());
    }

    public List<SimpleRoleView> getRoles() {
        return roles;
    }

    public void setRoles(List<SimpleRoleView> roles) {
        this.roles = roles;
    }
}
