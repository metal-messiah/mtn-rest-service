package com.mtn.repository.specification;

import com.mtn.model.domain.Permission;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by Allen on 4/23/2017.
 */
public class PermissionSpecifications {

    private static final String DELETED_DATE = "deletedDate";
    private static final String DISPLAY_NAME = "displayName";
    private static final String ID = "id";

    public static Specification<Permission> displayNameContains(String value) {
        return new Specification<Permission>() {
            @Override
            public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get(DISPLAY_NAME)), String.format("%%%s%%", value.toLowerCase()));
            }
        };
    }

    public static Specification<Permission> idEquals(Integer id) {
        return new Specification<Permission>() {
            @Override
            public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(ID), id);
            }
        };
    }

    public static Specification<Permission> isNotDeleted() {
        return new Specification<Permission>() {
            @Override
            public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isNull(root.get(DELETED_DATE));
            }
        };
    }

}