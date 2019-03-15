package com.mtn.controller;

import com.mtn.model.domain.StoreSurvey;
import com.mtn.model.view.StoreSurveyView;
import com.mtn.service.StoreSurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/store-survey")
public class StoreSurveyController extends CrudController<StoreSurvey, StoreSurveyView> {

    @Autowired
    public StoreSurveyController(StoreSurveyService storeSurveyService) {
        super(storeSurveyService, StoreSurveyView::new);
    }

}
