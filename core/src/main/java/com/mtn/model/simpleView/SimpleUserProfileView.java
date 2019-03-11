package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.UserProfile;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleUserProfileView extends SimpleAuditingEntityView {

    private String email;

    private Integer subscribedStoreListCount;
    private Integer createdStoreListCount;

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

    public Integer getSubscribedStoreListCount() {
        return this.subscribedStoreListCount;
    }

    public void setSubscribedStoreListCount(UserProfile userProfile) {
        this.subscribedStoreListCount = userProfile.getSubscribedStoreLists().size();
    }

    public Integer getCreatedStoreListCount() {
        return this.createdStoreListCount;
    }

    public void setCreatedStoreListCount(UserProfile userProfile) {
        this.subscribedStoreListCount = userProfile.getCreatedStoreLists().size();
    }
}
