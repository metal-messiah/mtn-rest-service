package com.mtn.controller;

import com.mtn.model.domain.Company;
import com.mtn.model.simpleView.SimpleCompanyView;
import com.mtn.model.view.CompanyView;
import com.mtn.service.CompanyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company")
public class CompanyController extends CrudController<Company, CompanyView> {

	@Autowired
	public CompanyController(CompanyService companyService) {
		super(companyService, CompanyView::new);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity findAllByCompanyName(@RequestParam(value = "name", required = false) String name, Pageable page) {
		Page<Company> domainModels;
		if (StringUtils.isNotBlank(name)) {
			domainModels = ((CompanyService) this.entityService).findAllByCompanyNameLike(name, page);
		} else {
			domainModels = this.entityService.findAll(page);
		}

		return ResponseEntity.ok(domainModels.map(SimpleCompanyView::new));
	}

	@RequestMapping(value = "/{childId}/parent/{parentId}")
	public ResponseEntity updateOneParentCompany(@PathVariable("childId") Integer childId, @PathVariable("parentId") Integer parentId) {
		Company domainModel = ((CompanyService) this.entityService).setParentCompany(childId, parentId);
		return ResponseEntity.ok(new CompanyView(domainModel));
	}

}
