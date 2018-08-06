package com.mtn.service;

import com.mtn.model.domain.Permission;
import com.mtn.repository.PermissionRepository;
import com.mtn.repository.specification.PermissionSpecifications;
import com.mtn.validators.PermissionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by Allen on 5/6/2017.
 */
@Service
public class PermissionServiceImpl extends EntityServiceImpl<Permission> implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private PermissionValidator permissionValidator;

    @Override
    public List<Permission> findAllByRoleId(Integer roleId) {
        return permissionRepository.findAll(Specifications.where(PermissionSpecifications.roleIdEquals(roleId)));
    }

    @Override
    public Permission updateEntityAttributes(Permission existing, Permission request) {
        existing.setDisplayName(request.getDisplayName());
        existing.setDescription(request.getDescription());

        return existing;
    }

    @Override
    public String getEntityName() {
        return "Permission";
    }

    @Override
    public PermissionValidator getValidator() {
        return permissionValidator;
    }

}
