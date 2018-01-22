package com.mtn.controller;

import com.mtn.model.converter.PermissionToSimplePermissionViewConverter;
import com.mtn.model.domain.Permission;
import com.mtn.model.view.PermissionView;
import com.mtn.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Allen on 5/6/2017.
 */
@RestController
@RequestMapping("/api/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAll(Pageable page) {
        Page<Permission> domainModels = permissionService.findAll(page);
        return ResponseEntity.ok(domainModels.map(new PermissionToSimplePermissionViewConverter()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        Permission domainModel = permissionService.findOne(id);
        if (domainModel == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(new PermissionView(domainModel));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateOne(@PathVariable("id") Integer id, @RequestBody Permission request) {
        Permission domainModel = permissionService.updateOne(id, request);
        return ResponseEntity.ok(new PermissionView(domainModel));
    }
}
