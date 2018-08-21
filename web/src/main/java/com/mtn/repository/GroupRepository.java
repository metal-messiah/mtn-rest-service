package com.mtn.repository;

import com.mtn.model.domain.Group;

import java.util.List;

public interface GroupRepository extends EntityRepository<Group> {

	List<Group> findAllByDisplayNameAndDeletedDateIsNull(String displayName);

}
