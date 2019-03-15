package com.mtn.controller;

import com.mtn.model.domain.StoreModel;
import com.mtn.model.view.StoreModelView;
import com.mtn.service.StoreModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/store-model")
public class StoreModelController extends CrudController<StoreModel, StoreModelView> {

    @Autowired
    public StoreModelController(StoreModelService modelService) {
       super(modelService, StoreModelView::new);
    }
}
