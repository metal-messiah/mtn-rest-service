package com.mtn.controller;

import com.mtn.model.domain.Project;
import com.mtn.model.domain.Store;
import com.mtn.model.domain.StoreCasing;
import com.mtn.model.domain.StoreVolume;
import com.mtn.model.view.StoreCasingView;
import com.mtn.model.view.StoreVolumeView;
import com.mtn.service.ProjectService;
import com.mtn.service.StoreCasingService;
import com.mtn.service.StoreVolumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/store-casing")
public class StoreCasingController extends CrudController<StoreCasing, StoreCasingView> {

    private final StoreVolumeService storeVolumeService;
    private final ProjectService projectService;

    @Autowired
    public StoreCasingController(StoreCasingService storeCasingService, StoreVolumeService storeVolumeService, ProjectService projectService) {
        super(storeCasingService, StoreCasingView::new);
        this.storeVolumeService = storeVolumeService;
        this.projectService = projectService;
    }

    @PutMapping(value = "/{storeCasingId}/projects/{projectId}")
    public ResponseEntity addProject(@PathVariable("storeCasingId") Integer storeCasingId,
                                     @PathVariable("projectId") Integer projectId) {
        Project project = projectService.findOne(projectId);
        StoreCasing domainModel = ((StoreCasingService) this.entityService).addProject(storeCasingId, project);
        return ResponseEntity.ok(new StoreCasingView(domainModel));
    }

    @RequestMapping(value = "/{storeCasingId}/projects/{projectId}", method = RequestMethod.DELETE)
    public ResponseEntity removeProject(@PathVariable("storeCasingId") Integer storeCasingId,
                                     @PathVariable("projectId") Integer projectId) {
        StoreCasing domainModel = ((StoreCasingService) this.entityService).removeProject(storeCasingId, projectId);
        return ResponseEntity.ok(new StoreCasingView(domainModel));
    }

    @RequestMapping(value = "/{storeCasingId}/store-volume", method = RequestMethod.DELETE)
    public ResponseEntity removeVolume(@PathVariable("storeCasingId") Integer storeCasingId) {
        StoreCasing domainModel = ((StoreCasingService) this.entityService).removeStoreVolume(storeCasingId);
        return ResponseEntity.ok(new StoreCasingView(domainModel));
    }

    @RequestMapping(value = "/{storeCasingId}/store-volume", method = RequestMethod.POST)
    public ResponseEntity createNewVolume(@PathVariable("storeCasingId") Integer storeCasingId,
                                       @RequestBody StoreVolumeView volumeRequest) {
        Store store = this.entityService.findOneUsingSpecs(storeCasingId).getStore();
        StoreVolume volume = storeVolumeService.addOneToStore(volumeRequest, store);
        StoreCasing casing = ((StoreCasingService) this.entityService).setStoreVolume(storeCasingId, volume);
        return ResponseEntity.ok(new StoreCasingView(casing));
    }

    @RequestMapping(value = "/{storeCasingId}/store-volume/{storeVolumeId}", method = RequestMethod.PUT)
    public ResponseEntity updateVolume(@PathVariable("storeCasingId") Integer storeCasingId,
                                       @PathVariable("storeVolumeId") Integer storeVolumeId) {
        StoreVolume volume = storeVolumeService.findOneUsingSpecs(storeVolumeId);
        StoreCasing domainModel = ((StoreCasingService) this.entityService).setStoreVolume(storeCasingId, volume);
        return ResponseEntity.ok(new StoreCasingView(domainModel));
    }

}
