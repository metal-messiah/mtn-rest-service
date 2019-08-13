package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.UserBoundary;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleUserBoundaryView extends SimpleAuditingEntityView {

	private String geojson;

	public SimpleUserBoundaryView() {
	}

	public SimpleUserBoundaryView(UserBoundary boundary) {
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
