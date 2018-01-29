package com.mtn.controller;

import com.mtn.model.domain.StoreModel;
import com.mtn.model.view.StoreModelView;
import com.mtn.service.StoreModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/store-model")
public class StoreModelController extends CrudControllerImpl<StoreModel> {

    @Autowired
    private StoreModelService modelService;

    @Override
    public StoreModelService getEntityService() {
        return modelService;
    }

    @Override
    public StoreModelView getViewFromModel(Object model) {
        return new StoreModelView((StoreModel) model);
    }
}
