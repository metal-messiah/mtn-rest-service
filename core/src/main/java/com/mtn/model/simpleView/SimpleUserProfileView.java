package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.UserProfile;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleUserProfileView extends SimpleAuditingEntityView {

    private String email;

    public SimpleUserProfileView() {
    }

    public SimpleUserProfileView(UserProfile userProfile) {
        super(userProfile);
        this.email = userProfile.getEmail();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
