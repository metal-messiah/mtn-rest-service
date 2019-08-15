package com.mtn.model.domain;

import com.vividsolutions.jts.geom.Geometry;

import javax.persistence.*;

@Entity
@Table
@AttributeOverride(name = "id", column = @Column(name = "user_boundary_id"))
public class UserBoundary extends AuditingEntity {

    private String geojson;
    private Geometry boundary;
    private String name;

    public String getGeojson() {
        return geojson;
    }

    public void setGeojson(String geojson) {
        this.geojson = geojson;
    }

    public Geometry getBoundary() {
        return boundary;
    }

    public void setBoundary(Geometry boundary) {
        this.boundary = boundary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
