package com.mtn.model.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@AttributeOverride(name = "id", column = @Column(name = "user_boundary_id"))
public class UserBoundary extends AuditingEntity {

    private String boundaryName;

    private UserProfile user;
    private Boundary boundary;

    public String getBoundaryName() {
        return boundaryName;
    }

    public void setBoundaryName(String boundaryName) {
        this.boundaryName = boundaryName;
    }

    @ManyToOne
    @JoinColumn(name = "user_profile_id")
    public UserProfile getUser() {
        return user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }

    // TODO figure out how to delete orphans
    @ManyToOne
    @JoinColumn(name = "boundary_id")
    public Boundary getBoundary() {
        return boundary;
    }

    public void setBoundary(Boundary boundary) {
        this.boundary = boundary;
    }
}
