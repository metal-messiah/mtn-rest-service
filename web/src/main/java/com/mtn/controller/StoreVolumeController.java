package com.mtn.controller;

import com.mtn.constant.PermissionType;
import com.mtn.model.domain.StoreVolume;
import com.mtn.model.view.SimpleStoreVolumeView;
import com.mtn.service.SecurityService;
import com.mtn.service.StoreVolumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/store-volume")
public class StoreVolumeController {

    @Autowired
    private StoreVolumeService storeVolumeService;
    @Autowired
    private SecurityService securityService;

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOne(@PathVariable("id") Integer id) {
        securityService.checkPermission(PermissionType.STORE_VOLUMES_DELETE);

        storeVolumeService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        securityService.checkPermission(PermissionType.STORE_VOLUMES_READ);

        StoreVolume domainModel = storeVolumeService.findOneUsingSpecs(id);
        if (domainModel != null) {
            return ResponseEntity.ok(new SimpleStoreVolumeView(domainModel));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateOne(@PathVariable("id") Integer id, @RequestBody StoreVolume request) {
        securityService.checkPermission(PermissionType.STORE_VOLUMES_UPDATE);

        StoreVolume domainModel = storeVolumeService.updateOne(id, request);
        return ResponseEntity.ok(new SimpleStoreVolumeView(domainModel));
    }


}
