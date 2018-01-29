package com.mtn.validators;

import com.mtn.model.domain.Group;
import com.mtn.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupValidator extends ValidatingDataService<Group> {

	@Autowired
	private GroupService groupService;

	@Override
	public void validateUnique(Group object) {
		Group existing = groupService.findOneByDisplayName(object.getDisplayName());
		if (existing != null) {
			throw new IllegalArgumentException("Group with this displayName already exists");
		}
	}

	@Override
	public GroupService getEntityService() {
		return groupService;
	}

}
