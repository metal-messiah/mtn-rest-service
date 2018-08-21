package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.simpleView.SimpleGroupView;
import com.mtn.model.simpleView.SimpleRoleView;

/**
 * Created by Allen on 4/22/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfileView extends AuditingEntityView {

    private String email;
    private String firstName;
    private String lastName;

    private SimpleGroupView group;
    private SimpleRoleView role;

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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public SimpleGroupView getGroup() {
        return group;
    }

    public SimpleRoleView getRole() {
        return role;
    }
}
