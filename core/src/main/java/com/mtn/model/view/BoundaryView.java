package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Boundary;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BoundaryView extends AuditingEntityView {

	private String geojson;
	private Integer legacyProjectId;

	public BoundaryView(Boundary boundary) {
		super(boundary);
		this.geojson = boundary.getGeojson();
		this.legacyProjectId = boundary.getLegacyProjectId();
	}

	public Integer getId() {
		return id;
	}

	public String getGeojson() {
		return geojson;
	}

	public Integer getLegacyProjectId() {
		return legacyProjectId;
	}

}
