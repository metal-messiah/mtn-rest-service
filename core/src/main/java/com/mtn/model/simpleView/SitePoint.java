package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Site;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SitePoint {

	private Integer id;
	private Float lat;
	private Float lng;
	private Integer assigneeId;

	public SitePoint() {
	}

	public SitePoint(Site site) {
		this.id = site.getId();
		this.lat = site.getLatitude();
		this.lng = site.getLongitude();
		if (site.getAssignee() != null) {
			this.assigneeId = site.getAssignee().getId();
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getLat() {
		return lat;
	}

	public void setLat(Float lat) {
		this.lat = lat;
	}

	public Float getLng() {
		return lng;
	}

	public void setLng(Float lng) {
		this.lng = lng;
	}

	public Integer getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(Integer assigneeId) {
		this.assigneeId = assigneeId;
	}
}
