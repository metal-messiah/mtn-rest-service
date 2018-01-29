package com.mtn.controller;

import com.mtn.model.domain.StoreCasing;
import com.mtn.model.view.StoreCasingView;
import com.mtn.service.StoreCasingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/store-casing")
public class StoreCasingController extends CrudControllerImpl<StoreCasing> {

    @Autowired
    private StoreCasingService storeCasingService;
//    @Autowired
//    private ProjectService projectService;

//    @RequestMapping(value = "/{id}/project", method = RequestMethod.GET)
//    public ResponseEntity findAllProjectsForStoreCasing(@PathVariable("id") Integer storeCasingId) {
//        List<Project> domainModels = projectService.findAllByStoreCasingId(storeCasingId);
//        return ResponseEntity.ok(domainModels.stream().map(SimpleProjectView::new).collect(Collectors.toList()));
//    }

    @Override
    public StoreCasingService getEntityService() {
        return storeCasingService;
    }

    @Override
    public StoreCasingView getViewFromModel(Object model) {
        return new StoreCasingView((StoreCasing) model);
    }
}
