package com.mtn.service;

import com.mtn.model.domain.StoreModel;
import com.mtn.repository.StoreModelRepository;
import com.mtn.repository.specification.StoreModelSpecifications;
import com.mtn.validators.StoreModelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mtn.repository.specification.StoreModelSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 5/6/2017.
 */
@Service
public class StoreModelServiceImpl extends StoreChildServiceImpl<StoreModel> implements StoreModelService {

    @Autowired
    private StoreModelRepository storeModelRepository;
    @Autowired
    private StoreModelValidator storeModelValidator;

    @Override
	public List<StoreModel> findAllByProjectId(Integer projectId) {
        return storeModelRepository.findAll(Specifications.where(StoreModelSpecifications.projectIdEquals(projectId)));
    }

    @Override
	public List<StoreModel> findAllByProjectIdUsingSpecs(Integer projectId) {
        return storeModelRepository.findAll(
                where(projectIdEquals(projectId))
                        .and(isNotDeleted())
        );
    }

    @Override
    public StoreModel updateEntityAttributes(StoreModel existing, StoreModel request) {
        existing.setMapkey(request.getMapkey());
        existing.setCurve(request.getCurve());
        existing.setPwta(request.getPwta());
        existing.setPower(request.getPower());
        existing.setFitAdjustedPower(request.getFitAdjustedPower());
        existing.setModelType(request.getModelType());
        existing.setModelDate(request.getModelDate());

        return existing;
    }

    @Override
    public String getEntityName() {
        return "StoreModel";
    }

    @Override
    public StoreModelValidator getValidator() {
        return storeModelValidator;
    }
}
