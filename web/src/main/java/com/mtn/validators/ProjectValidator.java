package com.mtn.validators;

import com.mtn.model.domain.Project;
import com.mtn.model.view.ProjectView;
import com.mtn.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectValidator extends EntityValidator<Project, ProjectView> {

	private ProjectRepository projectRepository;

	@Autowired
	public ProjectValidator(ProjectRepository repository) {
		super(repository);
		this.projectRepository = repository;
	}

	@Override
	protected void validateUpdateInsertBusinessRules(ProjectView request) {
		// If another Project (different ID) with the same name already exists
		List<Project> projectsWithName = this.projectRepository.findAllByProjectNameAndDeletedDateIsNull(request.getProjectName().toLowerCase());
		if (projectsWithName.stream()
				.anyMatch(group -> group.getProjectName().toLowerCase().equals(request.getProjectName().toLowerCase()) &&
						!group.getId().equals(request.getId()))) {
			throw new IllegalArgumentException(String.format("Group with display name '%s' already exists!", request.getProjectName()));
		}
	}

}
