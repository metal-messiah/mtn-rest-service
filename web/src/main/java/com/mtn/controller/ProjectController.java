package com.mtn.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.mtn.model.domain.*;
import com.mtn.model.simpleView.*;
import com.mtn.model.view.*;
import com.mtn.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/project")
public class ProjectController extends CrudController<Project, ProjectView> {

	private final BoundaryService boundaryService;
	private final StoreService storeService;
	private final StoreCasingService storeCasingService;

	@Autowired
	public ProjectController(ProjectService projectService, BoundaryService boundaryService, StoreService storeService, StoreCasingService storeCasingService) {
		super(projectService, ProjectView::new);
		this.boundaryService = boundaryService;
		this.storeService = storeService;
		this.storeCasingService = storeCasingService;
	}

	@GetMapping
	public ResponseEntity findAll(Pageable page,
								  @RequestParam(value = "query", required = false) String query,
								  @RequestParam(value = "active", required = false) Boolean active,
								  @RequestParam(value = "primaryData", required = false) Boolean primaryData) {
		Page<Project> domainModels;
		if (query != null || active != null || primaryData != null) {
			domainModels = ((ProjectService) this.entityService).findAllByQueryUsingSpecs(page, query, active, primaryData);
		} else {
			domainModels = this.entityService.findAllUsingSpecs(page);
		}
		return ResponseEntity.ok(domainModels.map(SimpleProjectView::new));
	}

	@GetMapping(path = "/{id}/boundary")
	public ResponseEntity findBoundaryFormProject(@PathVariable("id") Integer projectId) {
		Project project = this.entityService.findOneUsingSpecs(projectId);
		if (project.getBoundary() != null) {
			return ResponseEntity.ok(new BoundaryView(project.getBoundary()));
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	@PostMapping(path = "/{id}/boundary")
	public ResponseEntity createProjectBoundary(@PathVariable("id") Integer projectId, @RequestBody BoundaryView request) {
		Boundary boundary = boundaryService.addOne(request);
		Project project = ((ProjectService) this.entityService).setProjectBoundary(projectId, boundary);
		return ResponseEntity.ok(new SimpleProjectView(project));
	}

	@DeleteMapping(path = "/{id}/boundary")
	public ResponseEntity removeProjectBoundary(@PathVariable("id") Integer projectId) {
		Project project = ((ProjectService) this.entityService).removeProjectBoundary(projectId);
		return ResponseEntity.ok(new SimpleProjectView(project));
	}

	@GetMapping(path = "/{id}/stores")
	public ResponseEntity findAllStoresForProject(@PathVariable("id") Integer projectId) {
		List<Store> domainModels = storeService.findAllByProjectId(projectId);
		return ResponseEntity.ok(domainModels.stream().map(SimpleStoreView::new).collect(Collectors.toList()));
	}

	@GetMapping(path = "/{id}/store-casing")
	public ResponseEntity findAllStoreCasingsForProject(@PathVariable("id") Integer projectId) {
		List<StoreCasing> domainModels = storeCasingService.findAllByProjectId(projectId);
		return ResponseEntity.ok(domainModels.stream().map(SimpleStoreCasingView::new).collect(Collectors.toList()));
	}

	@GetMapping(path = "/{id}/cased-store-ids")
	public List<Integer> findAllCasedStoreIds(@PathVariable("id") Integer projectId) {
		Project project = this.entityService.findOne(projectId);
		return project.getStoreCasings().stream()
				.filter(storeCasing -> storeCasing.getDeletedDate() == null)
				.map(storeCasing -> storeCasing.getStore().getId()).distinct().collect(Collectors.toList());
	}

}
