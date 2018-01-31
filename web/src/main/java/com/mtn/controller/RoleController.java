package com.mtn.controller;

import com.mtn.model.domain.UserProfile;
import com.mtn.model.domain.Permission;
import com.mtn.model.domain.Role;
import com.mtn.model.simpleView.SimpleRoleView;
import com.mtn.model.simpleView.SimpleUserProfileView;
import com.mtn.model.view.RoleView;
import com.mtn.model.simpleView.SimplePermissionView;
import com.mtn.service.PermissionService;
import com.mtn.service.RoleService;
import com.mtn.service.UserProfileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by Allen on 5/6/2017.
 */
@RestController
@RequestMapping("/api/role")
public class RoleController extends CrudControllerImpl<Role> {

    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserProfileService userProfileService;

    @RequestMapping(value = "/{roleId}/member/{userId}", method = RequestMethod.POST)
    public ResponseEntity addOneMemberToRole(@PathVariable("roleId") Integer roleId, @PathVariable("userId") Integer userId) {
        if (roleId == 1) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Role domainModel = getEntityService().addOneMemberToRole(roleId, userId);
        return ResponseEntity.ok(new RoleView(domainModel));
    }

    @RequestMapping(value = "/{roleId}/permission/{permissionId}", method = RequestMethod.POST)
    public ResponseEntity addOnePermissionToRole(@PathVariable("roleId") Integer roleId, @PathVariable("permissionId") Integer permissionId) {
        if (roleId == 1) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Role domainModel = getEntityService().addOnePermissionToRole(roleId, permissionId);
        return ResponseEntity.ok(new RoleView(domainModel));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAll(@RequestParam(value = "displayName", required = false) String displayName, Pageable page) {
        Page<Role> domainModels;
        if (StringUtils.isNotBlank(displayName)) {
            domainModels = roleService.findAllByNameUsingSpecs(displayName, page);
        } else {
            domainModels = roleService.findAllUsingSpecs(page);
        }
        return ResponseEntity.ok(domainModels.map(this::getSimpleViewFromModel));
    }

    @RequestMapping(value = "/{id}/member", method = RequestMethod.GET)
    public ResponseEntity findAllMembersForRole(@PathVariable("id") Integer roleId) {
        List<UserProfile> domainModels = userProfileService.findAllByRoleIdUsingSpecs(roleId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleUserProfileView::new).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/{id}/permission", method = RequestMethod.GET)
    public ResponseEntity findAllPermissionsForRole(@PathVariable("id") Integer roleId) {
        List<Permission> domainModels = permissionService.findAllByRoleId(roleId);
        return ResponseEntity.ok(domainModels.stream().map(SimplePermissionView::new).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/{roleId}/member/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity removeOneMemberFromRole(@PathVariable("roleId") Integer roleId, @PathVariable("userId") Integer userId) {
        if (roleId == 1) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Role domainModel = roleService.removeOneMemberFromRole(roleId, userId);
        if (domainModel == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(new RoleView(domainModel));
    }

    @RequestMapping(value = "/{roleId}/permission/{permissionId}", method = RequestMethod.DELETE)
    public ResponseEntity removeOnePermissionFromRole(@PathVariable("roleId") Integer roleId, @PathVariable("permissionId") Integer permissionId) {
        if (roleId == 1) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Role domainModel = roleService.removeOnePermissionFromRole(roleId, permissionId);
        if (domainModel == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(new RoleView(domainModel));
    }

    @Override
    public RoleService getEntityService() {
        return roleService;
    }

    @Override
    public Object getViewFromModel(Role model) {
        return new RoleView(model);
    }

    @Override
    public Object getSimpleViewFromModel(Role model) {
        return new SimpleRoleView(model);
    }
}
