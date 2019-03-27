package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Site;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SiteMarker {

	private Integer id;
	private Float latitude;
	private Float longitude;
	private String address1;
	private String city;
	private String state;
	private Integer assigneeId;
	private Boolean duplicate;
	private Boolean backfilledNonGrocery;
	private LocalDateTime updatedDate;
	private List<StoreMarker> stores;

	public SiteMarker(Site site) {
		this.id = site.getId();
		this.latitude = site.getLatitude();
		this.longitude = site.getLongitude();
		this.address1 = site.getAddress1();
		this.city = site.getCity();
		this.state = site.getState();
		if (site.getAssignee() != null) {
			this.assigneeId = site.getAssignee().getId();
		}
		this.duplicate = site.getDuplicate();
		this.backfilledNonGrocery = site.getBackfilledNonGrocery();

		// Get the maximum updated date
		this.updatedDate = site.getUpdatedDate();
		site.getStores().forEach(store -> {
			if (store.getUpdatedDate().isAfter(this.updatedDate)) {
				this.updatedDate = store.getUpdatedDate();
			}
		});

		this.stores = site.getStores().stream()
				.filter(store -> store.getDeletedDate() == null)
				.map(StoreMarker::new).collect(Collectors.toList());
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

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}
}
