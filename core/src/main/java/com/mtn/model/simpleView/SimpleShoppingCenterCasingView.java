package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ShoppingCenterCasing;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleShoppingCenterCasingView extends SimpleAuditingEntityView {

	private LocalDateTime casingDate;
	private String note;

	public SimpleShoppingCenterCasingView() {
	}

	public SimpleShoppingCenterCasingView(ShoppingCenterCasing casing) {
		super(casing);
		this.casingDate = casing.getCasingDate();
		this.note = casing.getNote();
	}

	public LocalDateTime getCasingDate() {
		return casingDate;
	}

	public void setCasingDate(LocalDateTime casingDate) {
		this.casingDate = casingDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
