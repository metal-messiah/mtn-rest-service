package com.mtn.validators;

import com.mtn.model.domain.Group;
import com.mtn.model.domain.Identifiable;
import com.mtn.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupValidator extends ValidatingDataService<Group> {

	@Autowired
	private GroupService groupService;

	@Override
	public Identifiable getPotentialDuplicate(Group object) {
		return groupService.findOneByDisplayName(object.getDisplayName());
	}

	@Override
	public GroupService getEntityService() {
		return groupService;
	}

}