package com.mtn.model.domain;

import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Immutable
@Table
public class AvgByBanner {

	@Id
	private Integer bannerId;
	private Integer volume;
	private Integer areaSales;
	private Integer areaTotal;
	private Float dpsf;

	public Integer getBannerId() {
		return bannerId;
	}

	public Integer getVolume() {
		return volume;
	}

	public Integer getAreaSales() {
		return areaSales;
	}

	public Integer getAreaTotal() {
		return areaTotal;
	}

	public Float getDpsf() {
		return dpsf;
	}
}
