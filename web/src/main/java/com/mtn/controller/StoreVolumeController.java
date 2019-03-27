package com.mtn.controller;

import com.mtn.model.domain.StoreVolume;
import com.mtn.model.view.StoreVolumeView;
import com.mtn.service.StoreVolumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/store-volume")
public class StoreVolumeController extends CrudController<StoreVolume, StoreVolumeView> {

    @Autowired
    public StoreVolumeController(StoreVolumeService storeVolumeService) {
        super(storeVolumeService, StoreVolumeView::new);
    }
}
