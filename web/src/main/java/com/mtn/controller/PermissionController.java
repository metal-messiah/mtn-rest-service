package com.mtn.controller;

import com.mtn.model.converter.PermissionToSimplePermissionViewConverter;
import com.mtn.model.domain.Permission;
import com.mtn.model.view.PermissionView;
import com.mtn.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Allen on 5/6/2017.
 */
@RestController
@RequestMapping("/api/permission")
public class PermissionController extends CrudControllerImpl<Permission> {

    @Autowired
    private PermissionService permissionService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAll(Pageable page) {
        Page<Permission> domainModels = getEntityService().findAll(page);
        return ResponseEntity.ok(domainModels.map(new PermissionToSimplePermissionViewConverter()));
    }

    @Override
    public PermissionService getEntityService() {
        return permissionService;
    }

    @Override
    public Object getViewFromModel(Object model) {
        return new PermissionView((Permission) model);
    }
}
