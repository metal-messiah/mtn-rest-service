package com.mtn.service;

import com.mtn.model.domain.Boundary;
import com.mtn.model.domain.Project;
import com.mtn.model.domain.StoreCasing;
import com.mtn.model.view.ProjectView;
import com.mtn.repository.ProjectRepository;
import com.mtn.repository.specification.ProjectSpecifications;
import com.mtn.validators.ProjectValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.data.jpa.domain.Specifications.where;

@Service
public class ProjectService extends EntityService<Project, ProjectView> {

	@Autowired
	public ProjectService(SecurityService securityService,
						  ProjectRepository repository,
						  ProjectValidator validator) {
		super(securityService, repository, validator, Project::new);
	}

	public Page<Project> findAllByQueryUsingSpecs(Pageable page, String query, Boolean active, Boolean primaryData) {
		Specifications<Project> spec = where(ProjectSpecifications.isNotDeleted());

		if (query != null) {
			spec = spec.and(ProjectSpecifications.projectNameIsLike("%" + query + "%"));
		}
		if (active != null && active) {
			spec = spec.and(ProjectSpecifications.active());
		}
		if (primaryData != null && primaryData) {
			spec = spec.and(ProjectSpecifications.primaryData());
		}

		return this.repository.findAll(spec, page);
	}

	@Transactional
	public Project addStoreCasingToProject(Integer projectId, StoreCasing storeCasing) {
		Project project = this.findOne(projectId);
		project.getStoreCasings().add(storeCasing);
		return project;
	}

	@Transactional
	public Project setProjectBoundary(Integer projectId, Boundary boundary) {
		Project project = this.findOne(projectId);
		project.setBoundary(boundary);
		return project;
	}

	@Transactional
	public Project removeProjectBoundary(Integer projectId) {
		Project project = this.findOne(projectId);
		project.setBoundary(null);
		return project;
	}

	@Override
	protected void setEntityAttributesFromRequest(Project entity, ProjectView request) {
		entity.setProjectName(request.getProjectName());
		entity.setMetroArea(request.getMetroArea());
		entity.setClientName(request.getClientName());
		entity.setProjectYear(request.getProjectYear());
		entity.setProjectMonth(request.getProjectMonth());
		entity.setActive(request.getActive());
		entity.setPrimaryData(request.getPrimaryData());
		entity.setDateStarted(request.getDateStarted());
		entity.setDateCompleted(request.getDateCompleted());
		entity.setSource(request.getSource());
	}

	@Override
	public void handleAssociationsOnDeletion(Project project) {
		project.getStoreCasings().forEach(casing -> casing.getProjects().remove(project));
		project.getShoppingCenterCasings().forEach(casing -> casing.getProjects().remove(project));
	}
}
