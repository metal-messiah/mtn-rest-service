package com.mtn.controller;

import com.mtn.model.domain.StoreSurvey;
import com.mtn.model.view.SimpleStoreSurveyView;
import com.mtn.service.SecurityService;
import com.mtn.service.StoreSurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Allen on 6/7/2017.
 */
@RestController
@RequestMapping("/api/store-survey")
public class StoreSurveyController {

    @Autowired
    private StoreSurveyService surveyService;
    @Autowired
    private SecurityService securityService;

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOne(@PathVariable("id") Integer id) {
        securityService.checkPermission("SHOPPING_CENTER_SURVEYS_DELETE");

        surveyService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        securityService.checkPermission("SHOPPING_CENTER_SURVEYS_READ");

        StoreSurvey domainModel = surveyService.findOneUsingSpecs(id);
        if (domainModel != null) {
            return ResponseEntity.ok(new SimpleStoreSurveyView(domainModel));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateOne(@PathVariable("id") Integer id, @RequestBody StoreSurvey request) {
        securityService.checkPermission("SHOPPING_CENTER_SURVEYS_UPDATE");

        StoreSurvey domainModel = surveyService.updateOne(id, request);
        return ResponseEntity.ok(new SimpleStoreSurveyView(domainModel));
    }
}
