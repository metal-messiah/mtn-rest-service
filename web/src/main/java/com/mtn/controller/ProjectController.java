package com.mtn.controller;

import com.mtn.model.domain.Project;
import com.mtn.model.domain.StoreModel;
import com.mtn.model.view.ProjectView;
import com.mtn.model.view.SimpleProjectView;
import com.mtn.model.view.SimpleStoreModelView;
import com.mtn.model.view.StoreModelView;
import com.mtn.service.ProjectService;
import com.mtn.service.SecurityService;
import com.mtn.service.StoreModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private StoreModelService modelService;
    @Autowired
    private SecurityService securityService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addOne(@RequestBody Project request) {
        securityService.checkPermission("PROJECTS_CREATE");

        Project domainModel = projectService.addOne(request);
        return ResponseEntity.ok(new ProjectView(domainModel));
    }

    @RequestMapping(value = "/{id}/store-model", method = RequestMethod.POST)
    public ResponseEntity addOneStoreModelToProject(@PathVariable("id") Integer id, @RequestBody StoreModel request) {
        securityService.checkPermission("STORE_MODELS_CREATE");

        StoreModel domainModel = projectService.addOneModelToProject(id, request);
        return ResponseEntity.ok(new StoreModelView(domainModel));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOne(@PathVariable("id") Integer id) {
        securityService.checkPermission("PROJECTS_DELETE");

        projectService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAll() {
        securityService.checkPermission("PROJECTS_READ");

        List<Project> domainModels = projectService.findAllUsingSpecs();
        return ResponseEntity.ok(domainModels.stream().map(SimpleProjectView::new).collect(Collectors.toList()));
    }

    @RequestMapping(path = "/{id}/store-model", method = RequestMethod.GET)
    public ResponseEntity findAllStoreModelsForProject(@PathVariable("id") Integer projectId) {
        securityService.checkPermission("STORE_MODELS_READ");

        List<StoreModel> domainModels = modelService.findAllByProjectIdUsingSpecs(projectId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleStoreModelView::new).collect(Collectors.toList()));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        securityService.checkPermission("PROJECTS_READ");

        Project domainModel = projectService.findOneUsingSpecs(id);
        if (domainModel != null) {
            return ResponseEntity.ok(new ProjectView(domainModel));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateOne(@PathVariable("id") Integer id, @RequestBody Project request) {
        securityService.checkPermission("PROJECTS_UPDATE");

        Project domainModel = projectService.updateOne(id, request);
        return ResponseEntity.ok(new SimpleProjectView(domainModel));
    }


}
