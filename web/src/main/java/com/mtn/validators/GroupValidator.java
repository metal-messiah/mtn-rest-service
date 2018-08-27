package com.mtn.validators;

import com.mtn.model.domain.Group;
import com.mtn.model.view.GroupView;
import com.mtn.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupValidator extends EntityValidator<Group, GroupView> {

	private final GroupRepository groupRepository;

	@Autowired
	public GroupValidator(GroupRepository repository) {
		super(repository);
		this.groupRepository = repository;
	}

	@Override
	protected void validateUpdateInsertBusinessRules(GroupView request) {
		// If another Group (different ID) with the same display name already exists
		List<Group> usersWithEmail = this.groupRepository.findAllByDisplayName(request.getDisplayName().toLowerCase());
		if (usersWithEmail.stream()
				.anyMatch(group -> group.getDisplayName().toLowerCase().equals(request.getDisplayName().toLowerCase()) &&
						!group.getId().equals(request.getId()))) {
			throw new IllegalArgumentException(String.format("Group with display name '%s' already exists!", request.getDisplayName()));
		}
	}

}
