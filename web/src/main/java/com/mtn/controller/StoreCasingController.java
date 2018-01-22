package com.mtn.controller;

import com.mtn.model.domain.Project;
import com.mtn.model.domain.StoreCasing;
import com.mtn.model.simpleView.SimpleProjectView;
import com.mtn.model.view.StoreCasingView;
import com.mtn.service.ProjectService;
import com.mtn.service.StoreCasingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/store-casing")
public class StoreCasingController {

    @Autowired
    private StoreCasingService storeCasingService;
    @Autowired
    private ProjectService projectService;

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOne(@PathVariable("id") Integer id) {
        storeCasingService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}/project", method = RequestMethod.GET)
    public ResponseEntity findAllProjectsForStoreCasing(@PathVariable("id") Integer storeCasingId) {
        List<Project> domainModels = projectService.findAllByStoreCasingId(storeCasingId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleProjectView::new).collect(Collectors.toList()));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        StoreCasing domainModel = storeCasingService.findOneUsingSpecs(id);
        if (domainModel == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(new StoreCasingView(domainModel));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateOne(@PathVariable("id") Integer id, @RequestBody StoreCasing request) {
        StoreCasing domainModel = storeCasingService.updateOne(id, request);
        return ResponseEntity.ok(new StoreCasingView(domainModel));
    }


}
