package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.UserBoundary;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserBoundaryView extends AuditingEntityView {

	private String geojson;

	public UserBoundaryView() {
	}

	public UserBoundaryView(UserBoundary boundary) {
		super(boundary);
		this.geojson = boundary.getGeojson();
	}

	public String getGeojson() {
		return geojson;
	}

	public void setGeojson(String geojson) {
		this.geojson = geojson;
	}
}
