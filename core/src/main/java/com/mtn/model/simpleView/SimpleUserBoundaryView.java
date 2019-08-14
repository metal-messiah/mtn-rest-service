package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.UserBoundary;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleUserBoundaryView extends SimpleAuditingEntityView {

	private String geojson;
	private String boundaryName;

	public SimpleUserBoundaryView() {
	}

	public SimpleUserBoundaryView(UserBoundary boundary) {
		super(boundary);
		this.geojson = boundary.getGeojson();
		this.boundaryName = boundary.getBoundaryName();
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
}
