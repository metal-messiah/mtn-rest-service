package com.mtn.model.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 4/21/2017.
 */
@Entity
@Table
public class UserProfile extends AuditingEntity {

    private Integer id;
    private String email;
    private String firstName;
    private String lastName;

    private List<UserIdentity> identities = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user_profile_id")
    @SequenceGenerator(name = "seq_user_profile_id", sequenceName = "seq_user_profile_id", allocationSize = 1)
    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    @OneToMany(mappedBy = "userProfile", cascade = {CascadeType.ALL})
    public List<UserIdentity> getIdentities() {
        return identities;
    }

    public void setIdentities(List<UserIdentity> identities) {
        this.identities = identities;
    }
}
