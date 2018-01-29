package com.mtn.controller;

import com.mtn.model.domain.StoreSurvey;
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
    private StoreSurveyService surveyService;

    @Override
    public StoreSurveyService getEntityService() {
        return surveyService;
    }

    @Override
    public StoreSurveyView getViewFromModel(Object model) {
        return new StoreSurveyView((StoreSurvey) model);
    }
//    @Autowired
//    private ProjectService projectService;
//
//    @RequestMapping(value = "/{id}/project", method = RequestMethod.GET)
//    public ResponseEntity findAllProjectsForStoreSurvey(@PathVariable("id") Integer storeSurveyId) {
//        List<Project> domainModels = projectService.findAllByStoreSurveyId(storeSurveyId);
//        return ResponseEntity.ok(domainModels.stream().map(SimpleProjectView::new).collect(Collectors.toList()));
//    }

}
