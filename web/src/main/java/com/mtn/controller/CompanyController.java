package com.mtn.controller;

import com.mtn.model.converter.CompanyToSimpleCompanyViewConverter;
import com.mtn.model.domain.Company;
import com.mtn.model.domain.Store;
import com.mtn.model.view.CompanyView;
import com.mtn.model.view.SimpleStoreView;
import com.mtn.service.CompanyService;
import com.mtn.service.SecurityService;
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
public class CompanyController {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private SecurityService securityService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addOne(Company request) {
        securityService.checkPermission("COMPANIES_CREATE");

        Company domainModel = companyService.addOne(request);
        return ResponseEntity.ok(new CompanyView(domainModel));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAll(@RequestParam(value = "name", required = false) String name, Pageable page) {
        securityService.checkPermission("COMPANIES_READ");

        Page<Company> domainModels;
        if (StringUtils.isNotBlank(name)) {
            domainModels = companyService.findAllWhereNameLike(name, page);
        } else {
            domainModels = companyService.findAll(page);
        }

        return ResponseEntity.ok(domainModels.map(new CompanyToSimpleCompanyViewConverter()));
    }

    @RequestMapping(value = "/{id}/store", method = RequestMethod.GET)
    public ResponseEntity findAllStoresForCompany(@PathVariable("id") Integer id, @RequestParam(value = "recursive", defaultValue = "false") Boolean doRecursive) {
        securityService.checkPermission("COMPANIES_READ");

        List<Store> domainModels;
        if (doRecursive) {
            domainModels = storeService.findAllByParentCompanyIdRecursive(id);
        } else {
            domainModels = storeService.findAllByParentCompanyId(id);
        }

        return ResponseEntity.ok(domainModels.stream().map(SimpleStoreView::new).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateOne(@PathVariable("id") Integer id, Company request) {
        securityService.checkPermission("COMPANIES_UPDATE");

        Company domainModel = companyService.updateOne(id, request);
        return ResponseEntity.ok(new CompanyView(domainModel));
    }

    @RequestMapping(value = "/{childId}/parent/{parentId}")
    public ResponseEntity updateOneParentCompany(@PathVariable("childId") Integer childId, @PathVariable("parentId") Integer parentId) {
        securityService.checkPermission("COMPANIES_UPDATE");

        Company domainModel = companyService.updateOneParentCompany(childId, parentId);
        return ResponseEntity.ok(new CompanyView(domainModel));
    }
}
