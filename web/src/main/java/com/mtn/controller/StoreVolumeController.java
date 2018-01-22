package com.mtn.controller;

import com.mtn.model.domain.StoreVolume;
import com.mtn.model.simpleView.SimpleStoreVolumeView;
import com.mtn.service.StoreVolumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/store-volume")
public class StoreVolumeController {

    @Autowired
    private StoreVolumeService storeVolumeService;

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOne(@PathVariable("id") Integer id) {
        storeVolumeService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        StoreVolume domainModel = storeVolumeService.findOneUsingSpecs(id);
        if (domainModel != null) {
            return ResponseEntity.ok(new SimpleStoreVolumeView(domainModel));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateOne(@PathVariable("id") Integer id, @RequestBody StoreVolume request) {
        StoreVolume domainModel = storeVolumeService.updateOne(id, request);
        return ResponseEntity.ok(new SimpleStoreVolumeView(domainModel));
    }


}
