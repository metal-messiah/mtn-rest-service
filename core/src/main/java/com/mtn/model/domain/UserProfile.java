package com.mtn.model.domain;

import javax.persistence.*;

/**
 * Created by Allen on 4/21/2017.
 */
@Entity
@Table
@AttributeOverride(name="id", column=@Column(name="user_profile_id"))
public class UserProfile extends AuditingEntity {

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

}
