package com.mtn.model.domain;


import com.vividsolutions.jts.geom.Geometry;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@SqlResultSetMapping(
        name = "SimpleUserBoundaryView",
        classes = {
                @ConstructorResult(
                        targetClass = com.mtn.model.simpleView.SimpleUserBoundaryView.class,
                        columns = {
                                @ColumnResult(name = "user_boundary_id", type = Integer.class),
                                @ColumnResult(name = "boundary_id", type = Integer.class),
                                @ColumnResult(name = "boundary_name"),
                        }
                )
        }
)


@NamedNativeQuery(
        name = "getAllUserBoundariesForUser",
        query = "SELECT ub.user_boundary_id, ub.boundary_id, ub.boundary_name " +
                "from user_boundary ub " +
                "where ub.user_profile_id = :userProfileId " +
                "and ub.deleted_date is null",
        resultSetMapping = "SimpleUserBoundaryView",
        resultClass = com.mtn.model.simpleView.SimpleUserBoundaryView.class
)

@Entity
@Table
@AttributeOverride(name = "id", column = @Column(name = "boundary_id"))
public class Boundary extends AuditingEntity {

    private String geojson;
    private Geometry boundary;
    private Integer legacyProjectId;

    private List<Project> projects = new ArrayList<>();

    public String getGeojson() {
        return geojson;
    }

    public void setGeojson(String geojson) {
        this.geojson = geojson;
    }

    public Integer getLegacyProjectId() {
        return legacyProjectId;
    }

    public void setLegacyProjectId(Integer legacyProjectId) {
        this.legacyProjectId = legacyProjectId;
    }

    @OneToMany(mappedBy = "boundary")
    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public Geometry getBoundary() {
        return boundary;
    }

    public void setBoundary(Geometry boundary) {
        this.boundary = boundary;
    }
}
