package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Boundary;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleBoundaryView extends SimpleAuditingEntityView {

	private String geojson;

	public SimpleBoundaryView() {
	}

	public SimpleBoundaryView(Boundary boundary) {
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
