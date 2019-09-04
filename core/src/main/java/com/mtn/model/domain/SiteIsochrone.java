package com.mtn.model.domain;

import com.vividsolutions.jts.geom.Geometry;

import javax.persistence.*;

@Entity
@Table
public class SiteIsochrone {

    @Id
	@GeneratedValue
    private Integer id;

	@ManyToOne
	@JoinColumn(name = "site_id")
    private Site site;

    private Integer driveMinutes;
    private String geojson;
    private Geometry boundary;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Integer getDriveMinutes() {
        return driveMinutes;
    }

    public void setDriveMinutes(Integer driveMinutes) {
        this.driveMinutes = driveMinutes;
    }

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
