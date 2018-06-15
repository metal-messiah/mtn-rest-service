package com.mtn.model.domain;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by Allen on 4/21/2017.
 */
@Entity
@Table
public class UserProfile extends AuditingEntity implements Identifiable {

    private Integer id;
    private String email;
    private String firstName;
    private Group group;
    private String lastName;
    private Role role;

    @PrePersist
    @PreUpdate
    public void prePersist() {
        email = email.toLowerCase();
    }

    @Id
    @GeneratedValue
    @Column(name = "user_profile_id")
    public Integer getId() {
        return id;
    }

    @Override
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

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "auth_role_id")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "auth_group_id")
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public static UserProfile build( String email ) {
        UserProfile userProfile = new UserProfile();
        userProfile.setEmail( email );
        return userProfile;
    }

}
