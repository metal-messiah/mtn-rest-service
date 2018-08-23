package com.mtn.controller;

import com.mtn.model.domain.Permission;
import com.mtn.model.simpleView.SimplePermissionView;
import com.mtn.model.view.PermissionView;
import com.mtn.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/permission")
public class PermissionController extends CrudController<Permission, PermissionView> {

    @Autowired
    public PermissionController(PermissionService permissionService) {
        super(permissionService, PermissionView::new);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAll(Pageable page) {
        Page<Permission> domainModels = this.entityService.findAll(page);
        return ResponseEntity.ok(domainModels.map(SimplePermissionView::new));
    }

}
