package com.mtn.controller;

import com.mtn.model.domain.ShoppingCenterCasing;
import com.mtn.model.simpleView.SimpleShoppingCenterCasingView;
import com.mtn.model.view.ShoppingCenterCasingView;
import com.mtn.service.ShoppingCenterCasingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/shopping-center-casing")
public class ShoppingCenterCasingController extends CrudControllerImpl<ShoppingCenterCasing> {

    @Autowired
    private ShoppingCenterCasingService shoppingCenterCasingService;
//    @Autowired
//    private ProjectService projectService;
//
//    @RequestMapping(value = "/{id}/project", method = RequestMethod.GET)
//    public ResponseEntity findAllProjectsForShoppingCenterCasing(@PathVariable("id") Integer shoppingCenterCasingId) {
//        List<Project> domainModels = projectService.findAllByShoppingCenterCasingId(shoppingCenterCasingId);
//        return ResponseEntity.ok(domainModels.stream().map(SimpleProjectView::new).collect(Collectors.toList()));
//    }

    @Override
    public ShoppingCenterCasingService getEntityService() {
        return shoppingCenterCasingService;
    }

    @Override
    public Object getViewFromModel(ShoppingCenterCasing model) {
        return new ShoppingCenterCasingView(model);
    }

    @Override
    public Object getSimpleViewFromModel(ShoppingCenterCasing model) {
        return new SimpleShoppingCenterCasingView(model);
    }
}
