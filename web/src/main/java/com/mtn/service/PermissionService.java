package com.mtn.service;

import com.mtn.model.domain.Permission;
import com.mtn.model.view.PermissionView;
import com.mtn.repository.PermissionRepository;
import com.mtn.validators.PermissionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService extends EntityService<Permission, PermissionView> {

    @Autowired
    public PermissionService(SecurityService securityService,
                             PermissionRepository repository,
                             PermissionValidator validator) {
        super(securityService, repository, validator, Permission::new);
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
