package com.mtn.model.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Allen on 5/6/2017.
 */
@Entity
@Table(name = "auth_group")
@AttributeOverride(name="id", column=@Column(name="auth_group_id"))
public class Group extends AuditingEntity {

    private String displayName;
    private String description;

    private Set<UserProfile> members = new HashSet<>();

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
