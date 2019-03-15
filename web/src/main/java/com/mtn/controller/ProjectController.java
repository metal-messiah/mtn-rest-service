package com.mtn.controller;

import com.mtn.model.domain.Boundary;
import com.mtn.model.domain.Project;
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

@RestController
@RequestMapping("/api/project")
public class ProjectController extends CrudController<Project, ProjectView> {

	private final BoundaryService boundaryService;

	@Autowired
	public ProjectController(ProjectService projectService,
							 BoundaryService boundaryService) {
		super(projectService, ProjectView::new);
		this.boundaryService = boundaryService;
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
}
