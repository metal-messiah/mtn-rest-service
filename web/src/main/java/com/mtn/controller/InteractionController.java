package com.mtn.controller;

import com.mtn.model.domain.Interaction;
import com.mtn.model.view.SimpleInteractionView;
import com.mtn.service.InteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interaction")
public class InteractionController {

    @Autowired
    private InteractionService interactionService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addOne(@RequestBody Interaction request) {
        Interaction domainModel = interactionService.addOne(request);
        return ResponseEntity.ok(new SimpleInteractionView(domainModel));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOne(@PathVariable("id") Integer id) {
        interactionService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        Interaction domainModel = interactionService.findOneUsingSpecs(id);
        return ResponseEntity.ok(new SimpleInteractionView(domainModel));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateOne(@PathVariable("id") Integer id, @RequestBody Interaction request) {
        Interaction domainModel = interactionService.updateOne(id, request);
        return ResponseEntity.ok(new SimpleInteractionView(domainModel));
    }
}
