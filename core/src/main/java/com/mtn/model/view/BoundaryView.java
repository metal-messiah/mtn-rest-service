package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Boundary;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BoundaryView extends AuditingEntityView {

	private Integer id;
	private String geojson;
	private Integer legacyProjectId;

	public BoundaryView(Boundary boundary) {
		super(boundary);
		this.id = boundary.getId();
		this.geojson = boundary.getGeojson();
		this.legacyProjectId = boundary.getLegacyProjectId();
	}

	public Integer getId() {
		return id;
	}

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
}
