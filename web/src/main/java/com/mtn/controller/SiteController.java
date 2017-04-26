package com.mtn.controller;

import com.mtn.model.domain.Site;
import com.mtn.model.view.SiteView;
import com.mtn.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Allen on 4/25/2017.
 */
@RestController
@RequestMapping("/api/site")
public class SiteController {

    @Autowired
    private SiteService siteService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        Site domainModel = siteService.findOneUsingSpecs(id);
        if (domainModel != null) {
            return ResponseEntity.ok(new SiteView(domainModel));
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
