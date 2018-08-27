package com.mtn.controller;

import com.mtn.model.domain.ExtractionFieldSet;
import com.mtn.model.view.ExtractionFieldSetView;
import com.mtn.service.ExtractionFieldSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/extraction-field-set")
public class ExtractionFieldSetController extends CrudController<ExtractionFieldSet, ExtractionFieldSetView> {

    @Autowired
    public ExtractionFieldSetController(ExtractionFieldSetService extractionFieldSetService) {
        super(extractionFieldSetService, ExtractionFieldSetView::new);
    }

    @GetMapping
    public ResponseEntity findAll(Pageable page) {
        Page<ExtractionFieldSet> domainModels = this.entityService.findAllUsingSpecs(page);
        return ResponseEntity.ok(domainModels.map(ExtractionFieldSetView::new));
    }

}
