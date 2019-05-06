package com.mtn.controller;

import com.mtn.model.domain.Role;
import com.mtn.model.simpleView.SimpleRoleView;
import com.mtn.model.view.RoleView;
import com.mtn.service.RoleService;
import com.mtn.util.MtnLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/role")
public class RoleController extends CrudController<Role, RoleView> {

    @Autowired
    public RoleController(RoleService roleService) {
        super(roleService, RoleView::new);
    }

    @GetMapping
    public ResponseEntity findAll(Pageable page) {
        Page<Role> domainModels = this.entityService.findAllUsingSpecs(page);
        return ResponseEntity.ok(domainModels.map(SimpleRoleView::new));
    }

    @PutMapping("/{roleId}/permissions")
    public ResponseEntity<RoleView> updatePermissions(@PathVariable Integer roleId,
                                                      @RequestBody List<Integer> permissionIds) {
        Role role = ((RoleService) this.entityService).updatePermissions(roleId, permissionIds);
        MtnLogger.info(permissionIds.toString());
        return ResponseEntity.ok(new RoleView(role));
    }

}
