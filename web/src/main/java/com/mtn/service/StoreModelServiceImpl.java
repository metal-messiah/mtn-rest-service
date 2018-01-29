package com.mtn.service;

import com.mtn.model.domain.StoreModel;
import com.mtn.repository.StoreModelRepository;
import com.mtn.validators.StoreModelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mtn.repository.specification.StoreModelSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 5/6/2017.
 */
@Service
public class StoreModelServiceImpl extends EntityServiceImpl<StoreModel> implements StoreModelService {

    @Autowired
    private StoreModelRepository storeModelRepository;
    @Autowired
    private StoreModelValidator storeModelValidator;

    @Override
	public List<StoreModel> findAllByProjectId(Integer projectId) {
        return getEntityRepository().findAllByProjectId(projectId);
    }

    @Override
	public List<StoreModel> findAllByProjectIdUsingSpecs(Integer projectId) {
        return getEntityRepository().findAll(
                where(projectIdEquals(projectId))
                        .and(isNotDeleted())
        );
    }

    @Override
	public List<StoreModel> findAllByStoreId(Integer storeId) {
        return getEntityRepository().findAllByStoreId(storeId);
    }

    @Override
	public List<StoreModel> findAllByStoreIdUsingSpecs(Integer storeId) {
        return getEntityRepository().findAll(
                where(storeIdEquals(storeId))
                        .and(isNotDeleted())
        );
    }

    @Override
    public Page<StoreModel> findAllUsingSpecs(Pageable page) {
        return getEntityRepository().findAll(where(isNotDeleted()), page);
    }

    @Override
    public StoreModel findOneUsingSpecs(Integer id) {
        return getEntityRepository().findOne(
                where(idEquals(id))
                        .and(isNotDeleted())
        );
    }

    @Override
    public StoreModel getUpdatedEntity(StoreModel existing, StoreModel request) {
        existing.setMapkey(request.getMapkey());
        existing.setCurve(request.getCurve());
        existing.setPwta(request.getPwta());
        existing.setPower(request.getPower());
        existing.setFitAdjustedPower(request.getFitAdjustedPower());
        existing.setModelType(request.getModelType());
        existing.setModelDate(request.getModelDate());
        existing.setLegacyCasingId(request.getLegacyCasingId());
        existing.setUpdatedBy(securityService.getCurrentUser());

        return existing;
    }

    @Override
    public String getEntityName() {
        return "StoreModel";
    }

    @Override
    public void handleAssociationsOnDeletion(StoreModel existing) {
        // TODO - handle associations
    }

    @Override
    public void handleAssociationsOnCreation(StoreModel request) {
        // TODO - handle associations
    }

    @Override
    public StoreModelRepository getEntityRepository() {
        return storeModelRepository;
    }

    @Override
    public StoreModelValidator getValidator() {
        return storeModelValidator;
    }
}
