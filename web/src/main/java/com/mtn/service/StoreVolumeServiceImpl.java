package com.mtn.service;

import com.mtn.model.domain.StoreVolume;
import com.mtn.validators.StoreVolumeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by Allen on 5/6/2017.
 */
@Service
public class StoreVolumeServiceImpl extends StoreChildServiceImpl<StoreVolume> implements StoreVolumeService {

    @Autowired
    private StoreVolumeValidator storeVolumeValidator;

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
    public StoreVolumeValidator getValidator() {
        return storeVolumeValidator;
    }

}
