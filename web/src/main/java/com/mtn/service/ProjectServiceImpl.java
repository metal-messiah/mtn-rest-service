package com.mtn.service;

import com.mtn.model.domain.Boundary;
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

import java.util.Collections;

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
	@Autowired
	private BoundaryService boundaryService;

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
		return projectRepository.findOne(where(projectNameEquals(projectName)));
	}

	@Override
	public Page<Project> findAllByQueryUsingSpecs(Pageable page, String query, Boolean active, Boolean primaryData) {
		Specifications<Project> spec = where(isNotDeleted());

		if (query != null) {
			spec = spec.and(projectNameIsLike("%" + query + "%"));
		}
		if (active != null && active) {
			spec = spec.and(active());
		}
		if (primaryData != null && primaryData) {
			spec = spec.and(primaryData());
		}

		return projectRepository.findAll(spec, page);
	}

	@Override
	@Transactional
	public Project saveBoundary(Integer projectId, String geoJsonBoundary) {
		Project project = this.findOne(projectId);
		Boundary boundary = project.getBoundary();
		if (boundary != null) {
			boundary.setGeojson(geoJsonBoundary);
			boundaryService.updateOne(boundary.getId(), boundary);
		} else {
			boundary = new Boundary();
			boundary.setGeojson(geoJsonBoundary);
			boundaryService.addOne(boundary);
			project.setBoundary(boundary);
		}
		return project;
	}

	@Override
	@Transactional
	public Project deleteBoundary(Integer projectId) {
		Project project = this.findOne(projectId);
		project.setBoundary(null);
		return project;
	}

	@Override
	public Project updateEntityAttributes(Project existing, Project request) {
		existing.setProjectName(request.getProjectName());
		existing.setMetroArea(request.getMetroArea());
		existing.setClientName(request.getClientName());
		existing.setProjectYear(request.getProjectYear());
		existing.setProjectMonth(request.getProjectMonth());
		existing.setActive(request.getActive());
		existing.setPrimaryData(request.getPrimaryData());
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
	public ProjectValidator getValidator() {
		return projectValidator;
	}
}
