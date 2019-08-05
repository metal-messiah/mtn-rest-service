package com.mtn.controller;

import com.mtn.model.domain.Boundary;
import com.mtn.model.domain.Project;
import com.mtn.model.domain.Site;
import com.mtn.model.simpleView.ProjectSummary;
import com.mtn.model.simpleView.SimpleProjectView;
import com.mtn.model.view.BoundaryView;
import com.mtn.model.view.ProjectView;
import com.mtn.service.BoundaryService;
import com.mtn.service.ProjectService;
import com.mtn.service.SiteService;
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
	private final SiteService siteService;

	@Autowired
	public ProjectController(ProjectService projectService,
							 SiteService siteService,
							 BoundaryService boundaryService) {
		super(projectService, ProjectView::new);
		this.siteService = siteService;
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

	@GetMapping("/{id}/cased-store-ids")
	public ResponseEntity<List<Integer>> casedStoreIds(@PathVariable("id") Integer projectId) {
		Project project = this.entityService.findOne(projectId);
		List<Integer> storeIds = project.getStoreCasings().stream()
				.map(c -> c.getStore().getId())
				.distinct()
				.collect(Collectors.toList());
		return ResponseEntity.ok(storeIds);
	}

	@GetMapping("/{id}/summary")
	public ResponseEntity getProjectSummary(@PathVariable("id") Integer projectId) {
		Project project = this.entityService.findOne(projectId);
		if (project.getBoundary() != null) {
			List<Site> sitesInBounds = siteService.findAllInGeoJson(project.getBoundary().getGeojson());

			ProjectSummary projectSummary = new ProjectSummary(project, sitesInBounds);
			return ResponseEntity.ok(projectSummary);
		} else {
			return ResponseEntity.badRequest().body("Project does not have a boundary!");
		}
	}
}
