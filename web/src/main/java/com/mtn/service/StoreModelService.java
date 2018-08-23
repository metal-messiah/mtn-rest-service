package com.mtn.service;

import com.mtn.model.domain.Store;
import com.mtn.model.domain.StoreModel;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.view.StoreModelView;
import com.mtn.repository.StoreModelRepository;
import com.mtn.repository.specification.StoreModelSpecifications;
import com.mtn.validators.StoreModelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.data.jpa.domain.Specifications.where;

@Service
public class StoreModelService extends StoreChildService<StoreModel, StoreModelView> {

    @Autowired
    public StoreModelService(SecurityService securityService,
                             StoreModelRepository repository,
                             StoreModelValidator validator) {
        super(securityService, repository, validator, StoreModel::new);
    }

	public List<StoreModel> findAllByProjectId(Integer projectId) {
        return this.repository.findAll(Specifications.where(StoreModelSpecifications.projectIdEquals(projectId)));
    }

	public List<StoreModel> findAllByProjectIdUsingSpecs(Integer projectId) {
        return this.repository.findAll(
                where(StoreModelSpecifications.projectIdEquals(projectId))
                        .and(StoreModelSpecifications.isNotDeleted())
        );
    }

    @Transactional
    public StoreModel addOneToStore(StoreModelView request, Store store) {
        this.validator.validateForInsert(request);

        UserProfile currentUser = this.securityService.getCurrentUser();

        StoreModel model = createNewEntityFromRequest(request);
        model.setCreatedBy(currentUser);
        model.setUpdatedBy(currentUser);

        model.setStore(store);

        return model;
    }

    @Override
    protected void setEntityAttributesFromRequest(StoreModel model, StoreModelView request) {
        model.setMapkey(request.getMapkey());
        model.setCurve(request.getCurve());
        model.setPwta(request.getPwta());
        model.setPower(request.getPower());
        model.setFitAdjustedPower(request.getFitAdjustedPower());
        model.setModelType(request.getModelType());
        model.setModelDate(request.getModelDate());
    }

    @Override
    public void handleAssociationsOnDeletion(StoreModel existing) {
        // Do nothing
    }
}
