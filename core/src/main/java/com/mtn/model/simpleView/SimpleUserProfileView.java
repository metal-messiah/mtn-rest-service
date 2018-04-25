package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.UserProfile;

/**
 * Created by Allen on 4/21/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleUserProfileView {

    private Integer id;
    private String email;

    public SimpleUserProfileView(UserProfile userProfile) {
        this.id = userProfile.getId();
        this.email = userProfile.getEmail();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
