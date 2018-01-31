package com.mtn.validators;

import com.mtn.model.domain.Identifiable;
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
	public Identifiable getPotentialDuplicate(Project object) {
		return getEntityService().findOneByProjectName(object.getProjectName());
	}

}
