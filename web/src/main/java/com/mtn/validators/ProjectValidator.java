package com.mtn.validators;

import com.mtn.model.domain.Project;
import com.mtn.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectValidator extends ValidatingDataService<Project> {

	@Autowired
	private ProjectService projectService;

	@Override
	public ProjectService getEntityService() {
		return projectService;
	}

	@Override
	public void validateUnique(Project object) {
		Project existing = getEntityService().findOneByProjectName(object.getProjectName());
		if (existing != null && object.getId().equals(existing.getId())) {
			throw new IllegalArgumentException("Project with this name already exists");
		}
	}

}
