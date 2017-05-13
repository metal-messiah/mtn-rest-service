package com.mtn.controller;

import com.mtn.model.converter.RoleToSimpleRoleViewConverter;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.domain.auth.Permission;
import com.mtn.model.domain.auth.Role;
import com.mtn.model.view.SimpleUserProfileView;
import com.mtn.model.view.auth.RoleView;
import com.mtn.model.view.auth.SimplePermissionView;
import com.mtn.service.PermissionService;
import com.mtn.service.RoleService;
import com.mtn.service.SecurityService;
import com.mtn.service.UserProfileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by Allen on 5/6/2017.
 */
@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private SecurityService securityService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addOne(@RequestBody Role request) {
        securityService.checkPermission("ROLES_CREATE");

        Role domainModel = roleService.addOne(request);
        return ResponseEntity.ok(new RoleView(domainModel));
    }

    @RequestMapping(value = "/{roleId}/member/{userId}", method = RequestMethod.POST)
    public ResponseEntity addOneMemberToRole(@PathVariable("roleId") Integer roleId, @PathVariable("userId") Integer userId) {
        securityService.checkPermission("ROLES_UPDATE");

        Role domainModel = roleService.addOneMemberToRole(roleId, userId);
        return ResponseEntity.ok(new RoleView(domainModel));
    }

    @RequestMapping(value = "/{roleId}/permission/{permissionId}", method = RequestMethod.POST)
    public ResponseEntity addOnePermissionToRole(@PathVariable("roleId") Integer roleId, @PathVariable("permissionId") Integer permissionId) {
        securityService.checkPermission("ROLES_UPDATE");

        Role domainModel = roleService.addOnePermissionToRole(roleId, permissionId);
        return ResponseEntity.ok(new RoleView(domainModel));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOne(@PathVariable("id") Integer id) {
        securityService.checkPermission("ROLES_DELETE");

        roleService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAll(@RequestParam(value = "displayName", required = false) String displayName, Pageable page) {
        securityService.checkPermission("ROLES_READ");

        Page<Role> domainModels;
        if (StringUtils.isNotBlank(displayName)) {
            domainModels = roleService.findAllByNameUsingSpecs(displayName, page);
        } else {
            domainModels = roleService.findAllUsingSpecs(page);
        }
        return ResponseEntity.ok(domainModels.map(new RoleToSimpleRoleViewConverter()));
    }

    @RequestMapping(value = "/{id}/member", method = RequestMethod.GET)
    public ResponseEntity findAllMembersForRole(@PathVariable("id") Integer roleId) {
        securityService.checkPermission("ROLES_READ");

        List<UserProfile> domainModels = userProfileService.findAllByRoleIdUsingSpecs(roleId);
        return ResponseEntity.ok(domainModels.stream().map(SimpleUserProfileView::new).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/{id}/permission", method = RequestMethod.GET)
    public ResponseEntity findAllPermissionsForRole(@PathVariable("id") Integer roleId) {
        securityService.checkPermission("ROLES_READ");

        List<Permission> domainModels = permissionService.findAllByRoleId(roleId);
        return ResponseEntity.ok(domainModels.stream().map(SimplePermissionView::new).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        securityService.checkPermission("ROLES_READ");

        Role domainModel = roleService.findOneUsingSpecs(id);
        return ResponseEntity.ok(new RoleView(domainModel));
    }

    @RequestMapping(value = "/{roleId}/member/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity removeOneMemberFromRole(@PathVariable("roleId") Integer roleId, @PathVariable("userId") Integer userId) {
        securityService.checkPermission("ROLES_UPDATE");

        Role domainModel = roleService.removeOneMemberFromRole(roleId, userId);
        return ResponseEntity.ok(new RoleView(domainModel));
    }

    @RequestMapping(value = "/{roleId}/permission/{permissionId}", method = RequestMethod.DELETE)
    public ResponseEntity removeOnePermissionFromRole(@PathVariable("roleId") Integer roleId, @PathVariable("permissionId") Integer permissionId) {
        securityService.checkPermission("ROLES_UPDATE");

        Role domainModel = roleService.removeOnePermissionFromRole(roleId, permissionId);
        return ResponseEntity.ok(new RoleView(domainModel));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateOne(@PathVariable("id") Integer id, @RequestBody Role request) {
        securityService.checkPermission("ROLES_UPDATE");

        Role domainModel = roleService.updateOne(id, request);
        return ResponseEntity.ok(new RoleView(domainModel));
    }
}
