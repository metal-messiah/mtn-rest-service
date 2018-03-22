package com.mtn.service;

import com.mtn.model.domain.Project;
import com.mtn.model.domain.StoreModel;
import com.mtn.repository.ProjectRepository;
import com.mtn.validators.ProjectValidator;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
	private EntityManagerFactory entityManagerFactory;

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

//	public List<Project> findAllByShoppingCenterId(Interaction interaction) {
//		return getEntityRepository().findAllByInteractionsShoppingCenterIdAndDeletedDateIsNull(interaction);
//	}
//
//	public List<Project> findAllByShoppingCenterSurveyId(Interaction id) {
//		return getEntityRepository().findAllByInteractionsShoppingCenterSurveyIdAndDeletedDateIsNull(id);
//	}
//
//	public List<Project> findAllByShoppingCenterCasingId(Interaction id) {
//		return getEntityRepository().findAllByInteractionsShoppingCenterCasingIdAndDeletedDateIsNull(id);
//	}
//
//	public List<Project> findAllByStoreId(Interaction id) {
//		return getEntityRepository().findAllByInteractionsStoreIdAndDeletedDateIsNull(id);
//	}
//
//	public List<Project> findAllByStoreCasingId(Interaction id) {
//		return getEntityRepository().findAllByInteractionsStoreCasingIdAndDeletedDateIsNull(id);
//	}
//
//	public List<Project> findAllByStoreSurveyId(Interaction id) {
//		return getEntityRepository().findAllByInteractionsStoreSurveyIdAndDeletedDateIsNull(id);
//	}

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
	public Project getUpdatedEntity(Project existing, Project request) {
		existing.setLegacyProjectId(request.getLegacyProjectId());

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
