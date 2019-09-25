package com.mtn.model.simpleView;

import javax.persistence.*;

@Entity
public class SimpleUserBoundaryView {

	@Id
	private Integer id;
	private Integer boundaryId;
	private String boundaryName;

	public SimpleUserBoundaryView() {
	}

	public SimpleUserBoundaryView(Integer id, Integer boundaryId, String boundaryName) {
		this.id = id;
		this.boundaryId = boundaryId;
		this.boundaryName = boundaryName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBoundaryId() {
		return boundaryId;
	}

	public void setBoundaryId(Integer boundaryId) {
		this.boundaryId = boundaryId;
	}

	public String getBoundaryName() {
		return boundaryName;
	}

	public void setBoundaryName(String boundaryName) {
		this.boundaryName = boundaryName;
	}
}
