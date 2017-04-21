package com.mtn.model.domain;

import com.mtn.constant.Provider;

import javax.persistence.*;

/**
 * Created by Allen on 4/21/2017.
 */
@Entity
@Table
public class UserIdentity {

    private Integer id;
    private UserProfile userProfile;
    private Provider provider;
    private String providerUserId;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user_identity_id")
    @SequenceGenerator(name = "seq_user_identity_id", sequenceName = "seq_user_identity_id", allocationSize = 1)
    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "user_profile_id")
    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(final UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    @Enumerated(EnumType.STRING)
    public Provider getProvider() {
        return provider;
    }

    public void setProvider(final Provider provider) {
        this.provider = provider;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(final String providerUserId) {
        this.providerUserId = providerUserId;
    }
}
