package com.mtn.service;

import com.mtn.model.domain.Project;
import com.mtn.model.domain.StoreModel;
import com.mtn.repository.ProjectRepository;
import com.mtn.validators.ProjectValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;

import static com.mtn.repository.specification.ProjectSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 5/6/2017.
 */
@Service
public class ProjectServiceImpl extends EntityServiceImpl<Project> implements ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private StoreModelService modelService;
	@Autowired
	private ProjectValidator projectValidator;

	@Override
	@Transactional
	public StoreModel addOneModelToProject(Integer projectId, StoreModel request) {
		Project existing = findOneUsingSpecs(projectId);
		getValidator().validateNotNull(existing);

		request.setProject(existing);
		existing.setUpdatedBy(securityService.getCurrentUser());

		return modelService.addOne(request);
	}

	@Override
	public Project findOneByProjectName(String projectName) {
		return getEntityRepository().findOneByProjectName(projectName);
	}

	@Override
	public Page<Project> findAllUsingSpecs(Pageable page, String query, Boolean active, Boolean primaryData) {
		Specifications<Project> spec = where(isNotDeleted());

		if (query != null) {
			spec = spec.and(projectNameIsLike("%" + query + "%"));
		}
		if (active != null && active) {
			spec = spec.and(isActive());
		}
		if (primaryData != null && primaryData) {
			spec = spec.and(isPrimaryData());
		}

		return getEntityRepository().findAll(spec, page);
	}

	@Override
	public Page<Project> findAllUsingSpecs(Pageable page) {
		return getEntityRepository().findAll(where(isNotDeleted()), page);
	}

	@Override
	public Project findOneUsingSpecs(Integer id) {
		return getEntityRepository().findOne(
				where(idEquals(id))
						.and(isNotDeleted())
		);
	}

	@Override
	public Project updateEntityAttributes(Project existing, Project request) {
		existing.setProjectName(request.getProjectName());
		existing.setMetroArea(request.getMetroArea());
		existing.setClientName(request.getClientName());
		existing.setProjectYear(request.getProjectYear());
		existing.setProjectMonth(request.getProjectMonth());
		existing.setIsActive(request.getIsActive());
		existing.setIsPrimaryData(request.getIsPrimaryData());
		existing.setDateStarted(request.getDateStarted());
		existing.setDateCompleted(request.getDateCompleted());
		existing.setSource(request.getSource());

		return existing;
	}

	@Override
	public String getEntityName() {
		return "Project";
	}

	@Override
	public void handleAssociationsOnDeletion(Project existing) {
		// TODO Determine what to do with children of project on deletion
	}

	@Override
	public void handleAssociationsOnCreation(Project request) {
		// TODO When project is saved, are children handled with it?
	}

	@Override
	public ProjectRepository getEntityRepository() {
		return projectRepository;
	}

	@Override
	public ProjectValidator getValidator() {
		return projectValidator;
	}
}
