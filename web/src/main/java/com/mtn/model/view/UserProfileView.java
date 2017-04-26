package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.UserProfile;

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

    private List<SimpleUserIdentityView> identities = new ArrayList<>();

    public UserProfileView() {
    }

    public UserProfileView(UserProfile userProfile) {
        super(userProfile);

        this.createdBy = new SimpleUserProfileView(userProfile.getCreatedBy());
        this.createdDate = userProfile.getCreatedDate();
        this.updatedBy = new SimpleUserProfileView(userProfile.getUpdatedBy());
        this.updatedDate = userProfile.getUpdatedDate();

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

    public List<SimpleUserIdentityView> getIdentities() {
        return identities;
    }

    public void setIdentities(List<SimpleUserIdentityView> identities) {
        this.identities = identities;
    }
}
