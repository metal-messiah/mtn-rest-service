package com.mtn.controller;

import com.mtn.model.domain.*;
import com.mtn.model.simpleView.*;
import com.mtn.model.view.*;
import com.mtn.service.*;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/project")
public class ProjectController extends CrudControllerImpl<Project> {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private StoreModelService modelService;
    @Autowired
    private ShoppingCenterService shoppingCenterService;
    @Autowired
    private ShoppingCenterCasingService shoppingCenterCasingService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private StoreCasingService storeCasingService;

    @RequestMapping(value = "/{id}/store-model", method = RequestMethod.POST)
    public ResponseEntity addOneStoreModelToProject(@PathVariable("id") Integer id, @RequestBody StoreModel request) {
        StoreModel domainModel = projectService.addOneModelToProject(id, request);
        return ResponseEntity.ok(new StoreModelView(domainModel));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAll(Pageable page,
                                  @RequestParam(value = "query", required = false) String query,
                                  @RequestParam(value = "active", required = false) Boolean active,
                                  @RequestParam(value = "primaryData", required = false) Boolean primaryData) {
        Page<Project> domainModels;
        if (query != null || active != null || primaryData != null) {
            domainModels = getEntityService().findAllUsingSpecs(page, query, active, primaryData);
        } else {
            domainModels = getEntityService().findAllUsingSpecs(page);
        }
        return ResponseEntity.ok(domainModels.map(this::getSimpleViewFromModel));
    }

    @RequestMapping(path = "/{id}/store-model", method = RequestMethod.GET)
    public ResponseEntity findAllStoreModelsForProject(@PathVariable("id") Integer projectId) {
        List<StoreModel> domainModels = modelService.findAllByProjectIdUsingSpecs(projectId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleStoreModelView::new).collect(Collectors.toList()));
    }

    @RequestMapping(path = "/{id}/shopping-center", method = RequestMethod.GET)
    public ResponseEntity findAllShoppingCentersForProject(@PathVariable("id") Integer projectId) {
        List<ShoppingCenter> domainModels = shoppingCenterService.findAllByProjectId(projectId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleShoppingCenterView::new).collect(Collectors.toList()));
    }

    @RequestMapping(path = "/{id}/shopping-center-casing", method = RequestMethod.GET)
    public ResponseEntity findAllShoppingCenterCasingsForProject(@PathVariable("id") Integer projectId) {
        List<ShoppingCenterCasing> domainModels = shoppingCenterCasingService.findAllByProjectId(projectId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleShoppingCenterCasingView::new).collect(Collectors.toList()));
    }

    @RequestMapping(path = "/{id}/store", method = RequestMethod.GET)
    public ResponseEntity findAllStoresForProject(@PathVariable("id") Integer projectId) {
        List<Store> domainModels = storeService.findAllByProjectId(projectId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleStoreView::new).collect(Collectors.toList()));
    }

    @RequestMapping(path = "/{id}/store-casing", method = RequestMethod.GET)
    public ResponseEntity findAllStoreCasingsForProject(@PathVariable("id") Integer projectId) {
        List<StoreCasing> domainModels = storeCasingService.findAllByProjectId(projectId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleStoreCasingView::new).collect(Collectors.toList()));
    }

    @Override
    public ProjectService getEntityService() {
        return projectService;
    }

    @Override
    public Object getViewFromModel(Project model) {
        return new ProjectView(model);
    }

    @Override
    public Object getSimpleViewFromModel(Project model) {
        return new SimpleProjectView(model);
    }
}
