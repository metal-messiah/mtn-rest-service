package com.mtn.repository;

import com.mtn.model.domain.Project;

import java.util.List;

public interface ProjectRepository extends EntityRepository<Project> {

	List<Project> findAllByProjectNameAndDeletedDateIsNull(String projectName);
}
