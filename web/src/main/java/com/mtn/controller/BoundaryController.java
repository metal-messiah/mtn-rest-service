package com.mtn.controller;

import com.mtn.model.domain.Boundary;
import com.mtn.model.domain.Project;
import com.mtn.model.domain.StoreCasing;
import com.mtn.model.simpleView.SimpleBoundaryView;
import com.mtn.model.simpleView.SimpleProjectView;
import com.mtn.model.view.BoundaryView;
import com.mtn.model.view.ProjectView;
import com.mtn.service.BoundaryService;
import com.mtn.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/boundary")
public class BoundaryController extends CrudController<Boundary, BoundaryView> {

	@Autowired
	public BoundaryController(BoundaryService boundaryService) {
		super(boundaryService, BoundaryView::new);
	}

	@GetMapping
	public ResponseEntity findAll(Pageable page) {
		Page<Boundary> domainModels = this.entityService.findAllUsingSpecs(page);
		return ResponseEntity.ok(domainModels.map(SimpleBoundaryView::new));
	}
}
