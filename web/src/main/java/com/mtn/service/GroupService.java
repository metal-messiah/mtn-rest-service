package com.mtn.service;

import com.mtn.model.domain.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GroupService extends EntityService<Group> {
	Group addOneMemberToGroup(Integer groupId, Integer userId);

	Page<Group> findAllByNameUsingSpecs(String name, Pageable page);

	Group findOneByDisplayName(String displayName);

	Group removeOneMemberFromGroup(Integer groupId, Integer userId);
}
