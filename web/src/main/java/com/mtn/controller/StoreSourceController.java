package com.mtn.controller;

import com.mtn.model.domain.BannerSource;
import com.mtn.model.domain.Store;
import com.mtn.model.domain.StoreSource;
import com.mtn.model.simpleView.SimpleStoreSourceView;
import com.mtn.model.view.StoreSourceView;
import com.mtn.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/store-source")
public class StoreSourceController extends CrudController<StoreSource, StoreSourceView> {

	private final ChainXYService chainXYService;
	private final PlannedGroceryService plannedGroceryService;
	private final StoreService storeService;
	private final BannerSourceService bannerSourceService;

	@Autowired
	public StoreSourceController(StoreSourceService storeSourceService,
								 PlannedGroceryService plannedGroceryService,
								 StoreService storeService,
								 BannerSourceService bannerSourceService,
								 ChainXYService chainXYService) {
		super(storeSourceService, StoreSourceView::new);
		this.plannedGroceryService = plannedGroceryService;
		this.chainXYService = chainXYService;
		this.storeService = storeService;
		this.bannerSourceService = bannerSourceService;
	}

	@GetMapping
	public ResponseEntity findAll(Pageable page, @RequestParam Map<String, String> queryMap) {
		Page<StoreSource> domainModels = ((StoreSourceService) this.entityService).findAllByQuery(queryMap, page);
		return ResponseEntity.ok(domainModels.map(SimpleStoreSourceView::new));
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<StoreSourceView> findOne(@PathVariable("id") Integer id) throws Exception {
		StoreSource storeSource = this.entityService.findOneUsingSpecs(id);
		StoreSourceView storeSourceView = new StoreSourceView(storeSource);
		if (storeSource.getSourceName().equals("Planned Grocery")) {
			storeSourceView.setStoreSourceData(this.plannedGroceryService.getStoreDataForSource(storeSource));
		} else if (storeSource.getSourceName().equals("ChainXY")) {
			storeSourceView.setStoreSourceData(this.chainXYService.getStoreDataForSource(storeSource));
		}
		return ResponseEntity.ok(storeSourceView);
	}

	@PutMapping("/{id}/validate")
	public ResponseEntity validate(@PathVariable("id") Integer id) {
		StoreSource storeSource = ((StoreSourceService) this.entityService).validate(id);
		return ResponseEntity.ok(new StoreSourceView(storeSource));
	}

	@PutMapping("/{id}/invalidate")
	public ResponseEntity invalidate(@PathVariable("id") Integer id) {
		StoreSource storeSource = ((StoreSourceService) this.entityService).invalidate(id);
		return ResponseEntity.ok(new StoreSourceView(storeSource));
	}

	@PutMapping("/{id}/store/{storeId}")
	public ResponseEntity setStore(@PathVariable("id") Integer id,
								   @PathVariable("storeId") Integer storeId) {
		Store store = this.storeService.findOneUsingSpecs(storeId);
		StoreSource storeSource = ((StoreSourceService) this.entityService).setStore(id, store);
		return ResponseEntity.ok(new StoreSourceView(storeSource));
	}

	@DeleteMapping("/{id}/store")
	public ResponseEntity removeStore(@PathVariable("id") Integer id) {
		StoreSource storeSource = ((StoreSourceService) this.entityService).setStore(id, null);
		return ResponseEntity.ok(new StoreSourceView(storeSource));
	}

	@PutMapping("/{id}/banner-source/{bannerSourceId}")
	public ResponseEntity setBannerSource(@PathVariable("id") Integer id,
								   @PathVariable("bannerSourceId") Integer bannerSourceId) {
		BannerSource bannerSource = bannerSourceService.findOneUsingSpecs(bannerSourceId);
		StoreSource storeSource = ((StoreSourceService) this.entityService).setBannerSource(id, bannerSource);
		return ResponseEntity.ok(new StoreSourceView(storeSource));
	}

	@DeleteMapping("/{id}/banner-source")
	public ResponseEntity removeBannerSource(@PathVariable("id") Integer id) {
		StoreSource storeSource = ((StoreSourceService) this.entityService).setBannerSource(id, null);
		return ResponseEntity.ok(new StoreSourceView(storeSource));
	}


}
