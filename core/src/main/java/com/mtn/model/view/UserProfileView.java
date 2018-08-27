package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.simpleView.SimpleGroupView;
import com.mtn.model.simpleView.SimpleRoleView;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfileView extends AuditingEntityView {

    private String email;
    private String firstName;
    private String lastName;

    private SimpleGroupView group;
    private SimpleRoleView role;

    public UserProfileView() {
    }

    public UserProfileView(UserProfile userProfile) {
        super(userProfile);

        this.email = userProfile.getEmail();
        this.firstName = userProfile.getFirstName();
        this.lastName = userProfile.getLastName();

        if (userProfile.getGroup() != null) {
            this.group = new SimpleGroupView(userProfile.getGroup());
        }

        if (userProfile.getRole() != null) {
            this.role = new SimpleRoleView(userProfile.getRole());
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
