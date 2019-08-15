package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.UserBoundary;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleUserBoundaryView extends SimpleAuditingEntityView {

	private String geojson;
	private String name;

	public SimpleUserBoundaryView() {
	}

	public SimpleUserBoundaryView(UserBoundary boundary) {
		super(boundary);
		this.geojson = boundary.getGeojson();
		this.name = boundary.getName();
	}

	public String getGeojson() {
		return geojson;
	}

	public void setGeojson(String geojson) {
		this.geojson = geojson;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
