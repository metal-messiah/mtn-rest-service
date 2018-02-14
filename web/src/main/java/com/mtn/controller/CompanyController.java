package com.mtn.controller;

import com.mtn.model.domain.Company;
import com.mtn.model.domain.Store;
import com.mtn.model.simpleView.SimpleCompanyView;
import com.mtn.model.view.CompanyView;
import com.mtn.model.simpleView.SimpleStoreView;
import com.mtn.service.CompanyService;
import com.mtn.service.StoreService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Allen on 6/10/2017.
 */
@RestController
@RequestMapping("/api/company")
public class CompanyController extends CrudControllerImpl<Company> {

	@Autowired
	private CompanyService companyService;
	@Autowired
	private StoreService storeService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity findAllByCompanyName(@RequestParam(value = "name", required = false) String name, Pageable page) {
		Page<Company> domainModels;
		if (StringUtils.isNotBlank(name)) {
			domainModels = getEntityService().findAllByCompanyNameLike(name, page);
		} else {
			domainModels = getEntityService().findAll(page);
		}

		return ResponseEntity.ok(domainModels.map(this::getSimpleViewFromModel));
	}

	@RequestMapping(value = "/{id}/store", method = RequestMethod.GET)
	public ResponseEntity findAllStoresForCompany(@PathVariable("id") Integer id, @RequestParam(value = "recursive", defaultValue = "false") Boolean doRecursive) {
		List<Store> domainModels;
		if (doRecursive) {
			domainModels = storeService.findAllByParentCompanyIdRecursive(id);
		} else {
			domainModels = storeService.findAllByBannerId(id);
		}

		return ResponseEntity.ok(domainModels.stream().map(SimpleStoreView::new).collect(Collectors.toList()));
	}

	@RequestMapping(value = "/{childId}/parent/{parentId}")
	public ResponseEntity updateOneParentCompany(@PathVariable("childId") Integer childId, @PathVariable("parentId") Integer parentId) {
		Company domainModel = getEntityService().updateOneParentCompany(childId, parentId);
		return ResponseEntity.ok(new CompanyView(domainModel));
	}

	@Override
	public CompanyService getEntityService() {
		return companyService;
	}

	@Override
	public Object getViewFromModel(Company model) {
		return new CompanyView(model);
	}

	@Override
	public Object getSimpleViewFromModel(Company model) {
		return new SimpleCompanyView(model);
	}
}
