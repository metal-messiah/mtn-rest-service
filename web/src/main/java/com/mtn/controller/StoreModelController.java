package com.mtn.controller;

import com.mtn.model.domain.StoreModel;
import com.mtn.model.simpleView.SimpleStoreModelView;
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
    public Object getViewFromModel(StoreModel model) {
        return new StoreModelView(model);
    }

    @Override
    public Object getSimpleViewFromModel(StoreModel model) {
        return new SimpleStoreModelView(model);
    }
}
