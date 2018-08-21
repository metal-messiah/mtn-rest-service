package com.mtn.service;

import com.mtn.model.domain.Permission;
import com.mtn.model.view.PermissionView;
import com.mtn.repository.EntityRepository;
import com.mtn.repository.PermissionRepository;
import com.mtn.repository.specification.PermissionSpecifications;
import com.mtn.validators.EntityValidator;
import com.mtn.validators.PermissionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService extends EntityServiceImpl<Permission, PermissionView> {

    @Autowired
    public PermissionService(EntityServiceDependencies services,
                             PermissionRepository repository,
                             PermissionValidator validator) {
        super(services, repository, validator);
    }

    public List<Permission> findAllByRoleId(Integer roleId) {
        return this.repository.findAll(Specifications.where(PermissionSpecifications.roleIdEquals(roleId)));
    }

    @Override
    protected Permission createNewEntity() {
        return new Permission();
    }

    @Override
    protected void setEntityAttributesFromRequest(Permission entity, PermissionView request) {
        entity.setSystemName(request.getSystemName());
        entity.setDisplayName(request.getDisplayName());
        entity.setDescription(request.getDescription());
        entity.setSubject(request.getSubject());
        entity.setAction(request.getAction());
    }

    @Override
    public void handleAssociationsOnDeletion(Permission existing) {
        existing.getRoles().forEach(role -> role.getPermissions().remove(existing));
    }
}
