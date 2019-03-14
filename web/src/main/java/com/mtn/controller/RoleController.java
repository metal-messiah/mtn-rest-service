package com.mtn.controller;

import com.mtn.model.domain.Role;
import com.mtn.model.simpleView.SimpleRoleView;
import com.mtn.model.view.RoleView;
import com.mtn.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/role")
public class RoleController extends CrudController<Role, RoleView> {

    @Autowired
    public RoleController(RoleService roleService) {
        super(roleService, RoleView::new);
    }

    @GetMapping
    public ResponseEntity findAll(@RequestParam(value = "displayName", required = false) String displayName, Pageable page) {
        Page<Role> domainModels;
        if (StringUtils.isNotBlank(displayName)) {
            domainModels = ((RoleService) this.entityService).findAllByNameUsingSpecs(displayName, page);
        } else {
            domainModels = this.entityService.findAllUsingSpecs(page);
        }
        return ResponseEntity.ok(domainModels.map(SimpleRoleView::new));
    }

}
