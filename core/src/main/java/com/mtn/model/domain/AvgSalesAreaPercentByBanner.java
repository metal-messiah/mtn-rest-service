package com.mtn.model.domain;

import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Immutable
@Table
public class AvgSalesAreaPercentByBanner {

	@Id
	private Integer bannerId;
	private Float avgSalesPercent;

	public Integer getBannerId() {
		return bannerId;
	}

	public Float getAvgSalesPercent() {
		return avgSalesPercent;
	}
}
