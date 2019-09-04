package com.mtn.controller;

import com.mtn.model.domain.Project;
import com.mtn.service.GravityService;
import com.mtn.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gravity")
public class GravityController {

	final private ProjectService projectService;
	final private GravityService gravityService;

	@Autowired
	public GravityController(GravityService gravityService,
							 ProjectService projectService) {
		this.projectService = projectService;
		this.gravityService = gravityService;
	}

	@GetMapping
	public ResponseEntity getModelChoropleth(@RequestParam("project-id") Integer projectId,
											 @RequestParam("store-id") Integer storeId) {
		Project project = this.projectService.findOneUsingSpecs(projectId);
		try {
			String geoJson = this.gravityService.getModelChoropleth(project, storeId);
			return ResponseEntity.ok(geoJson);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
