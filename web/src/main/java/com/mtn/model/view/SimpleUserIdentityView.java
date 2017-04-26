package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.Provider;
import com.mtn.model.domain.UserIdentity;

/**
 * Created by Allen on 4/22/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleUserIdentityView {

    private Integer id;
    private Provider provider;
    private String providerUserId;

    public SimpleUserIdentityView() {
    }

    public SimpleUserIdentityView(UserIdentity userIdentity) {
        this.id = userIdentity.getId();
        this.provider = userIdentity.getProvider();
        this.providerUserId = userIdentity.getProviderUserId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }
}
