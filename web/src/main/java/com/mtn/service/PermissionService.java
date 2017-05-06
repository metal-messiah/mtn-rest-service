package com.mtn.service;

import com.mtn.model.domain.auth.Permission;
import com.mtn.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Allen on 5/6/2017.
 */
@Service
public class PermissionService extends ValidatingDataService<Permission> {

    @Autowired
    private PermissionRepository permissionRepository;

    public Page<Permission> findAll(Pageable page) {
        return permissionRepository.findAll(page);
    }

    public List<Permission> findAllByRoleId(Integer roleId) {
        return permissionRepository.findAllByRolesId(roleId);
    }

    public Permission findOne(Integer id) {
        return permissionRepository.findOne(id);
    }

    @Transactional
    public Permission updateOne(Integer id, Permission request) {
        validateForUpdate(request);

        Permission existing = findOne(id);
        if (existing == null) {
            throw new IllegalArgumentException("No Permission found with this id");
        }

        existing.setDisplayName(request.getDisplayName());
        existing.setDescription(request.getDescription());

        return existing;
    }

    @Override
    public String getEntityName() {
        return "Permission";
    }

    @Override
    public void validateBusinessRules(Permission object) {
        //No special rules to enforce yet
    }

    @Override
    public void validateDoesNotExist(Permission object) {
        //No user-facing unique constraints to enforce yet
    }
}
