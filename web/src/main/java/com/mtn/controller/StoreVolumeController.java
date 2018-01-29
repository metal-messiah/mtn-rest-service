package com.mtn.controller;

import com.mtn.model.domain.StoreVolume;
import com.mtn.model.simpleView.SimpleStoreVolumeView;
import com.mtn.service.StoreVolumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/store-volume")
public class StoreVolumeController extends CrudControllerImpl<StoreVolume> {

    @Autowired
    private StoreVolumeService storeVolumeService;

    @Override
    public StoreVolumeService getEntityService() {
        return storeVolumeService;
    }

    @Override
    public SimpleStoreVolumeView getViewFromModel(Object model) {
        return new SimpleStoreVolumeView((StoreVolume) model);
    }
}
