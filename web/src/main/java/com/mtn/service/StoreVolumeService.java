package com.mtn.service;

import com.mtn.model.domain.Store;
import com.mtn.model.domain.StoreVolume;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.view.StoreVolumeView;
import com.mtn.repository.StoreVolumeRepository;
import com.mtn.validators.StoreVolumeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class StoreVolumeService extends StoreChildServiceImpl<StoreVolume, StoreVolumeView> {

    @Autowired
    public StoreVolumeService(EntityServiceDependencies services,
                              StoreVolumeRepository repository,
                              StoreVolumeValidator validator) {
        super(services, repository, validator);
    }

    @Transactional
    public StoreVolume addOneToStore(StoreVolumeView request, Store store) {
        this.validator.validateForInsert(request);

        UserProfile currentUser = this.securityService.getCurrentUser();

        StoreVolume volume = createNewEntityFromRequest(request);
        volume.setCreatedBy(currentUser);
        volume.setUpdatedBy(currentUser);

        volume.setStore(store);

        return volume;
    }

    @Override
    protected StoreVolume createNewEntity() {
        return new StoreVolume();
    }

    @Override
    protected void setEntityAttributesFromRequest(StoreVolume volume, StoreVolumeView request) {
        volume.setVolumeTotal(request.getVolumeTotal());
        volume.setVolumeDate(request.getVolumeDate());
        volume.setVolumeType(request.getVolumeType());
        volume.setSource(request.getSource());
        volume.setVolumeGrocery(request.getVolumeGrocery());
        volume.setVolumePercentGrocery(request.getVolumePercentGrocery());
        volume.setVolumeMeat(request.getVolumeMeat());
        volume.setVolumePercentMeat(request.getVolumePercentMeat());
        volume.setVolumeNonFood(request.getVolumeNonFood());
        volume.setVolumePercentNonFood(request.getVolumePercentNonFood());
        volume.setVolumeOther(request.getVolumeOther());
        volume.setVolumePercentOther(request.getVolumePercentOther());
        volume.setVolumePercentProduce(request.getVolumePercentProduce());
        volume.setVolumeProduce(request.getVolumeProduce());
        volume.setVolumePlusMinus(request.getVolumePlusMinus());
        volume.setVolumeNote(request.getVolumeNote());
        volume.setVolumeConfidence(request.getVolumeConfidence());
    }

    @Override
    public void handleAssociationsOnDeletion(StoreVolume existing) {
        existing.setStore(null);
    }
}
