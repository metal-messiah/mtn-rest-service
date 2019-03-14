package com.mtn.controller;

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
@RequestMapping("/api/store-list")
public class StoreListController extends CrudController<StoreList, StoreListView> {

	private final StoreService storeService;

	@Autowired
	public StoreListController(StoreListService storeListService, 
			StoreService storeService) {
		super(storeListService, StoreListView::new);
		this.storeService = storeService;
	}

	@GetMapping
	public ResponseEntity findAll(Pageable page, @RequestParam(value = "subscriber-id", required = false) Integer subscriberId) {
		Page<StoreList> domainModels;
		if (subscriberId != null) {
			domainModels = ((StoreListService) this.entityService).findAllByQueryUsingSpecs(page, subscriberId);
		} else {
			domainModels = this.entityService.findAllUsingSpecs(page);
		}
		return ResponseEntity.ok(domainModels.map(SimpleStoreListView::new));
	}

	// @GetMapping(path = "/{id}/subscription")
	// public ResponseEntity findBoundaryFormProject(@PathVariable("id") Integer projectId) {
	// 	Project project = this.entityService.findOneUsingSpecs(projectId);
	// 	if (project.getBoundary() != null) {
	// 		return ResponseEntity.ok(new BoundaryView(project.getBoundary()));
	// 	} else {
	// 		return ResponseEntity.noContent().build();
	// 	}
	// }

	// @PostMapping(path = "/{id}/subscription")
	// public ResponseEntity createProjectBoundary(@PathVariable("id") Integer projectId,
	// 		@RequestBody BoundaryView request) {
	// 	Boundary boundary = boundaryService.addOne(request);
	// 	Project project = ((ProjectService) this.entityService).setProjectBoundary(projectId, boundary);
	// 	return ResponseEntity.ok(new SimpleProjectView(project));
	// }

	// @DeleteMapping(path = "/{id}/boundary")
	// public ResponseEntity removeProjectBoundary(@PathVariable("id") Integer projectId) {
	// 	Project project = ((ProjectService) this.entityService).removeProjectBoundary(projectId);
	// 	return ResponseEntity.ok(new SimpleProjectView(project));
	// }

	// @GetMapping(path = "/{id}/store")
	// public ResponseEntity findAllStoresForProject(@PathVariable("id") Integer projectId) {
	// 	List<Store> domainModels = storeService.findAllByProjectId(projectId);
	// 	return ResponseEntity.ok(domainModels.stream().map(SimpleStoreView::new).collect(Collectors.toList()));
	// }

	// @GetMapping(path = "/{id}/store-casing")
	// public ResponseEntity findAllStoreCasingsForProject(@PathVariable("id") Integer projectId) {
	// 	List<StoreCasing> domainModels = storeCasingService.findAllByProjectId(projectId);
	// 	return ResponseEntity.ok(domainModels.stream().map(SimpleStoreCasingView::new).collect(Collectors.toList()));
	// }

}
