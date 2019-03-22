package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Store;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreMarker {

	private Integer id;
	private String storeName;
	private Boolean floating;
	private String storeType;
	private LocalDateTime validatedDate;
	private LocalDateTime createdDate;
	private String logoFileName;
	private Integer bannerId;

	StoreMarker(Store store) {
		this.id = store.getId();
		this.storeName = store.getStoreName();
		this.floating = store.getFloating();
		this.storeType = store.getStoreType().toString();
		this.validatedDate = store.getValidatedDate();
		this.createdDate = store.getCreatedDate();
		if (store.getBanner() != null) {
			this.logoFileName = store.getBanner().getLogoFileName();
			this.bannerId = store.getBanner().getId();
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Boolean getFloat() {
		return floating;
	}

	public void setFloat(Boolean aFloat) {
		floating = aFloat;
	}

	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	public LocalDateTime getValidatedDate() {
		return validatedDate;
	}

	public void setValidatedDate(LocalDateTime validatedDate) {
		this.validatedDate = validatedDate;
	}

	public String getLogoFileName() {
		return logoFileName;
	}

	public void setLogoFileName(String logoFileName) {
		this.logoFileName = logoFileName;
	}

	public Integer getBannerId() {
		return bannerId;
	}

	public void setBannerId(Integer bannerId) {
		this.bannerId = bannerId;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
}
