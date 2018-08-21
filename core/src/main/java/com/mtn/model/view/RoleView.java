package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Role;
import com.mtn.model.simpleView.SimplePermissionView;
import com.mtn.model.simpleView.SimpleRoleView;
import com.mtn.model.simpleView.SimpleUserProfileView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 5/6/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleView extends AuditingEntityView {

    private String displayName;
    private String description;

    private List<SimpleUserProfileView> members;
    private List<SimplePermissionView> permissions;

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

    public String getDescription() {
        return description;
    }

    public List<SimpleUserProfileView> getMembers() {
        return members;
    }

    public List<SimplePermissionView> getPermissions() {
        return permissions;
    }

}
