package com.mtn.controller;

import com.mtn.model.domain.Project;
import com.mtn.model.domain.ShoppingCenterCasing;
import com.mtn.model.view.ShoppingCenterCasingView;
import com.mtn.model.simpleView.SimpleProjectView;
import com.mtn.service.ProjectService;
import com.mtn.service.ShoppingCenterCasingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/shopping-center-casing")
public class ShoppingCenterCasingController {

    @Autowired
    private ShoppingCenterCasingService casingService;
    @Autowired
    private ProjectService projectService;

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOne(@PathVariable("id") Integer id) {
        casingService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}/project", method = RequestMethod.GET)
    public ResponseEntity findAllProjectsForShoppingCenterCasing(@PathVariable("id") Integer shoppingCenterCasingId) {
        List<Project> domainModels = projectService.findAllByShoppingCenterCasingId(shoppingCenterCasingId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleProjectView::new).collect(Collectors.toList()));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        ShoppingCenterCasing domainModel = casingService.findOneUsingSpecs(id);
        if (domainModel != null) {
            return ResponseEntity.ok(new ShoppingCenterCasingView(domainModel));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateOne(@PathVariable("id") Integer id, @RequestBody ShoppingCenterCasing request) {
        ShoppingCenterCasing domainModel = casingService.updateOne(id, request);
        return ResponseEntity.ok(new ShoppingCenterCasingView(domainModel));
    }
}
