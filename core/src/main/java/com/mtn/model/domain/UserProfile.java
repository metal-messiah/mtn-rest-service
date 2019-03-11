package com.mtn.model.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 4/21/2017.
 */
@Entity
@Table
@AttributeOverride(name = "id", column = @Column(name = "user_profile_id"))
public class UserProfile extends AuditingEntity {

    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    private Group group;
    private Role role;
    private Boundary restrictionBoundary;

    private List<StoreList> subscribedStoreLists = new ArrayList<>();
    private StoreList[] createdStoreLists;

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

    @ManyToMany(mappedBy = "subscribedStoreLists")
    public List<StoreList> getSubscribedStoreLists() {
        return subscribedStoreLists;
    }

    public void setSubscribedStoreLists(List<StoreList> subscribedStoreLists) {
        this.subscribedStoreLists = subscribedStoreLists;
    }

    @ManyToOne(cascade = { CascadeType.MERGE })
    @JoinColumn(name = "auth_role_id")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @ManyToOne(cascade = { CascadeType.MERGE })
    @JoinColumn(name = "auth_group_id")
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @OneToOne
    @JoinColumn(name = "restriction_boundary_id")
    public Boundary getRestrictionBoundary() {
        return restrictionBoundary;
    }

    public void setRestrictionBoundary(Boundary restrictionBoundary) {
        this.restrictionBoundary = restrictionBoundary;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
