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
	public StoreListController(StoreListService storeListService, StoreService storeService) {
		super(storeListService, StoreListView::new);
		this.storeService = storeService;
	}

	@GetMapping
	public ResponseEntity findAll(Pageable page,
			@RequestParam(value = "subscriber-id", required = false) Integer subscriberId) {
		Page<StoreList> domainModels;
		if (subscriberId != null) {
			domainModels = ((StoreListService) this.entityService).findAllByQueryUsingSpecs(page, subscriberId);
		} else {
			domainModels = this.entityService.findAllUsingSpecs(page);
		}
		return ResponseEntity.ok(domainModels.map(SimpleStoreListView::new));
	}

	@PostMapping(path = "/{id}/addStores")
	public ResponseEntity addStoresToStoreList(@PathVariable("id") Integer storeListId,
			@RequestBody List<Integer> request) {
		StoreList storeList = ((StoreListService) this.entityService).addStoresToStoreList(storeListId, request);
		return ResponseEntity.ok(new SimpleStoreListView(storeList));
	}

	@DeleteMapping(path = "/{id}/removeStores")
	public ResponseEntity removeStoresFromStoreList(@PathVariable("id") Integer storeListId,
			@RequestBody List<Integer> request) {
		StoreList storeList = ((StoreListService) this.entityService).removeStoresFromStoreList(storeListId, request);
		return ResponseEntity.ok(new SimpleStoreListView(storeList));
	}

}
