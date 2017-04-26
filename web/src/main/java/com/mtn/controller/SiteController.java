package com.mtn.controller;

import com.mtn.model.domain.Site;
import com.mtn.model.view.SiteView;
import com.mtn.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Allen on 4/25/2017.
 */
@RestController
@RequestMapping("/api/site")
public class SiteController {

    @Autowired
    private SiteService siteService;

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOne(@PathVariable("id") Integer id) {
        siteService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        Site domainModel = siteService.findOneUsingSpecs(id);
        if (domainModel != null) {
            return ResponseEntity.ok(new SiteView(domainModel));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateOne(@PathVariable("id") Integer id, @RequestBody SiteView request) {
        Site requestModel = new Site(request);
        Site domainModel = siteService.updateOne(id, requestModel);
        return ResponseEntity.ok(new SiteView(domainModel));
    }
}
