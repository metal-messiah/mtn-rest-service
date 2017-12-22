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
public class RoleView extends SimpleRoleView {

    private SimpleUserProfileView createdBy;
    private LocalDateTime createdDate;
    private SimpleUserProfileView updatedBy;
    private LocalDateTime updatedDate;
    private Integer version;

    private List<SimpleUserProfileView> members = new ArrayList<>();
    private List<SimplePermissionView> permissions = new ArrayList<>();

    public RoleView() {
    }

    public RoleView(Role role) {
        super(role);

        this.createdBy = new SimpleUserProfileView(role.getCreatedBy());
        this.createdDate = role.getCreatedDate();
        this.updatedBy = new SimpleUserProfileView(role.getUpdatedBy());
        this.updatedDate = role.getUpdatedDate();
        this.version = role.getVersion();

        this.members = role.getMembers().stream().filter(member -> member.getDeletedDate() == null).map(SimpleUserProfileView::new).collect(Collectors.toList());
        this.permissions = role.getPermissions().stream().map(SimplePermissionView::new).collect(Collectors.toList());
    }

    public SimpleUserProfileView getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(SimpleUserProfileView createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public SimpleUserProfileView getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(SimpleUserProfileView updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getVersion() { return version; }

    public void setVersion(Integer version) { this.version = version; }

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
