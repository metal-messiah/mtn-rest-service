package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Role;
import com.mtn.model.simpleView.SimplePermissionView;
import com.mtn.model.simpleView.SimpleUserProfileView;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleView extends AuditingEntityView {

    private String displayName;
    private String description;

    private List<SimpleUserProfileView> members;
    private List<SimplePermissionView> permissions;

    public RoleView() {
    }

    public RoleView(Role role) {
        super(role);

        this.displayName = role.getDisplayName();
        this.description = role.getDescription();
        this.members = role.getMembers().stream().filter(member -> member.getDeletedDate() == null).map(SimpleUserProfileView::new).collect(Collectors.toList());
        this.permissions = role.getPermissions().stream().map(SimplePermissionView::new).collect(Collectors.toList());
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

    public List<SimpleUserProfileView> getMembers() {
        return members;
    }

    public void setMembers(List<SimpleUserProfileView> members) {
        this.members = members;
    }

    public List<SimplePermissionView> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<SimplePermissionView> permissions) {
        this.permissions = permissions;
    }
}
