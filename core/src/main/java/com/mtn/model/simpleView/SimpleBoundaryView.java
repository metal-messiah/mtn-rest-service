package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Boundary;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleBoundaryView extends SimpleAuditingEntityView {

	private String geojson;
	private Integer legacyProjectId;

	public SimpleBoundaryView() {
	}

	public SimpleBoundaryView(Boundary boundary) {
		super(boundary);
		this.geojson = boundary.getGeojson();
		this.legacyProjectId = boundary.getLegacyProjectId();
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
