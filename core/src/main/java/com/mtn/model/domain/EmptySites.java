package com.mtn.model.domain;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Immutable
@Table
public class EmptySites {

	@Id
	private Integer siteId;
	private Float latitude;
	private Float longitude;
	private String address;
	private String city;
	private String county;
	private String state;
	private String postalCode;
	private String intersectionType;
	private String quad;
	private String primaryIntersectionStreet;
	private String secondaryIntersectionStreet;
	private Integer cbsaId;
	private Integer plannedStoreId;
	private String plannedStoreName;
	private LocalDate plannedStoreOpeningDate;
	private Integer plannedStoreTotalArea;

	public Integer getSiteId() {
		return siteId;
	}

	public Float getLatitude() {
		return latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public String getCounty() {
		return county;
	}

	public String getState() {
		return state;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getIntersectionType() {
		return intersectionType;
	}

	public String getQuad() {
		return quad;
	}

	public String getPrimaryIntersectionStreet() {
		return primaryIntersectionStreet;
	}

	public String getSecondaryIntersectionStreet() {
		return secondaryIntersectionStreet;
	}

	public Integer getCbsaId() {
		return cbsaId;
	}

	public Integer getPlannedStoreId() {
		return plannedStoreId;
	}

	public String getPlannedStoreName() {
		return plannedStoreName;
	}

	public LocalDate getPlannedStoreOpeningDate() {
		return plannedStoreOpeningDate;
	}

	public Integer getPlannedStoreTotalArea() {
		return plannedStoreTotalArea;
	}
}
