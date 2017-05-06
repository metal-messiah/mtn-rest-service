package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.view.auth.SimpleGroupView;
import com.mtn.model.view.auth.SimpleRoleView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 4/22/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfileView extends SimpleUserProfileView {

    private SimpleUserProfileView createdBy;
    private LocalDateTime createdDate;
    private SimpleUserProfileView updatedBy;
    private LocalDateTime updatedDate;
    private SimpleGroupView group;
    private SimpleRoleView role;

    private List<SimpleUserIdentityView> identities = new ArrayList<>();

    public UserProfileView() {
    }

    public UserProfileView(UserProfile userProfile) {
        super(userProfile);

        this.createdBy = new SimpleUserProfileView(userProfile.getCreatedBy());
        this.createdDate = userProfile.getCreatedDate();
        this.updatedBy = new SimpleUserProfileView(userProfile.getUpdatedBy());
        this.updatedDate = userProfile.getUpdatedDate();

        if (userProfile.getGroup() != null) {
            this.group = new SimpleGroupView(userProfile.getGroup());
        }

        if (userProfile.getRole() != null) {
            this.role = new SimpleRoleView(userProfile.getRole());
        }

        this.identities = userProfile.getIdentities().stream().map(SimpleUserIdentityView::new).collect(Collectors.toList());
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

    public SimpleGroupView getGroup() {
        return group;
    }

    public void setGroup(SimpleGroupView group) {
        this.group = group;
    }

    public SimpleRoleView getRole() {
        return role;
    }

    public void setRole(SimpleRoleView role) {
        this.role = role;
    }

    public List<SimpleUserIdentityView> getIdentities() {
        return identities;
    }

    public void setIdentities(List<SimpleUserIdentityView> identities) {
        this.identities = identities;
    }
}
