package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Boundary;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BoundaryView extends AuditingEntityView {

	private String geojson;
	private String boundaryName;
	private Integer legacyProjectId;

	public BoundaryView() {
	}

	public BoundaryView(Boundary boundary) {
		super(boundary);
		this.geojson = boundary.getGeojson();
		this.boundaryName = boundary.getBoundaryName();
		this.legacyProjectId = boundary.getLegacyProjectId();
	}

	public String getGeojson() {
		return geojson;
	}

	public void setGeojson(String geojson) {
		this.geojson = geojson;
	}

	public String getBoundaryName() {
		return boundaryName;
	}

	public void setBoundaryName(String boundaryName) {
		this.boundaryName = boundaryName;
	}

	public Integer getLegacyProjectId() {
		return legacyProjectId;
	}

	public void setLegacyProjectId(Integer legacyProjectId) {
		this.legacyProjectId = legacyProjectId;
	}
}
