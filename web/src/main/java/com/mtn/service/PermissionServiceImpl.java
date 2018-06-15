package com.mtn.service;

import com.mtn.model.domain.Permission;
import com.mtn.repository.PermissionRepository;
import com.mtn.validators.PermissionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mtn.repository.specification.PermissionSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

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
        return getEntityRepository().findAllByRolesId(roleId);
    }

    @Override
    public Page<Permission> findAllUsingSpecs(Pageable page) {
        return getEntityRepository().findAll(
                where(isNotDeleted()),
                page
        );
    }

    @Override
    public Permission findOneUsingSpecs(Integer id) {
        return getEntityRepository().findOne(
                where(idEquals(id))
                        .and(isNotDeleted())
        );
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
    public void handleAssociationsOnDeletion(Permission existing) {
        // No collections to handle
    }

    @Override
    public void handleAssociationsOnCreation(Permission request) {
        // No collections to handle
    }

    @Override
    public PermissionRepository getEntityRepository() {
        return permissionRepository;
    }

    @Override
    public PermissionValidator getValidator() {
        return permissionValidator;
    }

}
