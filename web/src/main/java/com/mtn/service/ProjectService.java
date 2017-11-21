package com.mtn.service;

import com.mtn.exception.VersionConflictException;
import com.mtn.model.domain.Project;
import com.mtn.model.domain.StoreModel;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.view.SimpleProjectView;
import com.mtn.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mtn.repository.specification.ProjectSpecifications.idEquals;
import static com.mtn.repository.specification.ProjectSpecifications.isNotDeleted;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 5/6/2017.
 */
@Service
public class ProjectService extends ValidatingDataService<Project> {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private StoreModelService modelService;
    @Autowired
    private SecurityService securityService;

    @Transactional
    public Project addOne(Project request) {
        validateForInsert(request);

        UserProfile currentUser = securityService.getCurrentUser();
        request.setCreatedBy(currentUser);
        request.setUpdatedBy(currentUser);

        return projectRepository.save(request);
    }

    @Transactional
    public StoreModel addOneModelToProject(Integer projectId, StoreModel request) {
        Project existing = findOneUsingSpecs(projectId);
        validateNotNull(existing);

        request.setProject(existing);
        existing.setUpdatedBy(securityService.getCurrentUser());

        return modelService.addOne(request);
    }

    @Transactional
    public void deleteOne(Integer id) {
        Project existing = findOneUsingSpecs(id);
        if (existing == null) {
            throw new IllegalArgumentException("No Project found with this id");
        }

        existing.setDeletedBy(securityService.getCurrentUser());
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public List<Project> findAllByShoppingCenterId(Integer id) {
        return projectRepository.findAllByInteractionsShoppingCenterIdAndDeletedDateIsNull(id);
    }

    public List<Project> findAllByShoppingCenterSurveyId(Integer id) {
        return projectRepository.findAllByInteractionsShoppingCenterSurveyIdAndDeletedDateIsNull(id);
    }

    public List<Project> findAllByShoppingCenterCasingId(Integer id) {
        return projectRepository.findAllByInteractionsShoppingCenterCasingIdAndDeletedDateIsNull(id);
    }

    public List<Project> findAllByStoreId(Integer id) {
        return projectRepository.findAllByInteractionsStoreIdAndDeletedDateIsNull(id);
    }

    public List<Project> findAllByStoreCasingId(Integer id) {
        return projectRepository.findAllByInteractionsStoreCasingIdAndDeletedDateIsNull(id);
    }

    public List<Project> findAllByStoreSurveyId(Integer id) {
        return projectRepository.findAllByInteractionsStoreSurveyIdAndDeletedDateIsNull(id);
    }

    public List<Project> findAllUsingSpecs() {
        return projectRepository.findAll(
                where(isNotDeleted())
        );
    }

    public Project findOne(Integer id) {
        return projectRepository.findOne(id);
    }

    public Project findOneUsingSpecs(Integer id) {
        return projectRepository.findOne(
                where(idEquals(id))
                        .and(isNotDeleted())
        );
    }

    @Transactional
    public Project updateOne(Integer id, Project request) {
        validateNotNull(request);
        validateForUpdate(request);

        Project existing = findOneUsingSpecs(id);
        if (existing == null) {
            throw new IllegalArgumentException("No Project found with this id");
        }
        if (!request.getVersion().equals(existing.getVersion())) {
            throw new VersionConflictException(new SimpleProjectView(existing));
        }


        existing.setVersion(request.getVersion());
        existing.setLegacyProjectId(request.getLegacyProjectId());
        existing.setUpdatedBy(securityService.getCurrentUser());

        return existing;
    }

    @Override
    public String getEntityName() {
        return "Project";
    }

    @Override
    public void validateForInsert(Project object) {
        super.validateForInsert(object);

        if (object.getVersion() == null) {
            throw new IllegalArgumentException("Project version must be provided");
        }
    }

    @Override
    public void validateBusinessRules(Project object) {
        //No business requirements at this time
    }

    @Override
    public void validateDoesNotExist(Project object) {
        //No unique contraints to enforce
    }
}
