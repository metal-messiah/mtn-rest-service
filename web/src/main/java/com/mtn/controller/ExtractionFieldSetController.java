package com.mtn.controller;

import com.mtn.model.domain.ExtractionFieldSet;
import com.mtn.model.view.ExtractionFieldSetView;
import com.mtn.service.ExtractionFieldSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/extraction-field-set")
public class ExtractionFieldSetController extends CrudControllerImpl<ExtractionFieldSet> {

    @Autowired
    private ExtractionFieldSetService extractionFieldSetService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAll(Pageable page) {
        Page<ExtractionFieldSet> domainModels = getEntityService().findAll(page);
        return ResponseEntity.ok(domainModels.map(this::getSimpleViewFromModel));
    }

    @Override
    public ExtractionFieldSetService getEntityService() {
        return extractionFieldSetService;
    }

    @Override
    public Object getViewFromModel(ExtractionFieldSet model) {
        return new ExtractionFieldSetView(model);
    }

    @Override
    public Object getSimpleViewFromModel(ExtractionFieldSet model) {
        return new ExtractionFieldSetView(model);
    }
}
