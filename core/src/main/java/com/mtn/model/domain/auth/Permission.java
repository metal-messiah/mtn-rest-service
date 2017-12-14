package com.mtn.model.domain.auth;

import com.mtn.constant.CrudAction;
import com.mtn.model.domain.AuditingEntity;
import com.mtn.model.domain.Identifiable;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 5/6/2017.
 */
@Entity
@Table(name = "auth_permission")
public class Permission implements Identifiable, GrantedAuthority {

    private Integer id;
    private String systemName;
    private String displayName;
    private String description;
    private String subject;
    private CrudAction action;

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

    @Override
    @Transient
    public String getAuthority() {
        return getSystemName();
    }
}
