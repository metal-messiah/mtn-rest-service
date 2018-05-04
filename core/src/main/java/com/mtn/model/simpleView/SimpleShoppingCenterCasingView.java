package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ShoppingCenterCasing;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleShoppingCenterCasingView {

	private Integer id;
	private LocalDateTime casingDate;
	private String note;

	private SimpleShoppingCenterSurveyView shoppingCenterSurvey;
	private List<SimpleProjectView> projects = new ArrayList<>();

	public SimpleShoppingCenterCasingView(ShoppingCenterCasing casing) {
		this.id = casing.getId();
		this.casingDate = casing.getCasingDate();
		this.note = casing.getNote();
		if (casing.getShoppingCenterSurvey() != null) {
			this.shoppingCenterSurvey = new SimpleShoppingCenterSurveyView(casing.getShoppingCenterSurvey());
		}
		if (casing.getProjects() != null) {
			this.projects = casing.getProjects().stream()
					.filter(project -> project.getDeletedDate() == null)
					.map(SimpleProjectView::new)
					.collect(Collectors.toList());
		}

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public SimpleShoppingCenterSurveyView getShoppingCenterSurvey() {
		return shoppingCenterSurvey;
	}

	public void setShoppingCenterSurvey(SimpleShoppingCenterSurveyView shoppingCenterSurvey) {
		this.shoppingCenterSurvey = shoppingCenterSurvey;
	}

	public List<SimpleProjectView> getProjects() {
		return projects;
	}

	public void setProjects(List<SimpleProjectView> projects) {
		this.projects = projects;
	}
}
