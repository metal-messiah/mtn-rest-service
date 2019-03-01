package com.mtn.controller;

import com.mtn.model.domain.StoreSource;
import com.mtn.model.simpleView.SimpleStoreSourceView;
import com.mtn.model.view.StoreSourceView;
import com.mtn.service.StoreSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/store-source")
public class StoreSourceController extends CrudController<StoreSource, StoreSourceView> {

	@Autowired
	public StoreSourceController(StoreSourceService storeSourceService) {
		super(storeSourceService, StoreSourceView::new);
	}

	@GetMapping
	public ResponseEntity findAll(Pageable page, @RequestParam Map<String, String> queryMap) {
		Page<StoreSource> domainModels = ((StoreSourceService) this.entityService).findAllByQuery(queryMap, page);
		return ResponseEntity.ok(domainModels.map(SimpleStoreSourceView::new));
	}

}
