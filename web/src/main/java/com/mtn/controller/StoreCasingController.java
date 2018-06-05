package com.mtn.controller;

import com.mtn.model.domain.StoreCasing;
import com.mtn.model.simpleView.SimpleStoreCasingView;
import com.mtn.model.view.StoreCasingView;
import com.mtn.service.StoreCasingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/store-casing")
public class StoreCasingController extends CrudControllerImpl<StoreCasing> {

    @Autowired
    private StoreCasingService storeCasingService;

    @RequestMapping(value = "/{storeCasingId}/projects/{projectId}", method = RequestMethod.PUT)
    public ResponseEntity addProject(@PathVariable("storeCasingId") Integer storeCasingId,
                                     @PathVariable("projectId") Integer projectId) {
        StoreCasing domainModel = storeCasingService.addProject(storeCasingId, projectId);
        return ResponseEntity.ok(new StoreCasingView(domainModel));
    }

    @RequestMapping(value = "/{storeCasingId}/projects/{projectId}", method = RequestMethod.DELETE)
    public ResponseEntity removeProject(@PathVariable("storeCasingId") Integer storeCasingId,
                                     @PathVariable("projectId") Integer projectId) {
        StoreCasing domainModel = storeCasingService.removeProject(storeCasingId, projectId);
        return ResponseEntity.ok(new StoreCasingView(domainModel));
    }

    @RequestMapping(value = "/{storeCasingId}/store-volume", method = RequestMethod.DELETE)
    public ResponseEntity removeVolume(@PathVariable("storeCasingId") Integer storeCasingId) {
        StoreCasing domainModel = storeCasingService.removeStoreVolume(storeCasingId);
        return ResponseEntity.ok(new StoreCasingView(domainModel));
    }

    @RequestMapping(value = "/{storeCasingId}/store-volume/{storeVolumeId}", method = RequestMethod.PUT)
    public ResponseEntity updateVolume(@PathVariable("storeCasingId") Integer storeCasingId,
                                       @PathVariable("storeVolumeId") Integer storeVolumeId) {
        StoreCasing domainModel = storeCasingService.setStoreVolume(storeCasingId, storeVolumeId);
        return ResponseEntity.ok(new StoreCasingView(domainModel));
    }

    @Override
    public StoreCasingService getEntityService() {
        return storeCasingService;
    }

    @Override
    public Object getViewFromModel(StoreCasing model) {
        return new StoreCasingView(model);
    }

    @Override
    public Object getSimpleViewFromModel(StoreCasing model) {
        return new SimpleStoreCasingView(model);
    }
}
