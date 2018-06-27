package com.mtn.model.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Boundary extends AuditingEntity implements Identifiable {

    private Integer id;
    private String geojson;
    private Integer legacyProjectId;

    private List<Project> projects = new ArrayList<>();

    @Id
    @GeneratedValue
    @Column(name = "boundary_id")
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

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
}
