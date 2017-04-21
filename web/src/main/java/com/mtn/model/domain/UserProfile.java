package com.mtn.model.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 4/21/2017.
 */
@Entity
@Table
public class UserProfile {

    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private UserProfile createdBy;
    private LocalDateTime createdDate;
    private UserProfile deletedBy;
    private LocalDateTime deletedDate;
    private UserProfile updatedBy;
    private LocalDateTime updatedDate;

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

    @ManyToOne
    @JoinColumn(name = "created_by")
    public UserProfile getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final UserProfile createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @ManyToOne
    @JoinColumn(name = "deleted_by")
    public UserProfile getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(final UserProfile deletedBy) {
        this.deletedBy = deletedBy;
    }

    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(final LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }

    @ManyToOne
    @JoinColumn(name = "updated_by")
    public UserProfile getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final UserProfile updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    @OneToMany(mappedBy = "userProfile")
    public List<UserIdentity> getIdentities() {
        return identities;
    }

    public void setIdentities(List<UserIdentity> identities) {
        this.identities = identities;
    }
}
