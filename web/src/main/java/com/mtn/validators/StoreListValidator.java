package com.mtn.validators;

// import com.mtn.model.domain.Project;
import com.mtn.model.domain.StoreList;
// import com.mtn.model.view.ProjectView;
import com.mtn.model.view.StoreListView;
// import com.mtn.repository.ProjectRepository;
import com.mtn.repository.StoreListRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// import java.util.List;

@Service
public class StoreListValidator extends EntityValidator<StoreList, StoreListView> {

	private StoreListRepository storeListRepository;

	@Autowired
	public StoreListValidator(StoreListRepository repository) {
		super(repository);
		this.storeListRepository = repository;
	}

	// @Override
	// protected void validateUpdateInsertBusinessRules(ProjectView request) {
	// // If another Project (different ID) with the same name already exists
	// List<Project> projectsWithName =
	// this.projectRepository.findAllByProjectNameAndDeletedDateIsNull(request.getProjectName().toLowerCase());
	// if (projectsWithName.stream()
	// .anyMatch(group ->
	// group.getProjectName().toLowerCase().equals(request.getProjectName().toLowerCase())
	// &&
	// !group.getId().equals(request.getId()))) {
	// throw new IllegalArgumentException(String.format("Group with display name
	// '%s' already exists!", request.getProjectName()));
	// }
	// }

}
