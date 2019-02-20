package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Site;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SiteMarker {

	private Integer id;
	private Float latitude;
	private Float longitude;
	private Integer assigneeId;
	private Boolean duplicate;
	private Boolean backfilledNonGrocery;
	private List<StoreMarker> stores;

	public SiteMarker(Site site) {
		this.id = site.getId();
		this.latitude = site.getLatitude();
		this.longitude = site.getLongitude();
		if (site.getAssignee() != null) {
			this.assigneeId = site.getAssignee().getId();
		}
		this.duplicate = site.getDuplicate();
		this.backfilledNonGrocery = site.getBackfilledNonGrocery();
		this.stores = site.getStores().stream().map(StoreMarker::new).collect(Collectors.toList());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Integer getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(Integer assigneeId) {
		this.assigneeId = assigneeId;
	}

	public Boolean getDuplicate() {
		return duplicate;
	}

	public void setDuplicate(Boolean duplicate) {
		this.duplicate = duplicate;
	}

	public Boolean getBackfilledNonGrocery() {
		return backfilledNonGrocery;
	}

	public void setBackfilledNonGrocery(Boolean backfilledNonGrocery) {
		this.backfilledNonGrocery = backfilledNonGrocery;
	}

	public List<StoreMarker> getStores() {
		return stores;
	}

	public void setStores(List<StoreMarker> stores) {
		this.stores = stores;
	}
}
