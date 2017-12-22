package com.mtn.controller;

import com.mtn.constant.PermissionType;
import com.mtn.model.domain.Interaction;
import com.mtn.model.simpleView.SimpleInteractionView;
import com.mtn.service.InteractionService;
import com.mtn.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interaction")
public class InteractionController {

    @Autowired
    private InteractionService interactionService;
    @Autowired
    private SecurityService securityService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addOne(@RequestBody Interaction request) {
        securityService.checkPermission(PermissionType.INTERACTIONS_CREATE);

        Interaction domainModel = interactionService.addOne(request);
        return ResponseEntity.ok(new SimpleInteractionView(domainModel));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOne(@PathVariable("id") Integer id) {
        securityService.checkPermission(PermissionType.INTERACTIONS_DELETE);

        interactionService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        securityService.checkPermission(PermissionType.INTERACTIONS_READ);

        Interaction domainModel = interactionService.findOneUsingSpecs(id);
        return ResponseEntity.ok(new SimpleInteractionView(domainModel));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateOne(@PathVariable("id") Integer id, @RequestBody Interaction request) {
        securityService.checkPermission(PermissionType.INTERACTIONS_READ);

        Interaction domainModel = interactionService.updateOne(id, request);
        return ResponseEntity.ok(new SimpleInteractionView(domainModel));
    }
}
