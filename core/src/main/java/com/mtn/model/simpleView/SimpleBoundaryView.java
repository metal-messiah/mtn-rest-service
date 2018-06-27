package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Boundary;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleBoundaryView {

	private Integer id;
	private String geojson;

	public SimpleBoundaryView(Boundary boundary) {
		this.id = boundary.getId();
		this.geojson = boundary.getGeojson();
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
}
