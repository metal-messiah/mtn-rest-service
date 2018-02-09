package com.mtn.model.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Allen on 5/6/2017.
 */
@Entity
@Table(name = "auth_role")
public class Role extends AuditingEntity implements Identifiable {

    private Integer id;
    private String displayName;
    private String description;

    private Set<Permission> permissions = new HashSet<>();
    private Set<UserProfile> members = new HashSet<>();

    @Id
    @GeneratedValue
    @Column(name = "auth_role_id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotNull
    @Column(unique = true)
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "auth_role_auth_permission",
            joinColumns = @JoinColumn(name = "auth_role_id", referencedColumnName = "auth_role_id"),
            inverseJoinColumns = @JoinColumn(name = "auth_permission_id", referencedColumnName = "auth_permission_id")
    )
    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    @OneToMany(mappedBy = "role", cascade = {CascadeType.MERGE})
    public Set<UserProfile> getMembers() {
        return members;
    }

    public void setMembers(Set<UserProfile> members) {
        this.members = members;
    }
}
