package com.mtn.controller;

import com.mtn.constant.PermissionType;
import com.mtn.model.domain.Project;
import com.mtn.model.domain.StoreSurvey;
import com.mtn.model.view.SimpleProjectView;
import com.mtn.model.view.StoreSurveyView;
import com.mtn.service.ProjectService;
import com.mtn.service.SecurityService;
import com.mtn.service.StoreSurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private ProjectService projectService;

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOne(@PathVariable("id") Integer id) {
        securityService.checkPermission(PermissionType.STORE_SURVEYS_DELETE);

        surveyService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}/project", method = RequestMethod.GET)
    public ResponseEntity findAllProjectsForStoreSurvey(@PathVariable("id") Integer storeSurveyId) {
        securityService.checkPermission(PermissionType.PROJECTS_READ);

        List<Project> domainModels = projectService.findAllByStoreSurveyId(storeSurveyId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleProjectView::new).collect(Collectors.toList()));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        securityService.checkPermission(PermissionType.STORE_SURVEYS_READ);

        StoreSurvey domainModel = surveyService.findOneUsingSpecs(id);
        if (domainModel != null) {
            return ResponseEntity.ok(new StoreSurveyView(domainModel));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateOne(@PathVariable("id") Integer id, @RequestBody StoreSurvey request) {
        securityService.checkPermission(PermissionType.STORE_SURVEYS_UPDATE);

        StoreSurvey domainModel = surveyService.updateOne(id, request);
        return ResponseEntity.ok(new StoreSurveyView(domainModel));
    }
}
