package com.mtn.model.domain;

import com.mtn.constant.CrudAction;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "auth_permission")
@AttributeOverride(name="id", column=@Column(name="auth_permission_id"))
public class Permission extends AuditingEntity implements GrantedAuthority {

    private String systemName;
    private String displayName;
    private String description;
    private String subject;
    private CrudAction action;

    private List<Role> roles = new ArrayList<>();
    private List<UserProfile> users = new ArrayList<>();

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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "action")
    public CrudAction getAction() {
        return action;
    }

    public void setAction(CrudAction action) {
        this.action = action;
    }

    @ManyToMany(mappedBy = "permissions", cascade = {CascadeType.MERGE})
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @ManyToMany(mappedBy = "permissions", cascade = {CascadeType.MERGE})
    public List<UserProfile> getUsers() {
        return users;
    }

    public void setUsers(List<UserProfile> users) {
        this.users = users;
    }

    @Override
    @Transient
    public String getAuthority() {
        return getSystemName();
    }
}
