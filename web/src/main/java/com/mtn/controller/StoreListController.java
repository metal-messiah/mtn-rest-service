package com.mtn.controller;

import com.mtn.constant.StoreListSearchType;
import com.mtn.model.domain.AuditingEntity;
import com.mtn.model.domain.Site;
import com.mtn.model.domain.StoreList;
import com.mtn.model.simpleView.SimpleStoreListView;
import com.mtn.model.simpleView.SiteMarker;
import com.mtn.model.simpleView.StoreMarker;
import com.mtn.model.view.StoreListView;
import com.mtn.service.SiteService;
import com.mtn.service.StoreListService;
import com.mtn.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/store-list")
public class StoreListController extends CrudController<StoreList, StoreListView> {

	private final SiteService siteService;

	@Autowired
	public StoreListController(StoreListService storeListService,
							   StoreService storeService,
							   SiteService siteService) {
		super(storeListService, StoreListView::new);
		this.siteService = siteService;
	}

	@GetMapping
	public ResponseEntity findAll(Pageable page,
								  @RequestParam(value = "including-store-ids", required = false) List<Integer> includingStoreIds,
								  @RequestParam(value = "excluding-store-ids", required = false) List<Integer> excludingStoreIds,
								  @RequestParam(value = "search-type", required = false, defaultValue = "ANY") StoreListSearchType searchType) {
		Page<StoreList> domainModels = ((StoreListService) this.entityService).findAllByQueryUsingSpecs(page, includingStoreIds, excludingStoreIds, searchType);
		return ResponseEntity.ok(domainModels.map(SimpleStoreListView::new));
	}

	@GetMapping("/{id}/marker-data")
	public ResponseEntity<List<SiteMarker>> getSiteMarkersForStoreList(@PathVariable("id") Integer storeListId) {
		StoreList storeList = this.entityService.findOne(storeListId);
		if (storeList.getStores().size() == 0) {
			return ResponseEntity.ok(new ArrayList<>());
		} else {
			// Get the Store Ids that need to be included
			Set<Integer> storeIds = storeList.getStores().stream().map(AuditingEntity::getId).collect(Collectors.toSet());
			// Get the distinct set of Sites that need to be queried
			List<Integer> siteIds = storeList.getStores().stream().map(st -> st.getSite().getId()).distinct().collect(Collectors.toList());
			List<Site> sites = this.siteService.findAllByIdsUsingSpecs(siteIds);
			List<SiteMarker> siteMarkers = sites.stream().map(si -> {
				SiteMarker sm = new SiteMarker(si);
				// Filter out stores that aren't in the specified list
				List<StoreMarker> filteredStores = sm.getStores().stream().filter(st -> storeIds.contains(st.getId())).collect(Collectors.toList());
				sm.setStores(filteredStores);
				return sm;
			}).collect(Collectors.toList());
			return ResponseEntity.ok(siteMarkers);
		}
	}

	@PutMapping(path = "/{id}/add-stores")
	public ResponseEntity addStoresToStoreList(@PathVariable("id") Integer storeListId,
											   @RequestBody List<Integer> request) {
		StoreList storeList = ((StoreListService) this.entityService).addStoresToStoreList(storeListId, request);
		return ResponseEntity.ok(new StoreListView(storeList));
	}

	@PutMapping(path = "/{id}/remove-stores")
	public ResponseEntity removeStoresFromStoreList(@PathVariable("id") Integer storeListId,
													@RequestBody List<Integer> request) {
		StoreList storeList = ((StoreListService) this.entityService).removeStoresFromStoreList(storeListId, request);
		return ResponseEntity.ok(new StoreListView(storeList));
	}
}
