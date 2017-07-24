package com.mtn.model.domain.auth;

import com.mtn.model.domain.Identifiable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 5/6/2017.
 */
@Entity
@Table(name = "auth_permission")
public class Permission implements Identifiable {

    private Integer id;
    private String systemName;
    private String displayName;
    private String description;

    private List<Role> roles = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_auth_permission_id")
    @SequenceGenerator(name = "seq_auth_permission_id", sequenceName = "seq_auth_permission_id", allocationSize = 1)
    @Column(name = "auth_permission_id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

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

    @ManyToMany(mappedBy = "permissions", cascade = {CascadeType.MERGE})
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
