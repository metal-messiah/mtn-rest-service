package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.StoreType;
import com.mtn.model.domain.Site;
import com.mtn.model.domain.UserProfile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SiteMarker {

	private Integer id;
	private Float latitude;
	private Float longitude;
	private Integer assigneeId;
	private String assigneeName;
	private Boolean duplicate;
	private Boolean backfilledNonGrocery;
	private LocalDateTime updatedDate;
	private List<StoreMarker> stores;
	private boolean vacant;
	private String address;
	private String city;
	private String state;
	private String postalCode;
	private String quad;
	private String intersectionStreetPrimary;
	private String intersectionStreetSecondary;

	public SiteMarker(Site site) {
		this.id = site.getId();
		this.latitude = site.getLatitude();
		this.longitude = site.getLongitude();
		if (site.getAssignee() != null) {
			UserProfile assignee = site.getAssignee();
			this.assigneeId = assignee.getId();
			this.assigneeName = assignee.getFirstName() + " " + assignee.getLastName();
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

		this.stores = site.getStores().stream().filter(store -> store.getDeletedDate() == null).map(StoreMarker::new)
				.collect(Collectors.toList());

		this.vacant = this.stores.stream().noneMatch(st -> st.getStoreType().equals(StoreType.ACTIVE.name()));

		this.address = site.getAddress1();
		this.city = site.getCity();
		this.state = site.getState();
		this.postalCode = site.getPostalCode();
		this.quad = site.getQuad();
		this.intersectionStreetPrimary = site.getIntersectionStreetPrimary();
		this.intersectionStreetSecondary = site.getIntersectionStreetSecondary();
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

	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
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

	public boolean isVacant() {
		return vacant;
	}

	public void setVacant(boolean vacant) {
		this.vacant = vacant;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getQuad() {
		return quad;
	}

	public void setQuad(String quad) {
		this.quad = quad;
	}

	public String getIntersectionStreetPrimary() {
		return intersectionStreetPrimary;
	}

	public void setIntersectionStreetPrimary(String intersectionStreetPrimary) {
		this.intersectionStreetPrimary = intersectionStreetPrimary;
	}

	public String getIntersectionStreetSecondary() {
		return intersectionStreetSecondary;
	}

	public void setIntersectionStreetSecondary(String intersectionStreetSecondary) {
		this.intersectionStreetSecondary = intersectionStreetSecondary;
	}
}
