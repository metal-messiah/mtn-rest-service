package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.simpleView.SimpleGroupView;
import com.mtn.model.simpleView.SimpleRoleView;
import com.mtn.model.simpleView.SimpleUserProfileView;

import java.time.LocalDateTime;

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
}
