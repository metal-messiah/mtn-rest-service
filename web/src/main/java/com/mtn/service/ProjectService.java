package com.mtn.service;

import com.mtn.model.domain.Project;
import com.mtn.model.domain.StoreModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService extends EntityService<Project> {
	StoreModel addOneModelToProject(Integer projectId, StoreModel request);

	Project findOneByProjectName(String projectName);

	Page<Project> findAllUsingSpecs(Pageable page, String query, Boolean active, Boolean primaryData);
}
