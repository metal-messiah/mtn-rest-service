package com.mtn.model.domain;

import com.vividsolutions.jts.geom.Point;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class BlockGroupPopulationCentroid {

	@Id
	private String fips;
	private Float latitude;
	private Float longitude;
	private Point location;
	private Integer population;
	private Float pcw;

	public String getFips() {
		return fips;
	}

	public void setFips(String fips) {
		this.fips = fips;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public Integer getPopulation() {
		return population;
	}

	public void setPopulation(Integer population) {
		this.population = population;
	}

	public Float getPcw() {
		return pcw;
	}

	public void setPcw(Float pcw) {
		this.pcw = pcw;
	}
}
