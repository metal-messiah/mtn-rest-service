package com.mtn.model.domain;

import com.vividsolutions.jts.geom.Geometry;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@AttributeOverride(name = "id", column = @Column(name = "boundary_id"))
public class UserBoundary extends AuditingEntity {

    private String geojson;
    private Geometry boundary;

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
}
