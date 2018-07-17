package com.mtn.controller;

import com.mtn.model.domain.StoreSource;
import com.mtn.model.simpleView.SimpleStoreSourceView;
import com.mtn.model.view.StoreSourceView;
import com.mtn.service.EntityService;
import com.mtn.service.SecurityService;
import com.mtn.service.StoreSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/store-source")
public class StoreSourceController extends CrudControllerImpl<StoreSource> {

	@Autowired
	private StoreSourceService storeSourceService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity findAll(Pageable page, @RequestParam Map<String, String> queryMap) {
		Page<StoreSource> domainModels = storeSourceService.findAllByQuery(queryMap, page);
		return ResponseEntity.ok(domainModels.map(this::getSimpleViewFromModel));
	}

	@Override
	public EntityService<StoreSource> getEntityService() {
		return storeSourceService;
	}

	@Override
	public StoreSourceView getViewFromModel(StoreSource model) {
		return new StoreSourceView(model);
	}

	@Override
	public SimpleStoreSourceView getSimpleViewFromModel(StoreSource model) {
		return new SimpleStoreSourceView(model);
	}
}
