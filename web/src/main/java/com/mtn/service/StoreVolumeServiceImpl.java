package com.mtn.service;

import com.mtn.model.domain.StoreVolume;
import com.mtn.repository.StoreVolumeRepository;
import com.mtn.validators.StoreVolumeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mtn.repository.specification.StoreVolumeSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 5/6/2017.
 */
@Service
public class StoreVolumeServiceImpl extends EntityServiceImpl<StoreVolume> implements StoreVolumeService {

    @Autowired
    private StoreVolumeRepository storeVolumeRepository;
    @Autowired
    private StoreVolumeValidator storeVolumeValidator;

    @Override
	public List<StoreVolume> findAllByStoreId(Integer storeId) {
        return getEntityRepository().findAllByStoreId(storeId);
    }

    @Override
	public List<StoreVolume> findAllByStoreIdUsingSpecs(Integer storeId) {
        return getEntityRepository().findAll(
                where(storeIdEquals(storeId))
                        .and(isNotDeleted())
        );
    }

    @Override
    public Page<StoreVolume> findAllUsingSpecs(Pageable page) {
        return getEntityRepository().findAll(where(isNotDeleted()), page);
    }

    @Override
    public StoreVolume findOneUsingSpecs(Integer id) {
        return getEntityRepository().findOne(
                where(idEquals(id))
                        .and(isNotDeleted())
        );
    }

    @Override
    public StoreVolume updateEntityAttributes(StoreVolume existing, StoreVolume request) {
        existing.setVolumeTotal(request.getVolumeTotal());
        existing.setVolumeDate(request.getVolumeDate());
        existing.setVolumeType(request.getVolumeType());
        existing.setVolumeGrocery(request.getVolumeGrocery());
        existing.setVolumePercentGrocery(request.getVolumePercentGrocery());
        existing.setVolumeMeat(request.getVolumeMeat());
        existing.setVolumePercentMeat(request.getVolumePercentMeat());
        existing.setVolumeNonFood(request.getVolumeNonFood());
        existing.setVolumePercentNonFood(request.getVolumePercentNonFood());
        existing.setVolumeOther(request.getVolumeOther());
        existing.setVolumePercentOther(request.getVolumePercentOther());
        existing.setVolumePercentProduce(request.getVolumePercentProduce());
        existing.setVolumeProduce(request.getVolumeProduce());
        existing.setVolumePlusMinus(request.getVolumePlusMinus());
        existing.setVolumeNote(request.getVolumeNote());
        existing.setVolumeConfidence(request.getVolumeConfidence());
        existing.setSource(request.getSource());

        return existing;
    }

    @Override
    public String getEntityName() {
        return "StoreVolume";
    }

    @Override
    public void handleAssociationsOnDeletion(StoreVolume existing) {
        // TODO - Handle Store
    }

    @Override
    public void handleAssociationsOnCreation(StoreVolume request) {
        // TODO - Handle Store
    }

    @Override
    public StoreVolumeRepository getEntityRepository() {
        return storeVolumeRepository;
    }

    @Override
    public StoreVolumeValidator getValidator() {
        return storeVolumeValidator;
    }

}
