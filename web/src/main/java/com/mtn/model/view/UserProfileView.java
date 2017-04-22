package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 4/22/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfileView extends SimpleUserProfileView {

    private List<SimpleUserIdentityView> identities = new ArrayList<>();

    public List<SimpleUserIdentityView> getIdentities() {
        return identities;
    }

    public void setIdentities(List<SimpleUserIdentityView> identities) {
        this.identities = identities;
    }
}
