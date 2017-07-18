package com.mtn.model.domain.auth;

import com.mtn.model.domain.AuditingEntity;
import com.mtn.model.domain.Identifiable;
import com.mtn.model.domain.UserProfile;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Allen on 5/6/2017.
 */
@Entity
@Table(name = "auth_group")
public class Group extends AuditingEntity implements Identifiable {

    private Integer id;
    private String displayName;
    private String description;

    private Set<UserProfile> members = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_auth_group_id")
    @SequenceGenerator(name = "seq_auth_group_id", sequenceName = "seq_auth_group_id", allocationSize = 1)
    @Column(name = "auth_group_id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @OneToMany(mappedBy = "group", cascade = {CascadeType.MERGE})
    public Set<UserProfile> getMembers() {
        return members;
    }

    public void setMembers(Set<UserProfile> members) {
        this.members = members;
    }
}
