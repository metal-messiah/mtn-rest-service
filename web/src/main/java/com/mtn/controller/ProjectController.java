package com.mtn.controller;

import com.mtn.model.domain.*;
import com.mtn.model.view.*;
import com.mtn.service.*;
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
    @Autowired
    private ShoppingCenterService shoppingCenterService;
    @Autowired
    private ShoppingCenterCasingService shoppingCenterCasingService;
    @Autowired
    private ShoppingCenterSurveyService shoppingCenterSurveyService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private StoreCasingService storeCasingService;
    @Autowired
    private StoreSurveyService storeSurveyService;

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

    @RequestMapping(path = "/{id}/shopping-center", method = RequestMethod.GET)
    public ResponseEntity findAllShoppingCentersForProject(@PathVariable("id") Integer projectId) {
        securityService.checkPermission("SHOPPING_CENTERS_READ");

        List<ShoppingCenter> domainModels = shoppingCenterService.findAllByProjectId(projectId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleShoppingCenterView::new).collect(Collectors.toList()));
    }

    @RequestMapping(path = "/{id}/shopping-center-casing", method = RequestMethod.GET)
    public ResponseEntity findAllShoppingCenterCasingsForProject(@PathVariable("id") Integer projectId) {
        securityService.checkPermission("SHOPPING_CENTER_CASINGS_READ");

        List<ShoppingCenterCasing> domainModels = shoppingCenterCasingService.findAllByProjectId(projectId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleShoppingCenterCasingView::new).collect(Collectors.toList()));
    }

    @RequestMapping(path = "/{id}/shopping-center-survey", method = RequestMethod.GET)
    public ResponseEntity findAllShoppingCenterSurveysForProject(@PathVariable("id") Integer projectId) {
        securityService.checkPermission("SHOPPING_CENTER_SURVEYS_READ");

        List<ShoppingCenterSurvey> domainModels = shoppingCenterSurveyService.findAllByProjectId(projectId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleShoppingCenterSurveyView::new).collect(Collectors.toList()));
    }

    @RequestMapping(path = "/{id}/store", method = RequestMethod.GET)
    public ResponseEntity findAllStoresForProject(@PathVariable("id") Integer projectId) {
        securityService.checkPermission("STORES_READ");

        List<Store> domainModels = storeService.findAllByProjectId(projectId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleStoreView::new).collect(Collectors.toList()));
    }

    @RequestMapping(path = "/{id}/store-casing", method = RequestMethod.GET)
    public ResponseEntity findAllStoreCasingsForProject(@PathVariable("id") Integer projectId) {
        securityService.checkPermission("STORE_CASINGS_READ");

        List<StoreCasing> domainModels = storeCasingService.findAllByProjectId(projectId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleStoreCasingView::new).collect(Collectors.toList()));
    }

    @RequestMapping(path = "/{id}/store-survey", method = RequestMethod.GET)
    public ResponseEntity findAllStoreSurveysForProject(@PathVariable("id") Integer projectId) {
        securityService.checkPermission("STORE_SURVEYS_READ");

        List<StoreSurvey> domainModels = storeSurveyService.findAllByProjectId(projectId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleStoreSurveyView::new).collect(Collectors.toList()));
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
