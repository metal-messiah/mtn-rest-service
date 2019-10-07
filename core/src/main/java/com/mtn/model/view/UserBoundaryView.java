package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.UserBoundary;
import com.mtn.model.simpleView.SimpleBoundaryView;
import com.mtn.model.simpleView.SimpleUserProfileView;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserBoundaryView extends AuditingEntityView {

	private String boundaryName;
	private SimpleUserProfileView user;
	private SimpleBoundaryView boundary;

	public UserBoundaryView() {
	}

	public UserBoundaryView(UserBoundary userBoundary) {
		super(userBoundary);
		this.boundaryName = userBoundary.getBoundaryName();
		this.user = new SimpleUserProfileView(userBoundary.getUser());
		this.boundary = new SimpleBoundaryView(userBoundary.getBoundary());
	}

	public String getBoundaryName() {
		return boundaryName;
	}

	public void setBoundaryName(String boundaryName) {
		this.boundaryName = boundaryName;
	}

	public SimpleUserProfileView getUser() {
		return user;
	}

	public void setUser(SimpleUserProfileView user) {
		this.user = user;
	}

	public SimpleBoundaryView getBoundary() {
		return boundary;
	}

	public void setBoundary(SimpleBoundaryView boundary) {
		this.boundary = boundary;
	}
}
