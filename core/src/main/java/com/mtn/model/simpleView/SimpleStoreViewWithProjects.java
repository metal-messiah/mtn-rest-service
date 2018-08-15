package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Store;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleStoreViewWithProjects extends SimpleStoreView {

	private List<Integer> projectIds;

	public SimpleStoreViewWithProjects(Store store) {
		super(store);
		this.projectIds = new ArrayList<>();
		if (store.getCasings() != null) {
			store.getCasings().forEach(casing -> {
				if (casing.getProjects() != null) {
					casing.getProjects().forEach(p -> this.projectIds.add(p.getId()));
				}
			});
		}
		this.projectIds = this.getProjectIds().stream().distinct().collect(Collectors.toList());
	}

	public List<Integer> getProjectIds() {
		return projectIds;
	}
}
