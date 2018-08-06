package com.mtn.repository.specification;

import com.mtn.model.domain.Permission;
import com.mtn.model.domain.Role;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * Created by Allen on 4/23/2017.
 */
public class PermissionSpecifications extends AuditingEntitySpecifications {

    private static final String DISPLAY_NAME = "displayName";
    private static final String ROLE = "role";
    private static final String ID = "id";

    public static Specification<Permission> displayNameContains(String value) {
        return new Specification<Permission>() {
            @Override
            public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get(DISPLAY_NAME)), String.format("%%%s%%", value.toLowerCase()));
            }
        };
    }

    public static Specification<Permission> roleIdEquals(Integer id) {
        return new Specification<Permission>() {
            @Override
            public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join<Permission, Role> permissionRoleJoin = root.join(ROLE);
                return criteriaBuilder.equal(permissionRoleJoin.get(ID), id);
            }
        };
    }

}
