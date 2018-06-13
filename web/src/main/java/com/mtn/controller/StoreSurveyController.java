package com.mtn.controller;

import com.mtn.model.domain.StoreSurvey;
import com.mtn.model.simpleView.SimpleStoreSurveyView;
import com.mtn.model.view.StoreSurveyView;
import com.mtn.service.StoreSurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Allen on 6/7/2017.
 */
@RestController
@RequestMapping("/api/store-survey")
public class StoreSurveyController extends CrudControllerImpl<StoreSurvey> {

    @Autowired
    private StoreSurveyService storeSurveyService;

    @Override
    public StoreSurveyService getEntityService() {
        return storeSurveyService;
    }

    @Override
    public Object getViewFromModel(StoreSurvey model) {
        return new StoreSurveyView(model);
    }

    @Override
    public Object getSimpleViewFromModel(StoreSurvey model) {
        return new SimpleStoreSurveyView(model);
    }

}
