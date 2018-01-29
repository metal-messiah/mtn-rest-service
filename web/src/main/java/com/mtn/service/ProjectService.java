package com.mtn.service;

import com.mtn.model.domain.Project;
import com.mtn.model.domain.StoreModel;
import org.springframework.transaction.annotation.Transactional;

public interface ProjectService extends EntityService<Project> {
	StoreModel addOneModelToProject(Integer projectId, StoreModel request);

	Project findOneByProjectName(String projectName);
}
