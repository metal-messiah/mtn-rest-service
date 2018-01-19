package com.mtn.service;

import com.mtn.exception.VersionConflictException;
import com.mtn.model.domain.StoreModel;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.simpleView.SimpleStoreModelView;
import com.mtn.repository.StoreModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mtn.repository.specification.StoreModelSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 5/6/2017.
 */
@Service
public class StoreModelService extends ValidatingDataService<StoreModel> {

    @Autowired
    private StoreModelRepository modelRepository;
    @Autowired
    private SecurityService securityService;

    @Transactional
    public StoreModel addOne(StoreModel request) {
        validateForInsert(request);

        UserProfile currentUser = securityService.getCurrentUser();
        request.setCreatedBy(currentUser);
        request.setUpdatedBy(currentUser);

        return modelRepository.save(request);
    }

    @Transactional
    public void deleteOne(Integer id) {
        StoreModel existing = findOneUsingSpecs(id);
        if (existing == null) {
            throw new IllegalArgumentException("No StoreModel found with this id");
        }

        existing.setDeletedBy(securityService.getCurrentUser());
    }

    public List<StoreModel> findAllByProjectId(Integer projectId) {
        return modelRepository.findAllByProjectId(projectId);
    }

    public List<StoreModel> findAllByProjectIdUsingSpecs(Integer projectId) {
        return modelRepository.findAll(
                where(projectIdEquals(projectId))
                        .and(isNotDeleted())
        );
    }

    public List<StoreModel> findAllByStoreId(Integer storeId) {
        return modelRepository.findAllByStoreId(storeId);
    }

    public List<StoreModel> findAllByStoreIdUsingSpecs(Integer storeId) {
        return modelRepository.findAll(
                where(storeIdEquals(storeId))
                        .and(isNotDeleted())
        );
    }

    public StoreModel findOne(Integer id) {
        return modelRepository.findOne(id);
    }

    public StoreModel findOneUsingSpecs(Integer id) {
        return modelRepository.findOne(
                where(idEquals(id))
                        .and(isNotDeleted())
        );
    }

    @Transactional
    public StoreModel updateOne(Integer id, StoreModel request) {
        validateNotNull(request);
        validateForUpdate(request);

        StoreModel existing = findOneUsingSpecs(id);
        if (existing == null) {
            throw new IllegalArgumentException("No StoreModel found with this id");
        }
        if (!request.getVersion().equals(existing.getVersion())) {
            throw new VersionConflictException(new SimpleStoreModelView(existing));
        }

        existing.setMapkey(request.getMapkey());
        existing.setCurve(request.getCurve());
        existing.setPwta(request.getPwta());
        existing.setPower(request.getPower());
        existing.setFitAdjustedPower(request.getFitAdjustedPower());
        existing.setModelType(request.getModelType());
        existing.setModelDate(request.getModelDate());
        existing.setVersion(request.getVersion());
        existing.setLegacyCasingId(request.getLegacyCasingId());
        existing.setUpdatedBy(securityService.getCurrentUser());

        return existing;
    }

    @Override
    public String getEntityName() {
        return "StoreModel";
    }

    @Override
    public void validateForInsert(StoreModel object) {
        super.validateForInsert(object);

        if (object.getStore() == null) {
            throw new IllegalStateException("StoreModel was not mapped to Store before saving!");
        }
        if (object.getVersion() == null) {
            throw new IllegalArgumentException("StoreModel version must be provided");
        }
    }

    @Override
    public void validateBusinessRules(StoreModel object) {
        if (object.getModelType() == null) {
            throw new IllegalArgumentException("StoreModel modelType must be provided");
        } else if (object.getModelDate() == null) {
            throw new IllegalArgumentException("StoreModel modelDate must be provided");
        } else if (object.getProject() == null) {
            throw new IllegalArgumentException("StoreModel project must be provided");
        } else if (object.getStore() == null) {
            throw new IllegalArgumentException("StoreModel store must be provided");
        }
    }

    @Override
    public void validateUnique(StoreModel object) {
        //No unique contraints to enforce
    }
}
