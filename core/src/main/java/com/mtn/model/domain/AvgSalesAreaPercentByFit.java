package com.mtn.model.domain;

import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Immutable
@Table
public class AvgSalesAreaPercentByFit {

	@Id
	private String storeFit;
	private Float avgSalesPercent;

	public String getStoreFit() {
		return storeFit;
	}

	public Float getAvgSalesPercent() {
		return avgSalesPercent;
	}
}
