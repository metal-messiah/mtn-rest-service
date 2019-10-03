package com.mtn.model.domain;

import com.mtn.model.AvgCounty;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Immutable
@Table
public class AvgByFitInCounty implements AvgCounty {

	@Id
	private String id;
	private String storeFit;
	private String state;
	private String county;
	private Integer volume;
	private Integer areaSales;
	private Integer areaTotal;
	private Float dpsf;

	public String getId() {
		return id;
	}

	public String getStoreFit() {
		return storeFit;
	}

	public String getState() {
		return state;
	}

	public String getCounty() {
		return county;
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
