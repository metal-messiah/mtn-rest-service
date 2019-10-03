package com.mtn.model.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private List<Boundary> boundaries = new ArrayList<>();

    private List<StoreList> subscribedStoreLists = new ArrayList<>();
    private List<StoreList> createdStoreLists = new ArrayList<>();

    private Set<Permission> permissions = new HashSet<>();

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

    @ManyToMany(mappedBy = "subscribers")
    public List<StoreList> getSubscribedStoreLists() {
        return subscribedStoreLists;
    }

    public void setSubscribedStoreLists(List<StoreList> subscribedStoreLists) {
        this.subscribedStoreLists = subscribedStoreLists;
    }

    public void addSubscribedStoreList(StoreList storeList) {
        this.getSubscribedStoreLists().add(storeList);
    }

    public void removeSubscribedStoreList(StoreList storeList) {
        this.getSubscribedStoreLists().removeIf(sl -> sl.getId().equals(storeList.getId()));
    }

    @OneToMany
    @JoinColumn(name = "created_by")
    public List<StoreList> getCreatedStoreLists() {
        return createdStoreLists;
    }

    public void setCreatedStoreLists(List<StoreList> createdStoreLists) {
        this.createdStoreLists = createdStoreLists;
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

    @ManyToMany
    @JoinTable(name = "user_boundary", joinColumns = @JoinColumn(name = "user_profile_id", referencedColumnName = "user_profile_id"), inverseJoinColumns = @JoinColumn(name = "boundary_id", referencedColumnName = "boundary_id"))
    public List<Boundary> getBoundaries() {
        return boundaries;
    }

    public void setBoundaries(List<Boundary> boundaries) {
        this.boundaries = boundaries;
    }

    public void addBoundary(Boundary boundary) {
        this.getBoundaries().add(boundary);
    }

    public void addBoundaries(List<Boundary> boundaries) {
        this.getBoundaries().addAll(boundaries);
    }

    public void removeBoundary(Boundary boundary) {
        this.getBoundaries().removeIf(b -> b.getId().equals(boundary.getId()));
    }

    public void removeBoundaryById(Integer boundaryId) {
        this.getBoundaries().removeIf(b -> b.getId().equals(boundaryId));
    }

    public void removeBoundaries(List<Boundary> boundaries) {
        this.getBoundaries().removeAll(boundaries);
    }

    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_permission",
            joinColumns = @JoinColumn(name = "user_profile_id", referencedColumnName = "user_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "auth_permission_id", referencedColumnName = "auth_permission_id")
    )
    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
