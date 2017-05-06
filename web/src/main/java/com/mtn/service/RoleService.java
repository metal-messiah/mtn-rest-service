package com.mtn.service;

import com.mtn.model.domain.UserProfile;
import com.mtn.model.domain.auth.Permission;
import com.mtn.model.domain.auth.Role;
import com.mtn.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

import static com.mtn.repository.specification.RoleSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 5/6/2017.
 */
@Service
public class RoleService extends ValidatingDataService<Role> {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserProfileService userProfileService;

    @Transactional
    public Role addOne(Role request) {
        validateForInsert(request);

        UserProfile systemAdministrator = userProfileService.findSystemAdministrator();
        request.setCreatedBy(systemAdministrator);
        request.setUpdatedBy(systemAdministrator);

        return roleRepository.save(request);
    }

    @Transactional
    public Role addOneMemberToRole(Integer roleId, Integer userId) {
        Role role = findOneUsingSpecs(roleId);
        validateNotNull(role);

        UserProfile member = userProfileService.findOneUsingSpecs(userId);
        userProfileService.validateNotNull(member);

        role.getMembers().add(member);

        return role;
    }

    @Transactional
    public Role addOnePermissionToRole(Integer roleId, Integer permissionId) {
        Role role = findOneUsingSpecs(roleId);
        validateNotNull(role);

        Permission permission = permissionService.findOne(permissionId);
        permissionService.validateNotNull(permission);

        role.getPermissions().add(permission);

        return role;
    }

    @Transactional
    public void deleteOne(Integer id) {
        Role existing = findOneUsingSpecs(id);
        if (existing == null) {
            throw new IllegalArgumentException("No Role found with this id");
        }

        existing.setMembers(new HashSet<>());
        existing.setPermissions(new HashSet<>());

        existing.setDeletedBy(userProfileService.findSystemAdministrator());
    }

    public Page<Role> findAllUsingSpecs(Pageable page) {
        return roleRepository.findAll(
                where(isNotDeleted())
                , page
        );
    }

    public Page<Role> findAllByNameUsingSpecs(String name, Pageable page) {
        return roleRepository.findAll(
                where(displayNameContains(name))
                        .and(isNotDeleted())
                , page
        );
    }

    public Role findOne(Integer id) {
        return roleRepository.findOne(id);
    }

    public Role findOneUsingSpecs(Integer id) {
        return roleRepository.findOne(
                where(idEquals(id))
                        .and(isNotDeleted())
        );
    }

    public Role findOneByDisplayName(String displayName) {
        return roleRepository.findOneByDisplayName(displayName);
    }

    @Transactional
    public Role removeOneMemberFromRole(Integer roleId, Integer userId) {
        Role role = findOneUsingSpecs(roleId);
        validateNotNull(role);

        role.getMembers().removeIf(member -> member.getId().equals(userId));

        return role;
    }

    @Transactional
    public Role removeOnePermissionFromRole(Integer roleId, Integer permissionId) {
        Role role = findOneUsingSpecs(roleId);
        validateNotNull(role);

        role.getPermissions().removeIf(permission -> permission.getId().equals(permissionId));

        return role;
    }

    @Transactional
    public Role updateOne(Integer id, Role request) {
        validateNotNull(request);
        validateForUpdate(request);

        Role existing = findOneUsingSpecs(id);
        validateNotNull(existing);

        existing.setDisplayName(request.getDisplayName());
        existing.setDescription(request.getDescription());
        existing.setUpdatedBy(userProfileService.findSystemAdministrator());

        return existing;
    }

    @Override
    public String getEntityName() {
        return "Role";
    }

    @Override
    public void validateBusinessRules(Role object) {
        //No special rules to enforce yet
    }

    @Override
    public void validateDoesNotExist(Role object) {
        Role existing = findOneByDisplayName(object.getDisplayName());
        if (existing != null) {
            throw new IllegalArgumentException("Role with this displayName already exists");
        }
    }
}
